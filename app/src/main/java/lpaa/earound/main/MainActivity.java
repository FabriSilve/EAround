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
        Log.d(TAG, "onCreate: start");
        mainValues = getSharedPreferences("MainValues", MODE_PRIVATE);
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: user access control");
        String user = new DBTask(this).getUser();
        if(!user.equals(""))
            goToHomeActivity();

        Log.d(TAG, "onCreate: init UI");
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

    //TODO Ragrupare in un unico metodo
    public void goToRegister() {
        Log.d(TAG, "goToRegister: start");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, registerFragment);
        fragmentTransaction.commit();
        currentFragment = "REG";
    }

    public void goToLogin() {
        Log.d(TAG, "goToLogin: start");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, loginFragment);
        fragmentTransaction.commit();
        currentFragment = "LOG";
    }

    public void correctlyLogged(boolean keep, String username) {
        Log.d(TAG, "correctlyLogged: start");
        if(keep) {
            new DBTask(this).insertUser(username);
        }
        clearFragment("register_username", "register_password1", "register_password2", "register_email1", "register_email2");
        goToHomeActivity();
    }

    private void goToHomeActivity() {
        Log.d(TAG, "goToHomeActivity: start");
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        this.finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged: start");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: start");
        Editor editor = mainValues.edit();
        editor.putString("currentFragment", currentFragment);
        editor.apply();
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: start");
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
        Log.d(TAG, "onPauseFragment: ");
        Editor editor = mainValues.edit();
        editor.putString(key, value);
        editor.apply();
    }

    protected String onResumeFragment(String key, String valueDefault) {
        Log.d(TAG, "onResumeFragment: ");
        return mainValues.getString(key, valueDefault);
    }

    protected void clearFragment(String... ids) {
        Log.d(TAG, "clearLogin: ");
        Editor editor = mainValues.edit();
        for(String id : ids) {
            editor.putString(id, "");
        }
        editor.apply();
    }
}