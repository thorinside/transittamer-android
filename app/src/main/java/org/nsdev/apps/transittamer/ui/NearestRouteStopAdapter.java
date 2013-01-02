package org.nsdev.apps.transittamer.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.location.Location;
import android.view.LayoutInflater;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationInfo;
import org.nsdev.apps.transittamer.R;

public class NearestRouteStopAdapter extends SingleTypeAdapter<NearestRouteStopInfo>
{
    public NearestRouteStopAdapter(LayoutInflater inflater, int layoutResourceId, List<NearestRouteStopInfo> items) {
        super(inflater, layoutResourceId);
        setItems(items);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[] { R.id.busRouteText, R.id.stopNameText, R.id.scheduleText, R.id.stopCodeText };
    }

    @Override
    protected void update(int position, NearestRouteStopInfo item) {
        setText(R.id.busRouteText, item.getRouteNumber());
        setText(R.id.stopCodeText, item.getStopCode());
        setText(R.id.scheduleText, item.getSchedule());
        setText(R.id.stopNameText, item.getStopName());
    }

    @Override
    public void notifyDataSetChanged()
    {
        super.notifyDataSetChanged();
    }
}
