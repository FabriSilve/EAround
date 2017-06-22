package lpaa.earound.type;

import android.util.Log;
import java.util.Date;

public class LocalEvent {

    private final String TAG = "LocalEvent";

    private String name;
    private String description;
    private Date day;
    private String address;


    public LocalEvent(String name, String description, Date day, String address) {
        Log.d(TAG, "LocalEvent: costructor");
        this.name = name;
        this.description = description;
        this.day = day;
        this.address = address;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDay() { return day; }

    public String getDayString() { return String.valueOf(day); }

    public String getAddress() { return address; }
}

