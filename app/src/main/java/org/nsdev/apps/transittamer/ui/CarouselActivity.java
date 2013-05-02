package org.nsdev.apps.transittamer.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Window;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.*;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationInfo;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibrary;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.viewpagerindicator.TitlePageIndicator;
import org.nsdev.apps.transittamer.BootstrapApplication;
import org.nsdev.apps.transittamer.R;
import org.nsdev.apps.transittamer.R.id;
import org.nsdev.apps.transittamer.TransitTamerServiceProvider;
import org.nsdev.apps.transittamer.service.*;
import retrofit.http.Callback;
import retrofit.http.RetrofitError;

import javax.inject.Inject;
import java.util.ArrayList;

/**
 * Activity to view the carousel and view pager indicator with fragments.
 */
public class CarouselActivity extends SherlockFragmentActivity
{

    protected TitlePageIndicator indicator;
    protected ViewPager pager;
    protected FrameLayout layout;

    protected SupportMapFragment mapFragment;
    protected boolean isMapExpanded;

    @Inject
    Bus bus;

    @Inject
    TransitTamerServiceProvider transitServiceProvider;


    private static final String TAG = "CarouselActivity";
    private Circle me;
    private ArrayList<Marker> stopMarkers = new ArrayList<Marker>();
    private ArrayList<Circle> stopCircles = new ArrayList<Circle>();

    private BroadcastReceiver locationReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            LocationInfo location = (LocationInfo) intent.getSerializableExtra("com.littlefluffytoys.littlefluffylocationlibrary.LocationInfo");
            bus.post(location);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.carousel_view);

        BootstrapApplication.inject(this);

        indicator = (TitlePageIndicator) findViewById(id.tpi_header);
        pager = (ViewPager) findViewById(id.vp_pages);
        layout = (FrameLayout) findViewById(id.fl_map_layout);

        pager.setAdapter(new BootstrapPagerAdapter(getResources(), getSupportFragmentManager()));

        indicator.setViewPager(pager);
        pager.setCurrentItem(0);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(id.smf_map);

        final GoogleMap map = mapFragment.getMap();

        if (map != null)
        {
            map.setMyLocationEnabled(false);
            UiSettings settings = map.getUiSettings();
            settings.setMyLocationButtonEnabled(true);
            settings.setZoomControlsEnabled(false);

            map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener()
            {
                @Override
                public void onCameraChange(CameraPosition cameraPosition)
                {
                    showStopMarkers(cameraPosition.zoom > 14);
                }
            });

            map.setOnMapClickListener(new GoogleMap.OnMapClickListener()
            {
                @Override
                public void onMapClick(LatLng latLng)
                {
                    if (!isMapExpanded)
                    {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                        params.weight = 4.0f;
                        layout.setLayoutParams(params);

                    }
                    else
                    {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                        params.weight = 0.5f;
                        layout.setLayoutParams(params);
                    }

                    isMapExpanded = !isMapExpanded;
                }
            });

            LocationInfo location = new LocationInfo(this.getBaseContext());
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.lastLat, location.lastLong), 16));
        }
    }

    private void showStopMarkers(boolean show)
    {
        for (Marker marker : stopMarkers)
        {
            marker.setVisible(show);
        }
        for (Circle circle : stopCircles)
        {
            circle.setVisible(!show);
        }
    }

    @Override
    protected void onResume()
    {
        Log.e("TAG", "onResume called.");
        bus.register(this);
        getApplicationContext().registerReceiver(locationReceiver, new IntentFilter("org.nsdev.apps.transittamer.littlefluffylocationlibrary.LOCATION_CHANGED"));
        LocationLibrary.forceLocationUpdate(getBaseContext());
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected void onPause()
    {
        Log.e("TAG", "onPause called.");

        bus.unregister(this);
        getApplicationContext().unregisterReceiver(locationReceiver);
        super.onPause();
    }

    @Subscribe
    public void onSignUpEvent(SignUpEvent event)
    {
        ParseUser user = ParseUser.getCurrentUser();
        if (!user.isAuthenticated())
        {
            Log.e("TransitTamer", "User is not authenticated.");
            user.setUsername("thorinside");
            user.setPassword("1transittamerword!");
            user.signUpInBackground(new SignUpCallback()
            {
                @Override
                public void done(ParseException e)
                {
                    if (e != null) Log.e("TransitTamer", "Error in signup.", e);
                    else Log.e("TransitTamer", "Sign Up Done.");
                }
            });
        }
        else
        {
            Log.e("TransitTamer", "User authenticated.");
        }
    }

    @Subscribe
    public void onScheduleLinkEvent(ScheduleLinkClickEvent event)
    {
        System.err.println("Got link click event.");
    }

    @Subscribe
    public void onToggleLocationEvent(ToggleLocationEvent event)
    {
        GoogleMap map = mapFragment.getMap();
        map.setMyLocationEnabled(!map.isMyLocationEnabled());
    }

    @Subscribe
    public void onLocationEvent(LocationInfo event)
    {
        final GoogleMap map = mapFragment.getMap();
        if (map != null)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(event.lastLat, event.lastLong), 16));
    }

    @Subscribe
    public void onRouteStopClicked(RouteStopClickedEvent event)
    {
        final Stop stop = event.getStop();
        final Route route = event.getRoute();

        final GoogleMap map = mapFragment.getMap();
        if (stop != null)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(stop.loc.lat, stop.loc.lon), 16));
        map.clear();
        stopMarkers.clear();
        stopCircles.clear();

        transitServiceProvider.get().getShape(route.routeId, new Callback<ShapeResponse>()
        {
            @Override
            public void success(ShapeResponse shapeResponse)
            {
                for (Shape shape : shapeResponse.shapes)
                {
                    ArrayList<LatLng> latlngs = new ArrayList<LatLng>();
                    for (Point point : shape.points)
                    {
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
            public void failure(RetrofitError error)
            {
                System.err.println(error);
            }
        });

        transitServiceProvider.get().getTerseStopsForRoute(route.routeId, true, new Callback<StopsResponse>()
        {
            @Override
            public void success(StopsResponse stopsResponse)
            {
                for (Stop s : stopsResponse.stops)
                {
                    if (stop != null && s.stopCode.equals(stop.stopCode)) continue;

                    LatLng stopPosition = new LatLng(s.loc.lat, s.loc.lon);
                    stopMarkers.add(
                            map.addMarker(new MarkerOptions()
                                    .title(s.stopCode)
                                    .position(stopPosition)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop_marker_green))
                                    .visible(true)
                                    .anchor(0.5f, 0.5f)
                            ));

                    stopCircles.add(
                            map.addCircle(new CircleOptions()
                                    .center(stopPosition)
                                    .fillColor(Color.GREEN)
                                    .strokeColor(Color.GREEN)
                                    .zIndex(100)
                                    .radius(8)
                                    .visible(false))
                    );


                }

                if (stop != null)
                {
                    map.addMarker(
                            new MarkerOptions()
                                    .title(stop.stopCode)
                                    .snippet(stop.stopName)
                                    .position(new LatLng(stop.loc.lat, stop.loc.lon))

                    );
                }
            }

            @Override
            public void failure(RetrofitError error)
            {
            }
        });

    }
}
