package lpaa.earound;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginFragment extends Fragment implements View.OnClickListener {

    private final String TAG = "LoginFragment";

    private MainActivity main;
    private View view;

    private EditText username;
    private EditText password;
    private CheckBox rememberMe;
    private Button login;
    private TextView register;

    private String user;
    private boolean keep;

    public void setActivity(MainActivity main) {
        this.main = main;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: start");
        view = inflater.inflate(R.layout.login_fragment, container, false);

        Log.d(TAG, "onCreateView: init UI");
        username = (EditText) view.findViewById(R.id.login_username);
        password = (EditText) view.findViewById(R.id.login_password);
        rememberMe = (CheckBox) view.findViewById(R.id.login_rememberMe);
        login = (Button) view.findViewById(R.id.login_login);
        register = (TextView) view.findViewById(R.id.login_register);

        login.setOnClickListener(this);
        register.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: start");
        switch(v.getId()) {
            case R.id.login_login:
                loginClick();
                break;
            case R.id.login_register:
                registerClick();
                break;
        }
    }

    private void loginClick() {
        Log.d(TAG, "loginClick: start");
        String user = username.getText().toString();
        String pass = password.getText().toString();
        boolean keep = rememberMe.isChecked();

        if(user.trim().isEmpty()) {
            username.setError(getText(R.string.empty_field));
            username.requestFocus();
            return;
        }
        if(pass.trim().isEmpty()) {
            password.setError(getText(R.string.empty_field));
            password.requestFocus();
            return;
        }

        this.user = user;
        this.keep = rememberMe.isChecked();

        CheckLogin checker = new CheckLogin(this, user, pass);
        checker.execute();
        Toast toast = Toast.makeText(view.getContext(), getText(R.string.sending), Toast.LENGTH_SHORT);
        toast.show();
    }

    public void checkResult(boolean result) {
        Log.d(TAG, "checkResult: start");
        if(result) {
            Log.d("Sign", "sign_login_click: signed");
            Toast toast = Toast.makeText(view.getContext(), getText(R.string.logged), Toast.LENGTH_SHORT);
            toast.show();

            main.correctlyLogged(keep, user);
        } else {
            Log.d("Sign", "sign_login_click: not signed");
            Toast toast = Toast.makeText(view.getContext(), getText(R.string.error), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void registerClick() {
        Log.d(TAG, "registerClick: start");
        main.goToRegister();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged: start");
        super.onConfigurationChanged(newConfig);
    }
}
