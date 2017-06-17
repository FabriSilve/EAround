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
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

/**
 * Created by Fabrizio on 07/06/2017.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {

    private final String TAG = "RegisterFragment";

    private View view;
    private MainActivity main;

    private Button back;
    private EditText username;
    private EditText password1;
    private EditText password2;
    private EditText email1;
    private EditText email2;
    private Button register;

    private String user;

    public void setActivity(MainActivity main) {
        Log.d(TAG, "setActivity: start");
        this.main = main;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: init UI");
        view = inflater.inflate(R.layout.register_fragment, container, false);

        back = (Button) view.findViewById(R.id.register_back);
        username = (EditText) view.findViewById(R.id.register_username);
        password1 = (EditText) view.findViewById(R.id.register_password1);
        password2 = (EditText) view.findViewById(R.id.register_password2);
        email1 = (EditText) view.findViewById(R.id.register_email1);
        email2 = (EditText) view.findViewById(R.id.register_email2);
        register = (Button) view.findViewById(R.id.register_register);

        back.setOnClickListener(this);
        register.setOnClickListener(this);

        //TODO estrarre listener
        username.getOnFocusChangeListener();
        email1.getOnFocusChangeListener();

        return view;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: start");
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
        Log.d(TAG, "backClick: start");
        main.goToLogin();
    }

    private void registerClick() {
        Log.d(TAG, "registerClick: start");

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

        Toast toast = Toast.makeText(view.getContext(), getText(R.string.sending), Toast.LENGTH_SHORT);
        toast.show();

        user = username.getText().toString();

        CheckRegistration checker = new CheckRegistration(this, username.getText().toString(), password1.getText().toString(), email1.getText().toString());
        checker.execute();
    }

    private boolean mailChecker(EditText email) {
        Log.d(TAG, "mailChecker: start");
        if(!Pattern.matches("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}",email.getText().toString())) {
            email.setError(getText(R.string.not_valid));
            email.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Log.d(TAG, "onFocusChange: start");
        /*TODO su username e email per controllare se gia presenti nel sistema*/
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
        Log.d(TAG, "usernameCheck: start");
        //TODO controllo presenza username nel sistema
    }



    public void checkResult(boolean result) {
        Log.d(TAG, "checkResult: start");
        if(result) {
            Log.d(TAG, "checkResult: registred and logged");
            Toast toast = Toast.makeText(view.getContext(), getText(R.string.logged), Toast.LENGTH_SHORT);
            toast.show();
            main.correctlyLogged(false, user);
        } else {
            Log.d(TAG, "checkResult: not registred");
            Toast toast = Toast.makeText(view.getContext(), getText(R.string.error), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged: start");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause: ");
        main.onPauseFragment("register_username", username.getText().toString());
        main.onPauseFragment("register_password1", password1.getText().toString());
        main.onPauseFragment("register_password2", password2.getText().toString());
        main.onPauseFragment("register_email1", email1.getText().toString());
        main.onPauseFragment("register_email2", email2.getText().toString());
        super.onPause();
    }

    @Override
    public void onResume() {
        username.setText(main.onResumeFragment("register_username", ""));
        password1.setText(main.onResumeFragment("register_password1", ""));
        password2.setText(main.onResumeFragment("register_password2", ""));
        email1.setText(main.onResumeFragment("register_email1", ""));
        email2.setText(main.onResumeFragment("register.email2", ""));
        super.onResume();
    }

    /*@Override
    public void onStop() {
        Log.d(TAG, "onStop: ");

        //TODO capire perche vuole un elemento in più nell'array
        String[] ids = new String[6];
        ids[0] = "register_username";
        ids[1] = "register_password1";
        ids[2] = "register_password2";
        ids[3] = "register_email1";
        ids[4] = "register_email2";
        main.clearFragment(ids);
        super.onStop();
    }*/
}
