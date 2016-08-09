package ru.yandex.yamblz.ui.activities;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.List;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.loaders.SkateLoader;
import ru.yandex.yamblz.loaders.model.Skate;
import ru.yandex.yamblz.ui.fragments.ScreenSlidePageFragment;
import ru.yandex.yamblz.ui.views.MyViewPager;

public class ScreenSlideActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<List<Skate>>,
ListProvider{

    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private MyViewPager mPager;
    private List<Skate> skates;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        getLoaderManager().initLoader(0, null, this);

        mPager = (MyViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());

    }

    @Override
    public Loader<List<Skate>> onCreateLoader(int id, Bundle args) {
        return new SkateLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Skate>> loader, List<Skate> data) {
        skates = data;
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(false, (page, position) -> {
            View txt = page.findViewById(R.id.txtSkateName);
            txt.setTranslationX((int) (txt.getWidth() * (position * 0.5)));
            View img = page.findViewById(R.id.imgComp);
            img.setAlpha((position+1)/2);
            img.setTranslationX((int) (img.getWidth() * (position * 0.5)));
        });
    }

    @Override
    public void onLoaderReset(Loader<List<Skate>> loader) {

    }

    @Override
    public List<Skate> getList() {
        return skates;
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
            return ScreenSlidePageFragment.create(skates.get(position));
        }

        @Override
        public int getCount() {
            return skates.size();
        }
    }

    public void setSwipable(boolean f) {
        mPager.setSwipable(f);
    }
}
