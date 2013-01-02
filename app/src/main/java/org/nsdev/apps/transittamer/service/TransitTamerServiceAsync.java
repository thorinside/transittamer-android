package org.nsdev.apps.transittamer.service;

import retrofit.http.Callback;
import retrofit.http.GET;

import javax.inject.Named;
import java.util.List;

/**
 * Created by neal 12-12-15 3:36 PM
 */
public interface TransitTamerServiceAsync {
    @GET("/stops/{lon}/{lat}/{range}")
    void getNearestStops(@Named("lon")double longitude,
                         @Named("lat")double latitude,
                         @Named("range")double range,
                         Callback<StopDistancesResponse> callback);

    @GET("/stop/{route_id}/{lon}/{lat}")
    void getNearestStopForRoute(@Named("route_id") String routeId,
                                @Named("lon") double longitude,
                                @Named("lat") double latitude,
                                Callback<StopResponse> callback
                                );

    @GET("/stops/{route_id}")
    void getStopsForRoute(@Named("route_id") String routeId,
                          Callback<StopsResponse> callback);

    @GET("/stops/{route_id}")
    void getTerseStopsForRoute(@Named("route_id") String routeId,
                               @Named("terse") boolean terse,
                               Callback<StopsResponse> callback);

    @GET("/routes/{stop_code}")
    void getRoutesForStop(@Named("stop_code") String stopCode,
                          Callback<RoutesResponse> callback);

    @GET("/findroute/{route_short_name}")
    void getRoutesForShortName(@Named("route_short_name") String routeShortName,
                               Callback<RoutesResponse> callback);

    @GET("/findstop/{stop_code}")
    void getStopForStopCode(@Named("stop_code") String stopCode,
                            Callback<StopResponse> callback);


    @GET("/schedule/{serviceId}/{stop_code}/{route_id}")
    void getSchedule(@Named("service_id") String serviceId,
                     @Named("stop_code") String stopCode,
                     @Named("route_id") String routeId,
                     Callback<ScheduleResponse> callback);

    @GET("/schedule/{stop_code}/{route_id}")
    void getSchedule(@Named("stop_code") String stopCode,
                     @Named("route_id") String routeId,
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
    void getShape(@Named("route_id") String routeId, Callback<ShapeResponse> callback);

    @GET("/calendars/{year}/{month}/{day}")
    void getCalendars(@Named("year") int year,
                      @Named("month") int month,
                      @Named("day") int day,
                      Callback<List<Calendar>> callback);

    @GET("/exceptions/{date}")
    void getExceptions(@Named("date") String date,
                      Callback<Exceptions> callback);
}
