package ru.yandex.yamblz.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.ui.fragments.ScreenSlidePageFragment;
import ru.yandex.yamblz.ui.views.MyViewPager;

public class ScreenSlideActivity extends FragmentActivity {

    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private MyViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        mPager = (MyViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(false, (page, position) -> {
            View txt = page.findViewById(R.id.txtSkateName);
            txt.setTranslationX((int) (txt.getWidth() * (position * 0.5)));
            View img = page.findViewById(R.id.imgComp);
            img.setAlpha((position+1)/2);
            img.setTranslationX((int) (img.getWidth() * (position * 0.5)));
        });
    }

    /**
     * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment} objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ScreenSlidePageFragment.create(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public void setSwipable(boolean f) {
        mPager.setSwipable(f);
    }
}
