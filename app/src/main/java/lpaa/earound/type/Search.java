package lpaa.earound.type;

import android.util.Log;

public class Search {

    private final String TAG = "Search";
    private String position;
    private int distance;
    private int days;

    public Search(String position, int distance, int days) {
        Log.d(TAG, "Search: costructor");
        this.position = position;
        this.distance = distance;
        this.days = days;
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

}
