package org.nsdev.apps.transittamer;

import android.net.http.AndroidHttpClient;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Provider;
import org.nsdev.apps.transittamer.service.TransitTamerServiceAsync;
import retrofit.http.GsonConverter;
import retrofit.http.RestAdapter;
import retrofit.http.Server;

/**
 * Created by neal 12-12-16 11:10 AM
 */
public class TransitTamerServiceProvider implements Provider<TransitTamerServiceAsync> {

    public static final Gson GSON = new GsonBuilder()
            .setDateFormat("yyyyMMdd")
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    private static RestAdapter restAdapter;
    private static TransitTamerServiceAsync service;

    @Override
    public TransitTamerServiceAsync get() {
        if (restAdapter == null) {
            restAdapter = new RestAdapter.Builder()
                    .setServer(new Server("http://nosuchdevice.nsdev.org:10240"))
                    .setClient(AndroidHttpClient.newInstance("TransitTamer-Android"))
                    .setConverter(new GsonConverter(GSON))
                    .build();
        }

        if (service == null) {
            service = restAdapter.create(TransitTamerServiceAsync.class);
        }

        return service;
    }
}
