package lpaa.earound.type;

import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import java.util.Date;


public class Event {

    private final String TAG = "Event";

    private int id;
    private String name;
    private String description;
    private Date day;
    private String address;
    private double lat;
    private double lon;
    private String owner;


    public Event(int id, String name, String description, Date day, String address, double lat, double lon, String owner) {
        Log.d(TAG, "Event: costructor");
        this.id = id;
        this.name = name;
        this.description = description;
        this.day = day;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.owner = owner;

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

    public Date getDay() {
        return day;
    }

    public String getDayString() { return String.valueOf(day); }

    public String getAddress() { return address; }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public LatLng getPosition() { return new LatLng(lat, lon); }

    public String getOwner() { return owner; }
}
