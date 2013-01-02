package org.nsdev.apps.transittamer.service;

import java.io.Serializable;

/**
 * Created by neal 12-12-15 8:50 PM
 */
public class Schedule implements Serializable {
    public String tripId;
    public String arrivalTime;
    public String departureTime;
    public String stopId;
    public String stopSequence;
    public String pickupType;
    public String dropOffType;
}
