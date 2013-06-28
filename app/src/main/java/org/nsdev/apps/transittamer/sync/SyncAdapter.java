package org.nsdev.apps.transittamer.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: neal.sanche
 * Date: 13-06-28
 * Time: 11:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter
{
    private static final String TAG = "TransitTamerSync";

    public SyncAdapter(Context context, boolean autoInitialize)
    {
        super(context, autoInitialize);
        Log.d(TAG, "SyncAdapter constructed.");
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs)
    {
        super(context, autoInitialize, allowParallelSyncs);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult)
    {
        Log.e(TAG, "onPerformSync");
    }
}
