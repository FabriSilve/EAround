package lpaa.earound;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class HomeActivity  extends Activity implements View.OnClickListener {

    //TODO aggiungere carica degli eventi nella zona e aggiunta in locale prima di inizializzare tutto

    private Button search;
    private Button home;
    private Button personal;

    private int container;

    private FragmentManager fragmentManager;

    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private PersonalFragment personalFragment;

    private ArrayList<Event> events;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
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
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void searchEvent() {
        EventSearcher eventSearcher = new EventSearcher(this,null, null);
        eventSearcher.execute();
    }
}
