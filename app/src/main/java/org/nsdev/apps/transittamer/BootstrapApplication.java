

package org.nsdev.apps.transittamer;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.FROYO;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;

import com.github.kevinsawicki.http.HttpRequest;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibrary;

/**
 * TransitTamer application
 */
public class BootstrapApplication extends Application {

    /**
     * Create main application
     */
    public BootstrapApplication() {
        // Disable http.keepAlive on Froyo and below
        if (SDK_INT <= FROYO)
            HttpRequest.keepAlive(false);
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
        LocationLibrary.initialiseLibrary(getBaseContext(), "org.nsdev.apps.transittamer");
        LocationLibrary.forceLocationUpdate(getBaseContext());
    }
}
