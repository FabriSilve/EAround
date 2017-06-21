package lpaa.earound;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MapDrawer implements OnMapReadyCallback {

    private final String TAG = "MapDrawer";

    private View view;
    private GoogleMap map;
    private HomeFragment homeFragment;
    private HomeActivity parent;

    //TODO serve homefragment?
    public MapDrawer(HomeFragment homeFragment, View view, GoogleMap map, HomeActivity parent) {
        Log.d(TAG, "MapDrawer: costructor");
        this.homeFragment = homeFragment;
        this.view = view;
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

            //map.addMarker(new MarkerOptions().position(center).title(events.get(0).getName()));

            CameraPosition cameraPosition = new CameraPosition.Builder().target(center).zoom(14).build();
            map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            for (int i = 1; i < events.size(); i++) {
                map.addMarker(
                        new MarkerOptions()
                                .position(events.get(i).getPosition())
                                .title(events.get(i).getName()));
            }
        }


        /*TODO migliorare map listener o non usare
        MapListener mapListener = new MapListener(events, map, view);
        map.setOnMarkerClickListener(mapListener);*/
    }
}
