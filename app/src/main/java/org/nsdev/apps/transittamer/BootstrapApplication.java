package org.nsdev.apps.transittamer;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibrary;
import dagger.ObjectGraph;
import org.nsdev.apps.transittamer.module.ApplicationModule;

/**
 * TransitTamer application
 */
public class BootstrapApplication extends Application {

    private static ObjectGraph objectGraph;

    /**
     * Create main application
     */
    public BootstrapApplication() {
    }

    /**
     * Create main application
     *
     * @param context
     */
    public BootstrapApplication(final Context context) {
        this();
        attachBaseContext(context);
    }

    /**
     * Create main application
     *
     * @param instrumentation
     */
    public BootstrapApplication(final Instrumentation instrumentation) {
        this();
        attachBaseContext(instrumentation.getTargetContext());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        objectGraph = ObjectGraph.create(new ApplicationModule(this));

        LocationLibrary.initialiseLibrary(getBaseContext(), "org.nsdev.apps.transittamer");
        LocationLibrary.forceLocationUpdate(getBaseContext());
    }

    public static <T> void inject(T instance) {
        objectGraph.inject(instance);
    }
}
