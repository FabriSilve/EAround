package lpaa.earound.type;

import android.util.Log;

public class Search {

    private static final String TAG = "Search";
    private String position;
    private int distance;
    private int days;
    private double lat;
    private double lon;


    public Search(String position, int distance, int days) {
        Log.d(TAG, "Search: costructor");
        this.position = position;
        this.distance = distance;
        this.days = days;
        this.lat = 0;
        this.lon = 0;
    }

    public Search(String position, int distance, int days, double lat, double lon) {
        Log.d(TAG, "Search: costructor");
        this.position = position;
        this.distance = distance;
        this.days = days;
        this.lat = lat;
        this.lon = lon;
    }

    public String getPosition() {
        return position;
    }

    public String getDistance() {
        return String.valueOf(distance);
    }

    public String getDays() {
        return String.valueOf(days);
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
