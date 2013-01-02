

package org.nsdev.apps.transittamer.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import org.nsdev.apps.transittamer.R;

/**
 * Pager adapter
 */
public class BootstrapPagerAdapter extends FragmentPagerAdapter {

    private final Resources resources;

    /**
     * Create pager adapter
     *
     * @param resources
     * @param fragmentManager
     */
    public BootstrapPagerAdapter(Resources resources, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.resources = resources;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        switch (position) {
        case 0:
            NearestRouteStopFragment fragment = new NearestRouteStopFragment();
            fragment.setArguments(bundle);
            return fragment;
        case 1:
            UserListFragment userListFragment = new UserListFragment();
            userListFragment.setArguments(bundle);
            return userListFragment;
        case 2:
            CheckInsListFragment checkInsFragment = new CheckInsListFragment();
            checkInsFragment.setArguments(bundle);
            return checkInsFragment;
        default:
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
        case 0:
            return resources.getString(R.string.page_nearest_route_stops);
        case 1:
            return resources.getString(R.string.page_users);
        case 2:
            return resources.getString(R.string.page_checkins);
        default:
            return null;
        }
    }
}
