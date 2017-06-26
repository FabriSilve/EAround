package lpaa.earound.service;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class LocationViewer implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    private final String TAG = "LocationViewer";
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    //TODO da aumentare
    private static final int UPDATE_INTERVAL = 30000;
    private static final int FASTEST_UPDATE_INTERVAL = 10000;

    private Activity parent;
    private GoogleApiClient goolGoogleApiClient;
    private LocationRequest locationRequest;
    private double lat = 0.0;
    private double lon = 0.0;

    public LocationViewer(Activity parent) {
        Log.d(TAG, "LocationViewer: ");
        this.parent = parent;
        goolGoogleApiClient = new GoogleApiClient.Builder(parent)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL);
    }

    public void start() {
        Log.d(TAG, "start: ");
        goolGoogleApiClient.connect();
    }

    public void stop() {
        Log.d(TAG, "stop: ");
        goolGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected: ");
        if (ActivityCompat.checkSelfPermission(parent, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(parent, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "onConnected: permission request");
            return;
        }
        Log.d(TAG, "onConnected: have permission");
        Location location = LocationServices.FusedLocationApi
                .getLastLocation(goolGoogleApiClient);
        if(location != null) {
            lat = location.getLatitude();
            lon = location.getLongitude();
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(goolGoogleApiClient, locationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended: ");
        if(goolGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(goolGoogleApiClient, this);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: ");
        if(connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(parent, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch(IntentSender.SendIntentException e) {
                Log.e(TAG, "onConnectionFailed: ",e );
            }
        } else {
            new AlertDialog.Builder(parent).setMessage("Connection failed. Error code: "+connectionResult.getErrorCode()).show();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged: ");
        lat = location.getLatitude();
        lon = location.getLongitude();
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
