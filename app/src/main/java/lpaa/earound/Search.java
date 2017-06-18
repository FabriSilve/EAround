package lpaa.earound;


import android.util.Log;

class Search {

    private final String TAG = "Search";
    private String position;
    private int distance;
    private int days;

    Search(String position, int distance, int days) {
        Log.d(TAG, "Search: costructor");
        this.position = position;
        this.distance = distance;
        this.days = days;
    }

    String getPosition() {
        return position;
    }

    String getDistance() {
        return String.valueOf(distance);
    }

    String getDays() {
        return String.valueOf(days);
    }

}
