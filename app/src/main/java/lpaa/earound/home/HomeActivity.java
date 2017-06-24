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
import lpaa.earound.type.Search;
import lpaa.earound.database.DBTask;
import lpaa.earound.home.worker.EventSearcher;
import lpaa.earound.main.MainActivity;

//TODO implementare style.xml per stile app slide 07
//TODO sostituire async task con asynctask a tempo

public class HomeActivity  extends Activity implements View.OnClickListener {

    private final String TAG = "HomeActivity";
    private SharedPreferences homeValues;

    private int container;

    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private PersonalFragment personalFragment;
    private AddEventFragment addEventFragment;
    private MyEventsFragment myEventsFragment;
    private String currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: start");
        homeValues = getSharedPreferences("homeValues", MODE_PRIVATE);
        super.onCreate(savedInstanceState);

        Search search = new Search(
                homeValues.getString("search_position", "genova"),
                Integer.valueOf(homeValues.getString("search_distance", "1")),
                Integer.valueOf(homeValues.getString("search_days", "1"))
        );

        searchEvent(search);
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

    private void goTo(Fragment fragment) {
        Log.d(TAG, "goTo: start");
        if(fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(container, fragment);
            fragmentTransaction.commit();
        }
    }

    public void goToAddEvent() {
        Log.d(TAG, "goToAddEvent: ");
        currentFragment = "ADDEVENT";
        /*Editor editor = homeValues.edit();
        editor.putString("currentFragment", currentFragment);
        editor.apply();*/
        goTo(addEventFragment);
    }

    public void goToMyEvent() {
        Log.d(TAG, "goToMyEvent: ");
        currentFragment = "MYEVENT";
        goTo(myEventsFragment);
    }

    public void logoutUser() {
        Log.d(TAG, "logoutUser: start");
        new DBTask(this).clearDB();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        currentFragment = "HOME";
        onPause();
        this.finish();
    }

    public void searchEvent( Search search) {
        Log.d(TAG, "searchEvent: start");
        EventSearcher eventSearcher = new EventSearcher(this, search);
        eventSearcher.execute();
    }

    public void searchDone() {
        Log.d(TAG, "searchDone: start");
        onResume();
    }

    public void postSearch() {
        Editor editor = homeValues.edit();
        editor.putString("currentFragment", "HOME");
        editor.apply();
    }

    private void initUI() {
        setContentView(R.layout.home_activity);

        Log.d(TAG, "onCreate: init UI");
        Button search = (Button) findViewById(R.id.home_searchButton);
        Button home = (Button) findViewById(R.id.home_homeButton);
        Button personal = (Button) findViewById(R.id.home_personalButton);

        search.setOnClickListener(this);
        home.setOnClickListener(this);
        personal.setOnClickListener(this);

        container = R.id.home_container;

        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        personalFragment = new PersonalFragment();
        addEventFragment = new AddEventFragment();
        myEventsFragment = new MyEventsFragment();

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
        initUI();

        currentFragment = homeValues.getString("currentFragment", "HOME");
        switch (currentFragment) {
            case "PERSONAL":
                goTo(personalFragment);
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
}
