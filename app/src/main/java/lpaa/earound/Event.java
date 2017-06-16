package lpaa.earound;

import android.media.Image;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;


public class Event {

    private int id;
    private String name;
    private String description;
    private Date date;
    private String address;
    //private EventType type;
    private double lat;
    private double lon;
    private String image;


    public Event(int id, String name, String description, Date date, String address/*, String type*/, double lat, double lon, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.address = address;
        //this.type = EventType.valueFromString(type);
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

    public String getAddress() {
        return address;
    }

    /*public String getType() {
        switch(type) {
            case CULTURAL:
                return "Cultural";
            case PARTY:
                return "Party";
            case MUSIC:
                return "Music";
            case SPORT:
                return "Sport";
            default:
                return "";
        }
    }*/

    public LatLng getPosition() {
        return new LatLng(lat, lon);
    }

    public String getImage() {
        return image;
    }
}
