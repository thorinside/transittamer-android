package org.nsdev.apps.transittamer.core;

import com.google.inject.Provider;
import com.squareup.otto.Bus;

/**
 * Created by neal 12-12-16 11:10 AM
 */
public class BusProvider implements Provider<Bus> {

    private static Bus bus;

    @Override
    public Bus get() {

        if (bus == null) {
            bus = new Bus();
        }

        return bus;
    }
}
