package org.nsdev.apps.transittamer.sync;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created with IntelliJ IDEA.
 * User: neal.sanche
 * Date: 13-06-28
 * Time: 11:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigContentProvider extends android.content.ContentProvider
{
    public final static String AUTHORITY = "org.nsdev.apps.transittamer.config";

    @Override
    public boolean onCreate()
    {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        return null;
    }

    @Override
    public String getType(Uri uri)
    {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        return 0;
    }
}
