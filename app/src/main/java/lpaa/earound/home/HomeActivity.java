package lpaa.earound.home;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import lpaa.earound.R;
import lpaa.earound.service.LocationViewer;
import lpaa.earound.type.Search;
import lpaa.earound.database.DBTask;
import lpaa.earound.home.worker.EventSearcher;
import lpaa.earound.main.MainActivity;

//TODO implementare style.xml per stile app slide 07
//TODO sostituire async task con asynctask a tempo

public class HomeActivity  extends Activity implements View.OnClickListener {

    private final String TAG = "HomeActivity";
    private SharedPreferences homeValues;
    private LocationViewer locationViewer;

    private int container;

    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private MenuFragment menuFragment;
    private AddEventFragment addEventFragment;
    private MyEventsFragment myEventsFragment;
    private FollowedFragment followedFragment;
    private String currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        locationViewer = new LocationViewer(this);

        homeValues = getSharedPreferences("homeValues", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        Search search = new Search(
                    homeValues.getString("search_position", "genova"),
                    Integer.valueOf(homeValues.getString("search_distance", "5")),
                    Integer.valueOf(homeValues.getString("search_days", "5"))
            );
        searchEvent(search);
    }

    public LocationViewer getLocationViewer() {
        return locationViewer;
    }

    @Override
    public void onClick(View v) {
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
            case R.id.home_menuButton:
                currentFragment = "MENU";
                editor.putString("currentFragment", currentFragment);
                editor.apply();
                goTo(menuFragment);
                break;
        }
    }

    private void goTo(Fragment fragment) {
        if(fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(container, fragment);
            fragmentTransaction.commit();
        }
    }

    public void goToAddEvent() {
        currentFragment = "ADDEVENT";
        Editor editor = homeValues.edit();
        editor.putString("currentFragment", currentFragment);
        editor.apply();
        goTo(addEventFragment);
    }

    public void goToMyEvent() {
        currentFragment = "MYEVENT";
        Editor editor = homeValues.edit();
        editor.putString("currentFragment", currentFragment);
        editor.apply();
        goTo(myEventsFragment);
    }

    public void goToFollowed() {
        currentFragment = "FOLLOWED";
        Editor editor = homeValues.edit();
        editor.putString("currentFragment", currentFragment);
        editor.apply();
        goTo(followedFragment);
    }

    public void logoutUser() {
        new DBTask(this).clearDB();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        currentFragment = "HOME";
        onPause();
        this.finish();
    }

    public void searchEvent( Search search) {
        EventSearcher eventSearcher = new EventSearcher(this, search);
        eventSearcher.execute();
    }

    public void searchDone() {
        onResume();
    }

    public void postSearch() {
        Editor editor = homeValues.edit();
        editor.putString("currentFragment", "HOME");
        editor.apply();
    }

    private void initUI() {
        setContentView(R.layout.home_activity);

        Button search = (Button) findViewById(R.id.home_searchButton);
        Button home = (Button) findViewById(R.id.home_homeButton);
        Button menu = (Button) findViewById(R.id.home_menuButton);

        search.setOnClickListener(this);
        home.setOnClickListener(this);
        menu.setOnClickListener(this);

        container = R.id.home_container;

        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        menuFragment = new MenuFragment();
        addEventFragment = new AddEventFragment();
        myEventsFragment = new MyEventsFragment();
        followedFragment = new FollowedFragment();

        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, homeFragment);
        fragmentTransaction.commit();
        currentFragment = "HOME";
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        Editor editor = homeValues.edit();
        editor.putString("currentFragment", currentFragment);
        editor.apply();
        super.onPause();
    }

    @Override
    protected void onResume() {
        initUI();

        currentFragment = homeValues.getString("currentFragment", "HOME");
        switch (currentFragment) {
            case "MENU":
                goTo(menuFragment);
                break;
            case "SEARCH":
                goTo(searchFragment);
                break;
            case "ADDEVENT":
                goTo(addEventFragment);
                break;
            case "MYEVENT":
                goTo(myEventsFragment);
                break;
            case "FOLLOWED":
                goTo(followedFragment);
                break;
            default:
                goTo(homeFragment);
                break;
        }
        //TODO resumem va all'inizio?
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationViewer.start();
    }

    @Override
    protected void onStop() {
        locationViewer.stop();
        super.onStop();
    }

    protected void onPauseFragment(String key, String value) {
        Editor editor = homeValues.edit();
        editor.putString(key, value);
        editor.apply();
    }

    protected String onResumeFragment(String key, String valueDefault) {
        return homeValues.getString(key, valueDefault);
    }
}
