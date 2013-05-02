package org.nsdev.apps.transittamer.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import com.squareup.otto.Bus;
import org.nsdev.apps.transittamer.BootstrapApplication;

import javax.inject.Inject;

/**
 * Created by neal 13-04-09 9:09 PM
 */
public class ScheduleLinkClickActivity extends Activity
{
    @Inject
    Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        final Uri uri = getIntent().getData();
        BootstrapApplication.inject(this);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                bus.post(new ScheduleLinkClickEvent(uri));
            }
        }, 100);
        finish();
    }

}
