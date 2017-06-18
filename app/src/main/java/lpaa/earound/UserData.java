package lpaa.earound;


import android.util.Log;

public class UserData {

    private final String TAG = "UserData";

    private String username;
    private String city;

    public UserData(String username, String city) {
        Log.d(TAG, "UserData: costructor");
        this.username = username;
        this.city = city;
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
