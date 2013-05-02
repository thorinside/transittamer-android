package org.nsdev.apps.transittamer.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import org.nsdev.apps.transittamer.R;

/**
 * Pager adapter
 */
public class BootstrapPagerAdapter extends FragmentPagerAdapter
{

    private final Resources resources;

    /**
     * Create pager adapter
     *
     * @param resources
     * @param fragmentManager
     */
    public BootstrapPagerAdapter(Resources resources, FragmentManager fragmentManager)
    {
        super(fragmentManager);
        this.resources = resources;
    }

    @Override
    public int getCount()
    {
        return 2;
    }

    @Override
    public Fragment getItem(int position)
    {
        Bundle bundle = new Bundle();
        switch (position)
        {
            case 0:
                RouteLookupFragment routeLookupFragment = new RouteLookupFragment();
                routeLookupFragment.setArguments(bundle);
                return routeLookupFragment;
            case 1:
                NearestRouteStopFragment nearestRouteStopFragment = new NearestRouteStopFragment();
                nearestRouteStopFragment.setArguments(bundle);
                return nearestRouteStopFragment;
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
                return resources.getString(R.string.page_route_lookup);
            case 1:
                return resources.getString(R.string.page_nearest_route_stops);
            default:
                return null;
        }
    }
}
