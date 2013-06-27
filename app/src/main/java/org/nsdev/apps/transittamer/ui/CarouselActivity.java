package org.nsdev.apps.transittamer.ui;

import android.app.ProgressDialog;
import android.content.*;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Window;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.plus.PlusClient;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.viewpagerindicator.TitlePageIndicator;
import org.nsdev.apps.transittamer.BootstrapApplication;
import org.nsdev.apps.transittamer.R;
import org.nsdev.apps.transittamer.R.id;
import org.nsdev.apps.transittamer.TransitTamerServiceProvider;
import org.nsdev.apps.transittamer.service.*;
import org.nsdev.apps.transittamer.service.Location;
import retrofit.http.Callback;
import retrofit.http.RetrofitError;
import retrofit.http.client.Response;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Activity to view the carousel and view pager indicator with fragments.
 */
public class CarouselActivity extends SherlockFragmentActivity
        implements GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        LocationListener
{

    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    protected TitlePageIndicator indicator;
    protected ViewPager pager;

    protected SupportMapFragment mapFragment;
    protected boolean isMapExpanded;
    protected LocationClient locationClient;
    protected SlidingPaneLayout slidingPaneLayout;

    private ProgressDialog mConnectionProgressDialog;
    private PlusClient mPlusClient;
    private ConnectionResult mConnectionResult;

    @Inject
    Bus bus;

    @Inject
    TransitTamerServiceProvider transitServiceProvider;


    private static final String TAG = "CarouselActivity";
    private Circle me;
    private ArrayList<Marker> stopMarkers = new ArrayList<Marker>();
    private ArrayList<Circle> stopCircles = new ArrayList<Circle>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.carousel_view);

        BootstrapApplication.inject(this);

        final String scopes = Scopes.PLUS_LOGIN + " " + Scopes.PLUS_PROFILE + " " + "https://www.googleapis.com/auth/drive.appdata";

        mPlusClient = new PlusClient.Builder(this, new GooglePlayServicesClient.ConnectionCallbacks()
        {
            @Override
            public void onConnected(Bundle bundle)
            {
                final String accountName = mPlusClient.getAccountName();
                Toast.makeText(CarouselActivity.this, accountName + " is connected.", Toast.LENGTH_LONG).show();

                AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>()
                {
                    @Override
                    protected Void doInBackground(Void... voids)
                    {
                        HttpTransport httpTransport = new NetHttpTransport();
                        JsonFactory jsonFactory = new GsonFactory();

                        Bundle appActivities = new Bundle();
                        appActivities.putString(GoogleAuthUtil.KEY_REQUEST_VISIBLE_ACTIVITIES,
                                "http://schemas.google.com/AddActivity http://schemas.google.com/BuyActivity");

                        String token = null;
                        try
                        {
                            token = GoogleAuthUtil.getToken(CarouselActivity.this, accountName,
                                    "oauth2:" + scopes);
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        catch (GoogleAuthException e)
                        {
                            e.printStackTrace();
                        }

                        if (token == null)
                        {
                            Log.e(TAG, "No Token!");
                            return null;
                        }
                        else
                        {
                            Log.e(TAG, "Token: " + token);
                        }

                        GoogleCredential credential = new GoogleCredential().setAccessToken(token);

                        //Create a new authorized API client
                        Drive service = new Drive.Builder(httpTransport, jsonFactory, credential).build();

                        com.google.api.client.util.Lists;

                        try
                        {
                            service.files().touch("config.json");
                            service.files().list();
                        } catch (IOException ex) {
                            Log.e(TAG, "Unable to write", ex);
                        }
                        return null;
                    }
                };

                task.execute();

            }

            @Override
            public void onDisconnected()
            {
                Log.d(TAG, "disconnected");
            }
        }, this).setVisibleActivities("http://schemas.google.com/AddActivity", "http://schemas.google.com/BuyActivity")
                .setScopes()
                .build();

        // Progress bar to be displayed if the connection failure is not resolved.
        mConnectionProgressDialog = new ProgressDialog(this);
        mConnectionProgressDialog.setMessage("Signing in...");

        locationClient = new LocationClient(this, this, this);

        indicator = (TitlePageIndicator) findViewById(id.tpi_header);
        pager = (ViewPager) findViewById(id.vp_pages);

        pager.setAdapter(new BootstrapPagerAdapter(getResources(), getSupportFragmentManager()));

        indicator.setViewPager(pager);
        pager.setCurrentItem(1);

        slidingPaneLayout = (SlidingPaneLayout)findViewById(id.sliding_pane_layout);
        slidingPaneLayout.openPane();
        slidingPaneLayout.setSliderFadeColor(Color.TRANSPARENT);
        slidingPaneLayout.setCoveredFadeColor(Color.TRANSPARENT);

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
                    /*
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
                    */
                }
            });

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
        Log.d(TAG, "onResume called.");
        bus.register(this);
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected void onPause()
    {
        Log.d(TAG, "onPause called.");

        bus.unregister(this);
        super.onPause();
    }

    @Subscribe
    public void onSignUpEvent(SignUpEvent event)
    {
        if (!mPlusClient.isConnected()) {
            if (mConnectionResult == null) {
                mConnectionProgressDialog.show();
            } else {
                try {
                    mConnectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                } catch (IntentSender.SendIntentException e) {
                    // Try connecting again.
                    mConnectionResult = null;
                    Log.d(TAG, "onSignUpEvent catch block");
                    mPlusClient.connect();
                }
            }
        }
    }

    @Subscribe
    public void onSignOutEvent(SignOutEvent event)
    {
        if (mPlusClient.isConnected()) {
            mPlusClient.clearDefaultAccount();

            mPlusClient.revokeAccessAndDisconnect(new PlusClient.OnAccessRevokedListener() {
                @Override
                public void onAccessRevoked(ConnectionResult status) {
                    // mPlusClient is now disconnected and access has been revoked.
                    // Trigger app logic to comply with the developer policies
                    mPlusClient.disconnect();
                    mPlusClient.connect();
                }
            });

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
    public void onLocationEvent(Location event)
    {
        final GoogleMap map = mapFragment.getMap();
        if (map != null)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(event.lat, event.lon), 16));
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

        slidingPaneLayout.closePane();

        transitServiceProvider.get().getShape(route.routeId, new Callback<ShapeResponse>()
        {
            @Override
            public void success(ShapeResponse shapeResponse, Response response)
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
            public void success(StopsResponse stopsResponse, Response response)
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

    @Override
    protected void onStart()
    {
        super.onStart();
        locationClient.connect();
        Log.d(TAG, "onStart: " + (mConnectionResult == null));
        if (mConnectionResult == null)
            mPlusClient.connect();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        locationClient.disconnect();
        Log.d(TAG, "onStop: " + (mConnectionResult == null));
        mPlusClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle)
    {
        android.location.Location location = locationClient.getLastLocation();

        if (location != null)
            bus.post(location);

        LocationRequest request = LocationRequest.create()
                .setFastestInterval(15 * 1000)
                .setInterval(60 * 1000)
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        locationClient.requestLocationUpdates(request, this);
    }

    @Override
    public void onDisconnected()
    {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                * Thrown if Google Play services canceled the original
                * PendingIntent
                */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
                mPlusClient.connect();
                locationClient.connect();
                Log.d(TAG, "onConnectionFailed Catch Block");
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            // showErrorDialog(connectionResult.getErrorCode());
        }

        mConnectionResult = connectionResult;
        
    }

    @Override
    public void onLocationChanged(android.location.Location location)
    {
        bus.post(location);
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data)
    {
        if (requestCode == CONNECTION_FAILURE_RESOLUTION_REQUEST && responseCode == RESULT_OK) {
            mConnectionResult = null;
            Log.d(TAG, "onActivityResult");
            mPlusClient.connect();
        }
        else
        {
            Log.d(TAG, "Cancelled " + (responseCode == RESULT_CANCELED));
        }
    }
}
