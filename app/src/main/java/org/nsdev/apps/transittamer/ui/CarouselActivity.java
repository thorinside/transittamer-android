package org.nsdev.apps.transittamer.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import com.actionbarsherlock.view.Window;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.inject.Inject;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationInfo;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibrary;
import com.squareup.otto.Subscribe;
import com.viewpagerindicator.TitlePageIndicator;
import org.nsdev.apps.transittamer.R;
import org.nsdev.apps.transittamer.R.id;
import org.nsdev.apps.transittamer.TransitTamerServiceProvider;
import org.nsdev.apps.transittamer.core.BusProvider;
import org.nsdev.apps.transittamer.service.*;
import retrofit.http.Callback;
import retrofit.http.RetrofitError;
import roboguice.inject.InjectFragment;
import roboguice.inject.InjectView;

import java.util.ArrayList;

/**
 * Activity to view the carousel and view pager indicator with fragments.
 */
public class CarouselActivity extends RoboSherlockFragmentActivity {

    @InjectView(id.tpi_header)
    protected TitlePageIndicator indicator;
    @InjectView(id.vp_pages)
    protected ViewPager pager;
    @InjectFragment(R.id.smf_map)
    protected SupportMapFragment mapFragment;
    @Inject
    private BusProvider bus;
    @Inject
    private TransitTamerServiceProvider transitServiceProvider;

    private static final String TAG = "CarouselActivity";
    private LocationInfo location;

    private BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            location = (LocationInfo) intent.getSerializableExtra("com.littlefluffytoys.littlefluffylocationlibrary.LocationInfo");
            Log.e(TAG, "Location updated: " + location);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.carousel_view);

        pager.setAdapter(new BootstrapPagerAdapter(getResources(), getSupportFragmentManager()));

        indicator.setViewPager(pager);
        pager.setCurrentItem(0);

        final GoogleMap map = mapFragment.getMap();
        map.setMyLocationEnabled(true);
        LocationInfo location = new LocationInfo(this.getBaseContext());
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.lastLat, location.lastLong), 16));
    }

    @Override
    protected void onResume() {
        bus.get().register(this);
        getApplicationContext().registerReceiver(locationReceiver, new IntentFilter("org.nsdev.apps.transittamer.littlefluffylocationlibrary.LOCATION_CHANGED"));
        LocationLibrary.forceLocationUpdate(getBaseContext());
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected void onPause() {
        bus.get().unregister(this);
        getApplicationContext().unregisterReceiver(locationReceiver);
        super.onPause();
    }

    @Subscribe
    public void onRouteStopClicked(RouteStopClickedEvent event) {
        final Stop stop = event.getStop();
        final Route route = event.getRoute();

        final GoogleMap map = mapFragment.getMap();
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(stop.loc.lat, stop.loc.lon), 16));
        map.clear();

        transitServiceProvider.get().getShape(route.routeId, new Callback<ShapeResponse>() {
            @Override
            public void success(ShapeResponse shapeResponse) {
                for (Shape shape : shapeResponse.shapes) {
                    ArrayList<LatLng> latlngs = new ArrayList<LatLng>();
                    for (Point point : shape.points) {
                        latlngs.add(new LatLng(point.loc.lat, point.loc.lon));
                    }
                    map.addPolyline(
                            new PolylineOptions()
                                    .addAll(latlngs)
                                    .color(Color.BLUE)
                                    .width(7)
                    );
                }
            }

            @Override
            public void failure(RetrofitError error) {
                System.err.println(error);
            }
        });

        transitServiceProvider.get().getTerseStopsForRoute(route.routeId, true, new Callback<StopsResponse>() {
            @Override
            public void success(StopsResponse stopsResponse) {
                for (Stop s : stopsResponse.stops) {

                    if (s.stopCode.equals(stop.stopCode)) continue;

                    map.addMarker(new MarkerOptions()
                            .title(s.stopCode)
                            .position(new LatLng(s.loc.lat, s.loc.lon))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop_marker_green))
                            .anchor(0.5f, 0.5f)
                    );
                }

                map.addMarker(
                        new MarkerOptions()
                                .title(stop.stopCode)
                                .snippet(stop.stopName)
                                .position(new LatLng(stop.loc.lat, stop.loc.lon))

                );

            }

            @Override
            public void failure(RetrofitError error) {
            }
        });

    }
}
