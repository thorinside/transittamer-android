package org.nsdev.apps.transittamer.ui;

import android.graphics.Color;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.inject.Inject;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationInfo;
import org.nsdev.apps.transittamer.R;
import org.nsdev.apps.transittamer.TransitTamerServiceProvider;
import org.nsdev.apps.transittamer.service.*;
import retrofit.http.Callback;
import retrofit.http.RetrofitError;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectFragment;

import java.util.ArrayList;

import static org.nsdev.apps.transittamer.core.Constants.Extra.ROUTE;
import static org.nsdev.apps.transittamer.core.Constants.Extra.STOP;

public class TransitMapActivity extends BootstrapActivity {

    @InjectFragment(R.id.smf_map) protected SupportMapFragment mapFragment;

    @InjectExtra(STOP) protected Stop stop;
    @InjectExtra(ROUTE) protected Route route;

    @Inject protected TransitTamerServiceProvider transitTamerServiceProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.transit_map_view);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //avatarLoader.bind(avatar, user);
        //name.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));

        final GoogleMap map = mapFragment.getMap();
        map.setMyLocationEnabled(true);
        LocationInfo location = new LocationInfo(this.getBaseContext());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.lastLat, location.lastLong), 14));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(stop.loc.lat, stop.loc.lon), 16));

        transitTamerServiceProvider.getService().getShape(route.routeId, new Callback<ShapeResponse>() {
            @Override
            public void success(ShapeResponse shapeResponse) {
                for (Shape shape : shapeResponse.shapes)
                {
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

        transitTamerServiceProvider.getService().getTerseStopsForRoute(route.routeId, true, new Callback<StopsResponse>() {
            @Override
            public void success(StopsResponse stopsResponse) {
                for (Stop stop : stopsResponse.stops) {

                    if (stop.stopCode.equals(TransitMapActivity.this.stop.stopCode)) continue;

                    map.addMarker(new MarkerOptions()
                            .title(stop.stopCode)
                            .position(new LatLng(stop.loc.lat, stop.loc.lon))
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
