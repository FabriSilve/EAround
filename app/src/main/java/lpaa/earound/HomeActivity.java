package lpaa.earound;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import java.util.ArrayList;


public class HomeActivity  extends Activity implements View.OnClickListener {

    private final String TAG = "HomeActivity";
    //TODO aggiungere carica degli eventi nella zona e aggiunta in locale prima di inizializzare tutto

    private Button search;
    private Button home;
    private Button personal;

    private int container;

    private FragmentManager fragmentManager;

    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private PersonalFragment personalFragment;

    private LocationFinder locationFinder;
    private Location location;
    private ArrayList<Event> events = new ArrayList<>();

    public HomeActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        initLocationFinder();
        searchEvent();

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

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.home_searchButton:
                goTo(searchFragment);
                break;
            case R.id.home_homeButton:
                goTo(homeFragment);
                break;
            case R.id.home_personalButton:
                goTo(personalFragment);
                break;
        }
    }

    public void goToHome() {
        goTo(homeFragment);
    }

    private void goTo(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, fragment);
        fragmentTransaction.commit();
    }

    public void logoutUser() {
        DBTask dbTask = new DBTask(getApplicationContext());
        dbTask.deleteUser();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
        homeFragment.eventDrawer();
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void searchEvent() {
        EventSearcher eventSearcher = new EventSearcher(this,null, null);
        eventSearcher.execute();
    }

    private void initLocationFinder() {
        locationFinder = new LocationFinder(this, (LocationManager) getSystemService(LOCATION_SERVICE));
        if(!locationFinder.isEable()) {
            Log.d(TAG, "initLocationFinder: internet disable");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initLocationFinder();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationFinder.removeUpdate();
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
