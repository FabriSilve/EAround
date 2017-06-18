package lpaa.earound;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


public class HomeActivity  extends Activity implements View.OnClickListener {

    private final String TAG = "HomeActivity";
    private SharedPreferences homeValues;
    //TODO aggiungere carica degli eventi nella zona e aggiunta in locale prima di inizializzare tutto
    //TODO quando ruoto il dispositivi che resti sullo stesso fragment

    private Button search;
    private Button home;
    private Button personal;

    private int container;

    private FragmentManager fragmentManager;

    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private PersonalFragment personalFragment;
    private String currentFragment;

    //private LocationFinder locationFinder;
    //private Location location;
    private ArrayList<Event> events = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: start");
        homeValues = getSharedPreferences("homeValues", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        //TODO studiare localizzazione dispositivo e inizializzare location finder
        //initLocationFinder();
        searchEvent();

        Log.d(TAG, "onCreate: init UI");
        search = (Button) findViewById(R.id.home_searchButton);
        home = (Button) findViewById(R.id.home_homeButton);
        personal = (Button) findViewById(R.id.home_personalButton);

        search.setOnClickListener(this);
        home.setOnClickListener(this);
        personal.setOnClickListener(this);

        container = R.id.home_container;

        homeFragment = new HomeFragment();
        homeFragment.setParent(this);
        searchFragment = new SearchFragment();
        searchFragment.setParent(this);
        personalFragment = new PersonalFragment();
        personalFragment.setParent(this);

        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, homeFragment);
        fragmentTransaction.commit();
        currentFragment = "HOME";

    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: start");
        switch(v.getId()) {
            case R.id.home_searchButton:
                goTo(searchFragment);
                currentFragment = "SEARCH";
                break;
            case R.id.home_homeButton:
                goTo(homeFragment);
                currentFragment = "HOME";
                break;
            case R.id.home_personalButton:
                goTo(personalFragment);
                currentFragment = "PERSONAL";
                break;
        }
    }

    public void goToHome() {
        Log.d(TAG, "goToHome: start");
        goTo(homeFragment);
        currentFragment = "HOME";
    }

    private void goTo(Fragment fragment) {
        Log.d(TAG, "goTo: start");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, fragment);
        fragmentTransaction.commit();
    }

    public void logoutUser() {
        Log.d(TAG, "logoutUser: start");
        //DBTask dbTask = new DBTask(getApplicationContext());
        //dbTask.deleteUser();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    /*public void setEvents(ArrayList<Event> events) {
        Log.d(TAG, "setEvents: start");
        if(events.size() == 0) {
            Toast toast = Toast.makeText(this, getText(R.string.eventsNotFound), Toast.LENGTH_LONG);
            toast.show();
        }
        this.events = events;
        homeFragment.eventDrawer();
    }*/

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void updateEvents(ArrayList<Event> events) {
        DBTask dbTask = new DBTask(getApplicationContext());
        dbTask.updateEvents(events);
    }

    public void searchEvent() {
        Log.d(TAG, "searchEvent: start");
        //this.events = new DBTask(getApplicationContext()).getEvents();
        EventSearcher eventSearcher = new EventSearcher(this,null, null);
        eventSearcher.execute();
    }

    /*private void initLocationFinder() {
        /*locationFinder = new LocationFinder(this, (LocationManager) getSystemService(LOCATION_SERVICE));
        if(!locationFinder.isEable()) {
            new Toast().makeText(this, getText(R.string.notConnected), Toast.LENGTH_LONG);
            Log.d(TAG, "initLocationFinder: internet disable");
        }
    }*/

    /*public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }*/

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged: start");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: start");
        Editor editor = homeValues.edit();
        editor.putString("currentFragment", currentFragment);
        editor.apply();
        super.onPause();
    }

    @Override
    protected void onResume() {
        //TODO to complete
        currentFragment = homeValues.getString("currentFragment", "HOME");
        switch (currentFragment) {
            case "PERSONAL":
                goTo(personalFragment);
                break;
            case "SEARCH":
                goTo(searchFragment);
                break;
            default:
                goTo(homeFragment);
                break;
        }
        super.onResume();
    }

    protected void onPauseFragment(String key, String value) {
        Log.d(TAG, "onPauseFragment: ");
        Editor editor = homeValues.edit();
        editor.putString(key, value);
        editor.apply();
    }

    protected String onResumeFragment(String key, String valueDefault) {
        Log.d(TAG, "onResumeFragment: ");
        return homeValues.getString(key, valueDefault);
    }

    //TODO far si che il fragment iniziale sia sempre home
    /*@Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        Editor editor = homeValues.edit();
        editor.putString("currentFragment", "HOME");
        editor.apply();
        super.onStop();
    }*/
}
