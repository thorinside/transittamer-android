package org.nsdev.apps.transittamer.service;

import java.io.Serializable;

/**
 * Created by neal 12-12-15 5:04 PM
 */
public class Stop implements Serializable {
    public String stopId;
    public String stopCode;
    public String stopName;
    public String stopDesc;
    public String zoneId;
    public String stopUrl;
    public String locationType;
    public Location loc;
}
