package lpaa.earound;


import android.view.View;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapDrawer implements OnMapReadyCallback {

    private View view;
    private GoogleMap map;

    public MapDrawer(View view, GoogleMap map) {
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
        LatLng center = new LatLng(44.424704,8.849104);
        map.addMarker(new MarkerOptions().position(center).title(view.getContext().getString(R.string.youAreHere)));

        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(center).zoom(16).build();

        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        /* animazione zoom camera
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
         */

    }
}
