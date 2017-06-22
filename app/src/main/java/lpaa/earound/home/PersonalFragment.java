package lpaa.earound.home;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import lpaa.earound.R;


public class PersonalFragment extends Fragment implements View.OnClickListener {

    private final String TAG = "PersonalFragment";

    private View view;
    private HomeActivity parent;

    private Button addEvent;
    private Button myEvent;
    private Button logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: init UI");

        this.parent = (HomeActivity) this.getActivity();

        view = inflater.inflate(R.layout.personal_fragment, container, false);

        addEvent = (Button) view.findViewById(R.id.personal_addEvent);
        myEvent = (Button) view.findViewById(R.id.personal_myEvent);
        logout = (Button) view.findViewById(R.id.personal_logout);

        addEvent.setOnClickListener(this);
        myEvent.setOnClickListener(this);
        logout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: start");
        switch(v.getId()) {
            case R.id.personal_addEvent:
                parent.goToAddEvent();
                break;
            case R.id.personal_myEvent:
                parent.goToMyEvent();
                break;
            case R.id.personal_logout:
                logout();
                break;
        }
    }

    private void logout() {
        Log.d(TAG, "logout: start");
        parent.logoutUser();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged: start");
        super.onConfigurationChanged(newConfig);
    }
}
