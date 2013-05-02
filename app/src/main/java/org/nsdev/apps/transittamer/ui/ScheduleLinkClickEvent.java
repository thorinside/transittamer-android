package org.nsdev.apps.transittamer.ui;

import android.net.Uri;

/**
 * Created by neal 13-04-09 9:42 PM
 */
public class ScheduleLinkClickEvent
{
    private final Uri uri;

    public ScheduleLinkClickEvent(Uri uri)
    {
        this.uri = uri;
    }

    public Uri getUri()
    {
        return uri;
    }
}
