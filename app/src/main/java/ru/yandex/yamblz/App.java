package ru.yandex.yamblz;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.frogermcs.androiddevmetrics.AndroidDevMetrics;

import ru.yandex.yamblz.developer_settings.DevMetricsProxy;
import ru.yandex.yamblz.developer_settings.DeveloperSettingsModel;
import timber.log.Timber;

public class App extends Application {
    private ApplicationComponent applicationComponent;

    public static final int[] descriptions= {R.string.skate_desc_1, R.string.skate_desc_2, R.string.skate_desc_3, R.string.skate_desc_4, R.string.skate_desc_5};
    public static final int[] names= {R.string.skate_short_name_1, R.string.skate_short_name_2, R.string.skate_short_name_3, R.string.skate_short_name_4, R.string.skate_short_name_5};
    public static final int[] full_names= {R.string.skate_full_name_1, R.string.skate_full_name_2, R.string.skate_full_name_3, R.string.skate_full_name_4, R.string.skate_full_name_5};
    public static final int[] img_skate= {R.drawable.skate1, R.drawable.skate2, R.drawable.skate3, R.drawable.skate4, R.drawable.skate5};
    public static final int[] img_comp= {R.drawable.comp1, R.drawable.comp2, R.drawable.comp3, R.drawable.comp4, R.drawable.comp5};


    // Prevent need in a singleton (global) reference to the application object.
    @NonNull
    public static App get(@NonNull Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = prepareApplicationComponent().build();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());

            DeveloperSettingsModel developerSettingModel = applicationComponent.developerSettingModel();
            developerSettingModel.apply();

            DevMetricsProxy devMetricsProxy = applicationComponent.devMetricsProxy();
            devMetricsProxy.apply();
            AndroidDevMetrics.initWith(this);
        }
    }

    @NonNull
    protected DaggerApplicationComponent.Builder prepareApplicationComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this));
    }

    @NonNull
    public ApplicationComponent applicationComponent() {
        return applicationComponent;
    }
}
