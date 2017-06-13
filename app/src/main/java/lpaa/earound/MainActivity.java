package lpaa.earound;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Fabrizio on 07/06/2017.
 */

public class MainActivity extends Activity{

    private static String TAG = "MainActivity";

    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;

    private FragmentManager fragmentManager;
    private int container;
    private DBTask dbTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbTask = new DBTask(getApplicationContext());
        Log.d(TAG, "onCreate: check user keep");
        UserData user = dbTask.getUser();
        if(user != null) {
            Log.d(TAG, "onCreateView: keep" + user.getKeep());
            Log.d(TAG, "onCreateView: username" + user.getUsername());
            if (user.getKeep() == 1 && user.getUsername() != null) {
                goToHomeActivity();
            }
        }

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

    public void goToRegister() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, registerFragment);
        fragmentTransaction.commit();
    }

    public void goToLogin() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, loginFragment);
        fragmentTransaction.commit();
    }

    public void correctlyLogged(boolean keep, String username) {
        if(keep) {
            dbTask.deleteUser();
            dbTask.insertUser(new UserData(1, username, null));
        }
        goToHomeActivity();
    }

    private void goToHomeActivity() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }
}
