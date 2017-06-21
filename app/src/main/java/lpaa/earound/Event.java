package lpaa.earound;

import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import java.util.Date;


public class Event {

    private final String TAG = "Event";

    private int id;
    private String name;
    private String description;
    private Date date;
    private double lat;
    private double lon;


    public Event(int id, String name, String description, Date date, double lat, double lon) {
        Log.d(TAG, "Event: costructor");
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.lat = lat;
        this.lon = lon;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public LatLng getPosition() { return new LatLng(lat, lon); }
}
