package lpaa.earound;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;


public class LocationFinder implements LocationListener {

    private final String TAG = "LocationFinder";

    private LocationManager locationManager;
    private HomeActivity parent;
    private static final int MIN_DIST = 100;
    private static final int MIN_PERIOD = 60000;

    public LocationFinder(HomeActivity parent, LocationManager locationManager) {
        this.parent = parent;
        this.locationManager = locationManager;
    }

    @Override
    public void onLocationChanged(Location location) {
        parent.setLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public boolean isEable() {
        if (ActivityCompat.checkSelfPermission(parent.getBaseContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "isEable: not permission");
            return false;
        }
        if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return false;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 200, this);
        return true;
    }

    public void removeUpdate() {
        locationManager.removeUpdates(this);
    }
}
