package org.nsdev.apps.transittamer.ui;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationInfo;
import com.squareup.otto.Bus;
import org.nsdev.apps.transittamer.BootstrapApplication;
import org.nsdev.apps.transittamer.R;
import org.nsdev.apps.transittamer.TransitTamerServiceProvider;
import org.nsdev.apps.transittamer.service.TransitTamerServiceAsync;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NearestRouteStopFragment extends ItemListFragment<NearestRouteStopInfo> implements DataUpdatedCallback, LocationService {

    @Inject
    TransitTamerServiceProvider transitServiceProvider;
    @Inject
    Bus bus;

    private LocationInfo location;
    private String queryText = null;

    private static final String TAG = "NearestRouteStopFragment";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BootstrapApplication.inject(this);
        //setEmptyText(R.string.no_users);
    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        //getListAdapter().addHeader(activity.getLayoutInflater()
        //                .inflate(R.layout.user_list_item_labels, null));
    }

    @Override
    public void onCreateOptionsMenu(Menu optionsMenu, MenuInflater inflater) {
        super.onCreateOptionsMenu(optionsMenu, inflater);

        // Hook into the seearch action.
        final SearchView searchView = (SearchView) optionsMenu.findItem(R.id.menu_search).getActionView();
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        searchView.setQueryHint(getResources().getString(R.string.search_query_hint_bus_number));
        searchView.setSubmitButtonEnabled(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                queryText = s;
                refreshWithProgress();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.show_my_location) {
            bus.post(new ToggleLocationEvent());
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader<List<NearestRouteStopInfo>> onCreateLoader(int id, Bundle args) {

        return new ThrowableLoader<List<NearestRouteStopInfo>>(getActivity(), items) {

            @Override
            public List<NearestRouteStopInfo> loadData() throws Exception {

                ArrayList<NearestRouteStopInfo> items = new ArrayList<NearestRouteStopInfo>();

                if (queryText != null) {

                    TransitTamerServiceAsync service = transitServiceProvider.get();

                    items.add(new NearestRouteStopInfo(getContext(), queryText, service, NearestRouteStopFragment.this, NearestRouteStopFragment.this));
                }

                return items;
            }
        };

    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        NearestRouteStopInfo info = (NearestRouteStopInfo) l.getItemAtPosition(position);

        if (info.stop != null && info.routes != null) {
            bus.post(new RouteStopClickedEvent(info.stop, info.routes.get(0)));
        }
    }

    @Override
    public void onLoadFinished(Loader<List<NearestRouteStopInfo>> loader, List<NearestRouteStopInfo> items) {
        sortItems(items);
        super.onLoadFinished(loader, items);

    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_users;
    }

    @Override
    protected SingleTypeAdapter<NearestRouteStopInfo> createAdapter(List<NearestRouteStopInfo> items) {
        sortItems(items);
        return new NearestRouteStopAdapter(getActivity().getLayoutInflater(), R.layout.route_stop_schedule, items);
    }

    private void sortItems(List<NearestRouteStopInfo> items) {
        Collections.sort(items, new Comparator<NearestRouteStopInfo>() {
            @Override
            public int compare(NearestRouteStopInfo lhs, NearestRouteStopInfo rhs) {
                if (lhs.stop == null || rhs.stop == null || location == null)
                    return 0;

                Location l1 = new Location("internal");
                l1.setLatitude(lhs.stop.loc.lat);
                l1.setLongitude(lhs.stop.loc.lon);

                Location l2 = new Location("internal");
                l2.setLatitude(rhs.stop.loc.lat);
                l2.setLongitude(rhs.stop.loc.lon);

                Location me = new Location("internal");
                me.setLatitude(location.lastLat);
                me.setLongitude(location.lastLong);

                double distl1 = me.distanceTo(l1);
                double distl2 = me.distanceTo(l2);

                if (distl1 > distl2)
                    return 1;
                if (distl1 < distl2)
                    return -1;

                lhs.processSchedules();
                rhs.processSchedules();

                // Distances are the same, check the next bus time next.
                if (lhs.nextBusTime > rhs.nextBusTime)
                    return 1;
                if (lhs.nextBusTime < rhs.nextBusTime)
                    return -1;

                return 0;
            }
        });

    }

    @Override
    public void dataUpdated() {
        Log.e(TAG, "dataUpdated()");
    }

    @Override
    public LocationInfo getLocation() {
        Log.e(TAG, "getLocation()");
        if (getActivity() != null) {
            location = new LocationInfo(getActivity().getBaseContext());
        }
        return location;
    }
}
