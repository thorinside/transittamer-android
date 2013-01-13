package org.nsdev.apps.transittamer.ui;

import org.nsdev.apps.transittamer.service.Route;
import org.nsdev.apps.transittamer.service.Stop;

/**
 * Created by neal 13-01-12 9:45 PM
 */
public class RouteStopClickedEvent {
    private final Stop stop;
    private final Route route;

    public RouteStopClickedEvent(Stop stop, Route route) {
        this.stop = stop;
        this.route = route;
    }

    public Stop getStop() {
        return stop;
    }

    public Route getRoute() {
        return route;
    }
}
