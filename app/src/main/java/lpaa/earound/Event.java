package lpaa.earound;

import android.media.Image;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;


public class Event {

    private int id;
    private String name;
    private String description;
    private Date date;
    private double lat;
    private double lon;
    private String image;

    public Event(int id, String name, String description, Date date, double lat, double lon, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.lat = lat;
        this.lon = lon;
        this.image = image;
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

    public LatLng getPosition() {
        return new LatLng(lat, lon);
    }

    public String getImage() {
        return image;
    }
}
