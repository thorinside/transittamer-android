package org.nsdev.apps.transittamer;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.nsdev.apps.transittamer.service.TransitTamerServiceAsync;
import retrofit.http.android.AndroidApacheClient;
import retrofit.http.GsonConverter;
import retrofit.http.RestAdapter;
import retrofit.http.Server;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

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

    @Inject
    @Singleton
    public TransitTamerServiceProvider() {

    }

    @Override
    public TransitTamerServiceAsync get() {
        if (restAdapter == null) {
            restAdapter = new RestAdapter.Builder()
                     .setServer(new Server("http://nosuchdevice.nsdev.org:10240"))
                    //.setServer(new Server("http://192.168.0.10:10240"))
                    .setClient(new AndroidApacheClient())
                    .setConverter(new GsonConverter(GSON))
                    .build();
        }

        if (service == null) {
            service = restAdapter.create(TransitTamerServiceAsync.class);
        }

        return service;
    }
}
