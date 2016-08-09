package ru.yandex.yamblz.ui.fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.App;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.loaders.model.Skate;
import ru.yandex.yamblz.ui.activities.ScreenSlideActivity;

public class ScreenSlidePageFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {

    public static final String SKATE = "page";

    @BindView(R.id.txtFullName)
    TextView fullName;
    @BindView(R.id.txtSkateName)
    TextView name;
    @BindView(R.id.txtFullInfo)
    TextView desc;
    @BindView(R.id.imgSkate)
    ImageView imgSkate;
    @BindView(R.id.imgComp)
    ImageView imgComp;
    @BindView(R.id.second_part)
    View secondPart;

    private int h;
    private boolean secondVisible = false;
    private float transY = 0, initial = 0, initText = 0;
    private Skate skate;

    public static ScreenSlidePageFragment create(Skate skate) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putParcelable(SKATE, skate);
        fragment.setArguments(args);
        return fragment;
    }

    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skate = getArguments().getParcelable(SKATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page, container, false);

        ButterKnife.bind(this, rootView);

        imgSkate.setOnClickListener(this);

        name.setText(skate.getName());
        fullName.setText(skate.getFull_name());
        desc.setText(skate.getDescription());
        imgSkate.setImageResource(skate.getImgSkateResId());
        imgComp.setImageResource(skate.getImgCompResId());

        secondPart.setVisibility(View.INVISIBLE);
        secondPart.setOnTouchListener(this);

        return rootView;
    }


    @Override
    public void onClick(View view) {
        Log.w("OnClick", "Hey");
        secondPart.setVisibility(View.VISIBLE);
        h = secondPart.getHeight();

        secondVisible = !secondVisible;

        if (!secondVisible) h = 0;

        if (secondVisible)
            openDetails(h);
        else
            closeDetails(h);
    }

    private void openDetails(int h) {
        Log.w("", "Open Detail");
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(secondPart, "translationY", getView().getHeight(),
                getView().getHeight() - h - 64).setDuration(300);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(imgSkate, "translationY", -h).setDuration(300);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(name, "translationY", -h).setDuration(300);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(anim1, anim2, anim3);
        set.start();
        ((ScreenSlideActivity) getActivity()).setSwipable(false);
    }

    private void closeDetails(int h) {
        Log.w("", "Close Detail");
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(secondPart, "translationY", getView().getHeight()).setDuration(300);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(imgSkate, "translationY", 0).setDuration(300);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(name, "translationY", 0).setDuration(300);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(anim1, anim2, anim3);
        set.start();
        ((ScreenSlideActivity) getActivity()).setSwipable(true);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            transY = motionEvent.getRawY();
            initial = view.getY();
            initText = name.getY();
        }
        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE && secondVisible) {
            if (motionEvent.getRawY() - transY > 0) {
                float dY = motionEvent.getRawY() - transY;
                view.setTranslationY(initial + dY);
                imgSkate.setTranslationY(dY - h);
                name.setTranslationY(initText + dY);
            }

            if (motionEvent.getRawY() - transY > h / 4) {
                closeDetails(h);
                secondVisible = !secondVisible;
            }
            return true;
        }
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if (secondVisible) {
                ObjectAnimator anim1 = ObjectAnimator.ofFloat(secondPart, "translationY",
                        getView().getHeight() - h - 64).setDuration(300);
                ObjectAnimator anim2 = ObjectAnimator.ofFloat(imgSkate, "translationY", -h).setDuration(300);
                ObjectAnimator anim3 = ObjectAnimator.ofFloat(name, "translationY", -h).setDuration(300);
                AnimatorSet set = new AnimatorSet();
                set.playTogether(anim1, anim2, anim3);
                set.start();
            }
        }
        return true;
    }
}
