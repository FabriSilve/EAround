package lpaa.earound.home.worker;

import android.util.Log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;

import lpaa.earound.home.HomeActivity;
import lpaa.earound.home.HomeFragment;
import lpaa.earound.type.Event;
import lpaa.earound.database.DBTask;


public class MapDrawer implements OnMapReadyCallback {

    private final String TAG = "MapDrawer";

    private GoogleMap map;
    private HomeFragment homeFragment;
    private HomeActivity parent;

    public MapDrawer(HomeFragment homeFragment, GoogleMap map, HomeActivity parent) {
        Log.d(TAG, "MapDrawer: costructor");
        this.homeFragment = homeFragment;
        this.map = map;
        this.parent = parent;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: start");
        //TODO utilizzare posizione dispositivo

        map = googleMap;
        homeFragment.setMap(map);
        ArrayList<Event> events = new DBTask(parent).getEvents();

        Log.d(TAG, "onMapReady: eventi acquisiti");

        if(events != null && events.size() > 0) {
            LatLng center = new LatLng(events.get(0).getLat(), events.get(0).getLon());
            CameraPosition cameraPosition = new CameraPosition.Builder().target(center).zoom(14).build();
            map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            for (int i = 1; i < events.size(); i++) {
                map.addMarker(
                        new MarkerOptions()
                                .position(events.get(i).getPosition())
                                .title(events.get(i).getName()));
            }
        }
    }
}
