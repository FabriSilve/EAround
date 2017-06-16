package lpaa.earound;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

//TODO lavorare meglio con le dimensioni.
    //seekbar - bottoni - input - ecc

public class MainActivity extends Activity{

    //TODO quando ruoto il dispositivi che resti sullo stesso fragment

    private final String TAG = "MainActivity";

    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;

    private FragmentManager fragmentManager;
    private int container;
    private DBTask dbTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: start");
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: user access control");
        dbTask = new DBTask(getApplicationContext());
        UserData user = dbTask.getUser();
        if(user != null) {
            if (user.getKeep() == 1 && user.getUsername() != null) {
                goToHomeActivity();
            }
        }

        Log.d(TAG, "onCreate: init UI");
        setContentView(R.layout.main_activity);

        loginFragment = new LoginFragment();
        loginFragment.setActivity(this);
        registerFragment = new RegisterFragment();
        registerFragment.setActivity(this);
        container = R.id.MainActivity_frame;

        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container,loginFragment);
        fragmentTransaction.commit();

    }

    //TODO Ragrupare in un unico metodo
    public void goToRegister() {
        Log.d(TAG, "goToRegister: start");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, registerFragment);
        fragmentTransaction.commit();
    }

    public void goToLogin() {
        Log.d(TAG, "goToLogin: start");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, loginFragment);
        fragmentTransaction.commit();
    }

    public void correctlyLogged(boolean keep, String username) {
        Log.d(TAG, "correctlyLogged: start");
        if(keep) {
            dbTask.deleteUser();
            dbTask.insertUser(new UserData(1, username, null));
        }
        goToHomeActivity();
    }

    private void goToHomeActivity() {
        Log.d(TAG, "goToHomeActivity: start");
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged: start");
        super.onConfigurationChanged(newConfig);
    }
}
