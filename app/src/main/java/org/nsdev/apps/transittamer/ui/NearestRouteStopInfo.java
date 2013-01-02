package org.nsdev.apps.transittamer.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import android.content.Context;
import android.util.Log;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationInfo;
import org.nsdev.apps.transittamer.R;
import org.nsdev.apps.transittamer.service.*;
import retrofit.http.Callback;
import retrofit.http.RetrofitError;

public class NearestRouteStopInfo
{
    private final Context context;
    private static final int NUMBER_OF_TIMES = 3;
    private static final String TAG = "NearestRouteStopInfo";
    Stop stop;
    String routeShortName;
    List<Schedule> schedules;
    List<Route> routes;
    long nextBusTime;
    transient TransitTamerServiceAsync service;
    transient LocationService locationService;
    transient DataUpdatedCallback callback;
    transient CountDownLatch loadComplete = new CountDownLatch(1);

    public NearestRouteStopInfo(Context context, String routeShortName, TransitTamerServiceAsync service, LocationService locationService, DataUpdatedCallback callback) throws InterruptedException {
        this.context = context;
        this.routeShortName = routeShortName;
        localizedDateFormat = android.text.format.DateFormat.getTimeFormat(this.context.getApplicationContext());
        this.service = service;
        this.locationService = locationService;
        this.callback = callback;
        findRoute(routeShortName);
        loadComplete.await();
    }

    private void findRoute(final String routeName)
    {
        service.getRoutesForShortName(routeName, new Callback<RoutesResponse>() {
            @Override
            public void success(RoutesResponse routesResponse) {
                Log.e(TAG, "Got routes: " + routesResponse.routes);
                routes = routesResponse.routes;

                updateNearestStop();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                loadComplete.countDown();
            }
        });
    }

    public String getRouteNumber()
    {
        return routeShortName;
    }

    public String getStopCode()
    {
        if (stop == null)
            return null;
        return stop.stopCode;
    }

    public String getStopName()
    {
        if (stop == null)
            return null;
        return stop.stopName;
    }

    public String getSchedule()
    {
        if (schedules == null)
            return null;
        return processSchedules();
    }

    public long getNextBusTime()
    {
        return nextBusTime;
    }

    public void updateNearestStop()
    {
        LocationInfo location = locationService.getLocation();
        final float longitude = location.lastLong;
        final float latitude = location.lastLat;

        Log.e(TAG, "U are at "+latitude+","+longitude);

        if (routes != null)
        {
            for (final Route route : routes) {

                Log.e(TAG, "Finding nearest stop for route "+route.routeId+" lon="+longitude+" lat="+latitude);


                service.getNearestStopForRoute(route.routeId, longitude, latitude, new Callback<StopResponse>() {
                    @Override
                    public void success(StopResponse stopResponse) {
                        if (stopResponse.stop != null) {
                            stop = stopResponse.stop;
                            Log.e(TAG, "Found stop for route: " + route.routeId + " Stop=" + stop.stopCode);
                            loadSchedule(route);
                        } else {
                            Log.e(TAG, "StopResponse.stop is null");
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        Log.e(TAG, "Error for URL " + retrofitError.getUrl());
                        Log.e(TAG, "Exception", retrofitError.getException());
                        loadComplete.countDown();
                    }
                });


            }
        }

    }

    private void loadSchedule(final Route route)
    {
        service.getSchedule(stop.stopCode, route.routeId, new Callback<ScheduleResponse>() {
            @Override
            public void success(ScheduleResponse scheduleResponse) {
                schedules = scheduleResponse.times;
                callback.dataUpdated();
                loadComplete.countDown();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                loadComplete.countDown();
            }
        });
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
    java.text.DateFormat localizedDateFormat;

    public String processSchedules()
    {
        Date now = new Date();

        int count = 0;

        StringBuilder sb = new StringBuilder();

        String previousDate = null;

        if (schedules != null)
        {

            for (Schedule schedule: schedules)
            {
                String timeStr = schedule.departureTime;
                Date date = null;

                String day = dayFormat.format(now);

                try
                {
                    date = dateFormat.parse(day + " " + timeStr);
                }
                catch (ParseException ex)
                {
                    continue;
                }

                timeStr = localizedDateFormat.format(date);

                if (date.before(now))
                    previousDate = timeStr;

                if (date.after(now) && count++ < NUMBER_OF_TIMES)
                {
                    if (sb.length() > 0)
                        sb.append("  |  ");
                    if (previousDate != null)
                    {
                        sb.append(previousDate);
                        previousDate = null;
                        sb.append("  \u00BB  ");
                        nextBusTime = date.getTime();
                    }
                    sb.append(timeStr);
                }
            }
        }

        if (sb.length() == 0 && schedules != null)
        {
            sb.append(context.getString(R.string.no_service));
            nextBusTime = Long.MAX_VALUE;
        }
        else if (schedules == null)
        {
            // An error occurred
            sb.append(context.getString(R.string.data_error));
        }

        return sb.toString();
    }

}