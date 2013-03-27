package org.nsdev.apps.transittamer;

import android.app.Application;
import android.content.Context;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibrary;
import dagger.ObjectGraph;
import org.nsdev.apps.transittamer.module.ApplicationModule;

/**
 * TransitTamer application
 */
public class BootstrapApplication extends Application
{

    private static ObjectGraph objectGraph;

    /**
     * Create main application
     */
    public BootstrapApplication()
    {
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        objectGraph = ObjectGraph.create(new ApplicationModule(this));

        LocationLibrary.initialiseLibrary(getBaseContext(), true, "org.nsdev.apps.transittamer");
        LocationLibrary.showDebugOutput(true);
        LocationLibrary.forceLocationUpdate(getBaseContext());
    }

    public static <T> void inject(T instance)
    {
        objectGraph.inject(instance);
    }

    public static BootstrapApplication from(Context context)
    {
        return (BootstrapApplication) context.getApplicationContext();
    }
}
