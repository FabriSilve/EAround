package lpaa.earound;


import android.view.View;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MapDrawer implements OnMapReadyCallback {

    private View view;
    private GoogleMap map;
    private HomeFragment homeFragment;

    public MapDrawer(HomeFragment homeFragment, View view, GoogleMap map) {
        this.homeFragment = homeFragment;
        this.view = view;
        this.map = map;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //TODO utilizzare posizione dispositivo
        //TODO disegnare gli eventi salvati in locale

        map = googleMap;

        //LocationManager locationManager = (LocationManager) view.getContext().getSystemService(Context.LOCATION_SERVICE);

        // For dropping a marker at a point on the Map
        LatLng center;
        //if(homeFragment.getParent().getLocation() == null)
            center = new LatLng(44.424704,8.849104);
        /*else
            center = new LatLng(homeFragment.getParent().getLocation().getLatitude(),homeFragment.getParent().getLocation().getLongitude());
        */


        map.addMarker(new MarkerOptions().position(center).title(view.getContext().getString(R.string.youAreHere)));

        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(center).zoom(16).build();

        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        /* animazione zoom camera
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
         */
        ArrayList<Event> events = homeFragment.getParentEvents();
        for(int i = 0; i<events.size(); i++) {
            map.addMarker(
                    new MarkerOptions()
                            .position(events.get(i).getPosition())
                            .title(events.get(i).getName()));
        }
    }
}
