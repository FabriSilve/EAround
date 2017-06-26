package lpaa.earound.main;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import lpaa.earound.home.HomeActivity;
import lpaa.earound.R;
import lpaa.earound.database.DBTask;


public class MainActivity extends Activity{

    private final String TAG = "MainActivity";
    private SharedPreferences mainValues;
    private String currentFragment;

    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;

    private FragmentManager fragmentManager;
    private int container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainValues = getSharedPreferences("MainValues", MODE_PRIVATE);
        super.onCreate(savedInstanceState);

        String user = new DBTask(this).getUser();
        if(!user.equals(""))
            goToHomeActivity();

        setContentView(R.layout.main_activity);

        loginFragment = new LoginFragment();
        registerFragment = new RegisterFragment();

        container = R.id.MainActivity_frame;

        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container,loginFragment);
        fragmentTransaction.commit();
        currentFragment = "LOG";

    }

    public void goToRegister() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, registerFragment);
        fragmentTransaction.commit();
        currentFragment = "REG";
    }

    public void goToLogin() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, loginFragment);
        fragmentTransaction.commit();
        currentFragment = "LOG";
    }

    public void correctlyLogged(boolean keep, String username) {
        if(keep) {
            new DBTask(this).insertUser(username);
        }
        //TODO sostituire gli argomenti con un array
        clearFragment("register_username", "register_password1", "register_password2", "register_email1", "register_email2");
        goToHomeActivity();
    }

    private void goToHomeActivity() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        this.finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        Editor editor = mainValues.edit();
        editor.putString("currentFragment", currentFragment);
        editor.apply();
        super.onPause();
    }

    @Override
    protected void onResume() {
        currentFragment = mainValues.getString("currentFragment", "LOG");
        switch (currentFragment) {
            case "REG":
                goToRegister();
                break;
            default:
                goToLogin();
                break;
        }
        super.onResume();
    }


    protected void onPauseFragment(String key, String value) {
        Editor editor = mainValues.edit();
        editor.putString(key, value);
        editor.apply();
    }

    protected String onResumeFragment(String key, String valueDefault) {
        return mainValues.getString(key, valueDefault);
    }

    protected void clearFragment(String... ids) {
        Editor editor = mainValues.edit();
        for(String id : ids) {
            editor.putString(id, "");
        }
        editor.apply();
    }
}
