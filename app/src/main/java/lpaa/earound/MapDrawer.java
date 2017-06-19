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

        LatLng center = new LatLng(44.424704,8.849104);

        map.addMarker(new MarkerOptions().position(center).title(view.getContext().getString(R.string.youAreHere)));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(center).zoom(13).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        ArrayList<Event> events = new DBTask(parent).getEvents();
        for(int i = 0; i<events.size(); i++) {
            map.addMarker(
                    new MarkerOptions()
                            .position(events.get(i).getPosition())
                            .title(events.get(i).getName()));
        }

        /*TODO migliorare map listener o non usare
        MapListener mapListener = new MapListener(events, map, view);
        map.setOnMarkerClickListener(mapListener);*/
    }
}
