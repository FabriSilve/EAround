package lpaa.earound;

import android.app.Fragment;
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

        view = inflater.inflate(R.layout.login_fragment, container, false);

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
    }

    public void checkResult(boolean result) {
        if(result) {
            Log.d("Sign", "sign_login_click: signed");
            Toast toast = Toast.makeText(view.getContext(), getText(R.string.logged), Toast.LENGTH_SHORT);
            toast.show();

            main.correctlyLogged(keep, user);
        } else {
            Log.d("Sign", "sign_login_click: not signed");
            Toast toast = Toast.makeText(view.getContext(), getText(R.string.error), Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
    }

    private void registerClick() {
        main.goToRegister();
    }
}
