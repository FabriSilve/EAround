package lpaa.earound;

import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;


import java.util.ArrayList;


public class MapListener implements OnMarkerClickListener {

    private final static String TAG = "MapListener";

    private ArrayList<Event> events;
    private GoogleMap map;
    private View view;

    public MapListener (ArrayList<Event> events, GoogleMap map, View view) {
        this.events = events;
        this.map = map;
        this.view = view;
    }

    private boolean isNear(LatLng pos1, LatLng pos2) {
        double precision = 0.000001;
        if((pos1.latitude - pos2.latitude < precision) && (pos1.longitude - pos2.longitude < precision) &&
                (pos1.latitude - pos2.latitude > 0) && (pos1.longitude - pos2.longitude > 0)  )
            return true;
        if((pos2.latitude - pos1.latitude < precision) && (pos2.longitude - pos1.longitude < precision) &&
                (pos2.latitude - pos1.latitude > 0) && (pos2.longitude - pos1.longitude > 0))
            return true;
        if((pos1.latitude - pos2.latitude < precision) && (pos2.longitude - pos1.longitude < precision) &&
                (pos1.latitude - pos2.latitude > 0) && (pos2.longitude - pos1.longitude > 0))
            return true;
        if((pos2.latitude - pos1.latitude < precision) && (pos1.longitude - pos2.longitude < precision) &&
                (pos2.latitude - pos1.latitude > 0) && (pos1.longitude - pos2.longitude > 0))
            return true;
        return false;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(TAG, "onMarkerClick: ");
        for(Event event : events) {
            if (marker.getTitle().equals(event.getName())) {
                for(Event all : events) {
                    LinearLayout layout = (LinearLayout) view.findViewById(all.getId());
                    layout.requestFocus();
                    layout.setBackgroundResource(R.drawable.lightbg);
                }
                LinearLayout layout = (LinearLayout) view.findViewById(event.getId());
                layout.requestFocus();
                layout.setBackgroundResource(R.color.colorPrimary);
                return true;
            }
        }
        return false;
    }
}
