package lpaa.earound;


import android.content.Context;
import android.util.Log;
import android.view.View;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
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
        //TODO disegnare gli eventi salvati in locale

        map = googleMap;

        LatLng center = new LatLng(44.424704,8.849104);

        map.addMarker(new MarkerOptions().position(center).title(view.getContext().getString(R.string.youAreHere)));

        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(center).zoom(16).build();

        // camera non animata
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        //animazione zoom camera
        //map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        ArrayList<Event> events = new DBTask(parent).getEvents();
        for(int i = 0; i<events.size(); i++) {
            //TODO debug
            map.addMarker(
                    new MarkerOptions()
                            .position(events.get(i).getPosition())
                            .title(events.get(i).getName()));
        }
    }
}
