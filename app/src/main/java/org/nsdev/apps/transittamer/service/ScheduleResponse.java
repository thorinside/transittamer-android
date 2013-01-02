package org.nsdev.apps.transittamer.service;

import java.util.List;

/**
 * Created by neal 12-12-15 8:49 PM
 */
public class ScheduleResponse {

    public int ok;
    public List<String> service;
    public List<Schedule> times;
}
