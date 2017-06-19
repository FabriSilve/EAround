package lpaa.earound;

import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraMoveListener;
import com.google.android.gms.maps.model.LatLng;



import java.util.ArrayList;


public class MapListener implements OnCameraMoveListener {

    private final static String TAG = "MapListener";

    private ArrayList<Event> events;
    private GoogleMap map;
    private View view;

    public MapListener (ArrayList<Event> events, GoogleMap map, View view) {
        this.events = events;
        this.map = map;
        this.view = view;
    }

    @Override
    public void onCameraMove() {
        LatLng camera = map.getCameraPosition().target;
        LatLng event = null;
        for(int i = 0; i < events.size(); i++) {
            event = events.get(i).getPosition();
            if (isNear(camera, event)) {
                LinearLayout layout = (LinearLayout) view.findViewById(i);
                layout.requestFocus();
                layout.setBackgroundResource(R.color.colorPrimary);
                Log.d(TAG, "onCameraMove: onMove");
                break;
            }
        }
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
}
