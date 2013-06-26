package org.nsdev.apps.transittamer.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.actionbarsherlock.app.SherlockFragment;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import org.nsdev.apps.transittamer.BootstrapApplication;
import org.nsdev.apps.transittamer.R;
import org.nsdev.apps.transittamer.TransitTamerServiceProvider;
import org.nsdev.apps.transittamer.service.Route;
import org.nsdev.apps.transittamer.service.RoutesResponse;
import retrofit.http.Callback;
import retrofit.http.RetrofitError;
import retrofit.http.client.Response;

import javax.inject.Inject;

/**
 * Created by neal 13-05-01 9:41 PM
 */
public class RouteLookupFragment extends SherlockFragment
{
    @Inject
    Bus bus;

    @Inject
    TransitTamerServiceProvider serviceProvider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.route_lookup_view, null);

        final EditText text = (EditText) view.findViewById(R.id.edittext_route_number);
        Button findButton = (Button) view.findViewById(R.id.button_find);
        findButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                serviceProvider.get().getRoutesForShortName(text.getText().toString(), new Callback<RoutesResponse>()
                {
                    @Override
                    public void success(RoutesResponse routesResponse, Response response)
                    {
                        bus.post(routesResponse);
                    }

                    @Override
                    public void failure(RetrofitError error)
                    {
                    }
                });

            }
        });

        return view;
    }

    @Subscribe
    public void onRoutesResponse(RoutesResponse routesResponse)
    {
        for (Route route : routesResponse.routes)
        {
            bus.post(new RouteStopClickedEvent(null, route));
        }
    }

    @Override
    public void onAttach(Activity activity)
    {
        BootstrapApplication.inject(this);
        super.onAttach(activity);
        bus.register(this);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        bus.unregister(this);
    }
}
