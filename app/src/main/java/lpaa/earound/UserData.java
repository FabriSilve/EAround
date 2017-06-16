package lpaa.earound;


import android.util.Log;

public class UserData {

    private final String TAG = "UserData";

    private int keep;
    private String username;
    private String city;

    public UserData(int keep, String username, String city) {
        Log.d(TAG, "UserData: costructor");
        this.keep = keep;
        this.username = username;
        this.city = city;
    }

    public void setKeep(int keep) {
        this.keep = keep;
    }

    public int getKeep() {
        return keep;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}
