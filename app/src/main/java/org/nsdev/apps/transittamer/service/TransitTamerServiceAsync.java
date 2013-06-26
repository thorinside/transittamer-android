package org.nsdev.apps.transittamer.service;

import retrofit.http.Callback;
import retrofit.http.GET;
import retrofit.http.Name;
import java.util.List;

/**
 * Created by neal 12-12-15 3:36 PM
 */
public interface TransitTamerServiceAsync {
    @GET("/stops/{lon}/{lat}/{range}")
    void getNearestStops(@Name("lon")double longitude,
                         @Name("lat")double latitude,
                         @Name("range")double range,
                         Callback<StopDistancesResponse> callback);

    @GET("/stop/{route_id}/{lon}/{lat}")
    void getNearestStopForRoute(@Name("route_id") String routeId,
                                @Name("lon") double longitude,
                                @Name("lat") double latitude,
                                Callback<StopResponse> callback
                                );

    @GET("/stops/{route_id}")
    void getStopsForRoute(@Name("route_id") String routeId,
                          Callback<StopsResponse> callback);

    @GET("/stops/{route_id}")
    void getTerseStopsForRoute(@Name("route_id") String routeId,
                               @Name("terse") boolean terse,
                               Callback<StopsResponse> callback);

    @GET("/routes/{stop_code}")
    void getRoutesForStop(@Name("stop_code") String stopCode,
                          Callback<RoutesResponse> callback);

    @GET("/findroute/{route_short_name}")
    void getRoutesForShortName(@Name("route_short_name") String routeShortName,
                               Callback<RoutesResponse> callback);

    @GET("/findstop/{stop_code}")
    void getStopForStopCode(@Name("stop_code") String stopCode,
                            Callback<StopResponse> callback);


    @GET("/schedule/{serviceId}/{stop_code}/{route_id}")
    void getSchedule(@Name("service_id") String serviceId,
                     @Name("stop_code") String stopCode,
                     @Name("route_id") String routeId,
                     Callback<ScheduleResponse> callback);

    @GET("/schedule/{stop_code}/{route_id}")
    void getSchedule(@Name("stop_code") String stopCode,
                     @Name("route_id") String routeId,
                     Callback<ScheduleResponse> callback);

    @GET("/calendars")
    void getAllCalendars(Callback<CalendarsResponse> callback);

    @GET("/service")
    void getService(Callback<ServicesResponse> callback);

    @GET("/calendar")
    void getCurrentCalendars(Callback<CalendarsResponse> callback);

    @GET("/agency")
    void getAgency(Callback<AgencyResponse> callback);

    @GET("/shape/{route_id}")
    void getShape(@Name("route_id") String routeId, Callback<ShapeResponse> callback);

    @GET("/calendars/{year}/{month}/{day}")
    void getCalendars(@Name("year") int year,
                      @Name("month") int month,
                      @Name("day") int day,
                      Callback<List<Calendar>> callback);

    @GET("/exceptions/{date}")
    void getExceptions(@Name("date") String date,
                      Callback<Exceptions> callback);
}
