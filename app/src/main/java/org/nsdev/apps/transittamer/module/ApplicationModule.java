package org.nsdev.apps.transittamer.module;

import android.content.Context;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.Provides;
import org.nsdev.apps.transittamer.BootstrapApplication;
import org.nsdev.apps.transittamer.ui.CarouselActivity;
import org.nsdev.apps.transittamer.ui.NearestRouteStopFragment;
import org.nsdev.apps.transittamer.ui.RouteLookupFragment;
import org.nsdev.apps.transittamer.ui.ScheduleLinkClickActivity;

import javax.inject.Singleton;

@Module(
        entryPoints = {
                BootstrapApplication.class,
                ScheduleLinkClickActivity.class,
                CarouselActivity.class,
                NearestRouteStopFragment.class,
                RouteLookupFragment.class
        },
        includes = AndroidServicesModule.class
)
public class ApplicationModule
{
    private Context context;

    public ApplicationModule(Context context)
    {
        this.context = context.getApplicationContext();
    }

    @Provides
    @Singleton
    Context provideContext()
    {
        return context;
    }

    @Provides
    @Singleton
    Bus provideBus()
    {
        return new Bus();
    }
}