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

//TODO implementare style.xml per stile app slide 07

public class HomeActivity  extends Activity implements View.OnClickListener {

    private final String TAG = "HomeActivity";
    private SharedPreferences homeValues;
    //TODO aggiungere carica degli eventi nella zona e aggiunta in locale prima di inizializzare tutto

    private Button search;
    private Button home;
    private Button personal;

    private int container;

    private FragmentManager fragmentManager;

    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private PersonalFragment personalFragment;
    private String currentFragment;

    private ArrayList<Event> events = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: start");
        homeValues = getSharedPreferences("homeValues", MODE_PRIVATE);
        super.onCreate(savedInstanceState);

        //TODO CORREGERE searching layout con slide 05
        setContentView(R.layout.searching_layout);

        searchEvent(null);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: start");
        Editor editor = homeValues.edit();
        switch(v.getId()) {
            case R.id.home_searchButton:
                currentFragment = "SEARCH";
                editor.putString("currentFragment", currentFragment);
                editor.apply();
                goTo(searchFragment);
                break;
            case R.id.home_homeButton:
                currentFragment = "HOME";
                editor.putString("currentFragment", currentFragment);
                editor.apply();
                goTo(homeFragment);
                break;
            case R.id.home_personalButton:
                currentFragment = "PERSONAL";
                editor.putString("currentFragment", currentFragment);
                editor.apply();
                goTo(personalFragment);
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
        if(fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(container, fragment);
            fragmentTransaction.commit();
        }
    }

    public void logoutUser() {
        Log.d(TAG, "logoutUser: start");
        //TODO inserire dati user in db per renderli accessibili a tutte le activity
        //DBTask dbTask = new DBTask(getApplicationContext());
        //dbTask.deleteUser();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));

    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void searchEvent( Search search) {
        Log.d(TAG, "searchEvent: start");
        EventSearcher eventSearcher = new EventSearcher(this, search);
        eventSearcher.execute();
    }

    public void searchDone() {
        Log.d(TAG, "searchDone: aggiungo eventi nel DB");
        //TODO add events to DB
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.home_activity);

        Log.d(TAG, "onCreate: init UI");
        search = (Button) findViewById(R.id.home_searchButton);
        home = (Button) findViewById(R.id.home_homeButton);
        personal = (Button) findViewById(R.id.home_personalButton);

        search.setOnClickListener(this);
        home.setOnClickListener(this);
        personal.setOnClickListener(this);

        container = R.id.home_container;

        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        personalFragment = new PersonalFragment();

        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, homeFragment);
        fragmentTransaction.commit();
        currentFragment = "HOME";

    }

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
        Log.d(TAG, "onResume: start");
        //TODO to complete
        //initUI();

        super.onResume();

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
}
