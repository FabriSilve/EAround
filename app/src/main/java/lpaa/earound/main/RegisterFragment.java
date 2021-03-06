package lpaa.earound.main;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import lpaa.earound.R;
import lpaa.earound.main.worker.CheckRegistration;


public class RegisterFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {

    private final String TAG = "RegisterFragment";

    private MainActivity parent;

    private EditText username;
    private EditText password1;
    private EditText password2;
    private EditText email1;
    private EditText email2;

    private String user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.parent = (MainActivity) this.getActivity();

        View view = inflater.inflate(R.layout.register_fragment, container, false);

        Button back = (Button) view.findViewById(R.id.register_back);
        username = (EditText) view.findViewById(R.id.register_username);
        password1 = (EditText) view.findViewById(R.id.register_password1);
        password2 = (EditText) view.findViewById(R.id.register_password2);
        email1 = (EditText) view.findViewById(R.id.register_email1);
        email2 = (EditText) view.findViewById(R.id.register_email2);
        Button register = (Button) view.findViewById(R.id.register_register);

        back.setOnClickListener(this);
        register.setOnClickListener(this);

        //TODO estrarre listener
        username.getOnFocusChangeListener();
        email1.getOnFocusChangeListener();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.register_back:
                backClick();
                break;
            case R.id.register_register:
                registerClick();
                break;
        }
    }

    private void backClick() {
        parent.goToLogin();
    }

    private void registerClick() {
        if(username.getText().toString().trim().isEmpty()) {
            username.setError(getText(R.string.empty_field));
            username.requestFocus();
            return;
        }

        if(password1.getText().toString().trim().isEmpty()) {
            password1.setError(getText(R.string.empty_field));
            password1.requestFocus();
            return;
        }
        if(password2.getText().toString().trim().isEmpty()) {
            password2.setError(getText(R.string.empty_field));
            password2.requestFocus();
            return;
        }

        if(!password1.getText().toString().equals(password2.getText().toString())) {
            password2.setError(getText(R.string.not_equal));
            password2.requestFocus();
            return;
        }

        if(!email1.getText().toString().equals(email2.getText().toString())) {
            email2.setError(getText(R.string.not_equal));
            email2.requestFocus();
            return;
        }

        if(email1.getText().toString().trim().isEmpty()) {
            email1.setError(getText(R.string.empty_field));
            email1.requestFocus();
            return;
        }

        if(!mailChecker(email1)) {
            return;
        }

        if(email2.getText().toString().trim().isEmpty()) {
            email2.requestFocus();
            return;
        }

        Toast toast = Toast.makeText(parent, getText(R.string.sending), Toast.LENGTH_SHORT);
        toast.show();

        user = username.getText().toString();

        CheckRegistration checker = new CheckRegistration(this, username.getText().toString(), password1.getText().toString(), email1.getText().toString());
        checker.execute();
    }

    private boolean mailChecker(EditText email) {
        if(!Pattern.matches("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}",email.getText().toString())) {
            email.setError(getText(R.string.not_valid));
            email.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch(v.getId()) {
            case R.id.register_username:
                usernameCheck(username);
                break;
            case R.id.register_email1:
                mailChecker(email1);
                break;
        }
    }

    private void usernameCheck(EditText username) {
        Log.d(TAG, "usernameCheck: start check "+username);
        //TODO controllo presenza username nel sistema
    }



    public void checkResult(boolean result) {
        if(result) {
            Log.d(TAG, "checkResult: registred and logged");
            Toast toast = Toast.makeText(parent, getText(R.string.logged), Toast.LENGTH_SHORT);
            toast.show();
            parent.correctlyLogged(false, user);
        } else {
            Log.d(TAG, "checkResult: not registred");
            Toast toast = Toast.makeText(parent, getText(R.string.error), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPause() {
        parent.onPauseFragment("register_username", username.getText().toString());
        parent.onPauseFragment("register_password1", password1.getText().toString());
        parent.onPauseFragment("register_password2", password2.getText().toString());
        parent.onPauseFragment("register_email1", email1.getText().toString());
        parent.onPauseFragment("register_email2", email2.getText().toString());
        super.onPause();
    }

    @Override
    public void onResume() {
        username.setText(parent.onResumeFragment("register_username", ""));
        password1.setText(parent.onResumeFragment("register_password1", ""));
        password2.setText(parent.onResumeFragment("register_password2", ""));
        email1.setText(parent.onResumeFragment("register_email1", ""));
        email2.setText(parent.onResumeFragment("register.email2", ""));
        super.onResume();
    }
}
