package org.nsdev.apps.transittamer.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Service wrapper for sync service.
 * User: neal.sanche
 * Date: 13-06-28
 * Time: 11:01 AM
 */
public class SyncService extends Service
{
    private static final String TAG = "TransitTamerSyncService";
    private static SyncAdapter mSyncAdapter = null;

    public SyncService()
    {
        super();
        Log.d(TAG, "constructor");
    }

    @Override
    public void onCreate()
    {
        Log.d(TAG, "onCreate()");
        super.onCreate();

        if (mSyncAdapter == null)
        {
            Log.d(TAG, "Creating sync adapter instance.");
            mSyncAdapter = new SyncAdapter(getApplicationContext(), true);
        }
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.d(TAG, "onBind " + intent);

        return mSyncAdapter.getSyncAdapterBinder();
    }
}
