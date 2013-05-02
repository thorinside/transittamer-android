package org.nsdev.apps.transittamer;

import android.app.Application;
import android.content.Context;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibrary;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
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

        // Add your initialization code here
        Parse.initialize(this, "JfNTwe2XXLjmZNp6LAGK8A66PvZ9qTAEtKMDiYAH", "nuKWdQDxl17T4IqVWSzqPZLRjOGWGPFrPdePsrlq");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);

        ParseTwitterUtils.initialize("W86y4XkynTtjziFL1cJzA", "UjvmdW0SMOYAKZ9eKmh0ziXtu8rmlIsXxK3Aa9R8");
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
