package ru.yandex.yamblz.loaders;

/**
 * Created by vorona on 09.08.16.
 */

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.loaders.model.Skate;

/**
 * Created by vorona on 19.07.16.
 */

public class SkateLoader extends AsyncTaskLoader<List<Skate>> {
    private List<Skate> skates;

    public SkateLoader(Context context) {
        super(context);

    }

    @Override
    public List<Skate> loadInBackground() {
        //Can load data from net here
        skates = new ArrayList<>();
        skates.add(new Skate(getContext().getString(R.string.skate_short_name_1),
                getContext().getString(R.string.skate_full_name_1),
                getContext().getString(R.string.skate_desc_1),
                R.drawable.skate1,
                R.drawable.comp1
        ));
        skates.add(new Skate(getContext().getString(R.string.skate_short_name_2),
                getContext().getString(R.string.skate_full_name_2),
                getContext().getString(R.string.skate_desc_2),
                R.drawable.skate2,
                R.drawable.comp2
        ));
        skates.add(new Skate(getContext().getString(R.string.skate_short_name_3),
                getContext().getString(R.string.skate_full_name_3),
                getContext().getString(R.string.skate_desc_3),
                R.drawable.skate3,
                R.drawable.comp3
        ));
        skates.add(new Skate(getContext().getString(R.string.skate_short_name_4),
                getContext().getString(R.string.skate_full_name_4),
                getContext().getString(R.string.skate_desc_4),
                R.drawable.skate4,
                R.drawable.comp4
        ));
        skates.add(new Skate(getContext().getString(R.string.skate_short_name_5),
                getContext().getString(R.string.skate_full_name_5),
                getContext().getString(R.string.skate_desc_5),
                R.drawable.skate5,
                R.drawable.comp5
        ));
        return skates;
    }


    /**
     * Called when there is new data to deliver to the client.  The
     * super class will take care of delivering it; the implementation
     * here just adds a little more logic.
     */
    @Override
    public void deliverResult(List<Skate> sin) {
        if (isReset()) {
            // An async query came in while the loader is stopped.  We
            // don't need the result.
            if (sin != null) {
                onReleaseResources(sin);
            }
        }
        skates = sin;

        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(sin);
        }
    }

    /**
     * Handles a request to start the Loader.
     */
    @Override
    protected void onStartLoading() {
        if (skates != null) {
            // If we currently have a result available, deliver it
            // immediately.
            deliverResult(skates);
        }

        if (takeContentChanged() || skates == null) {
            // If the data has changed since the last time it was loaded
            // or is not currently available, start a load.
            forceLoad();
        }
    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    /**
     * Handles a request to cancel a load.
     */
    @Override
    public void onCanceled(List<Skate> singers) {
        super.onCanceled(singers);

        // At this point we can release the resources associated with 'apps'
        // if needed.
        onReleaseResources(singers);
    }

    /**
     * Handles a request to completely reset the Loader.
     */
    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        // At this point we can release the resources associated with 'apps'
        // if needed.
        if (skates != null) {
            onReleaseResources(skates);
            skates = null;
        }
    }

    /**
     * Helper function to take care of releasing resources associated
     * with an actively loaded data set.
     *
     * @param singers
     */
    protected void onReleaseResources(List<Skate> singers) {
        // For a simple List<> there is nothing to do.  For something
        // like a Cursor, we would close it here.
    }
}