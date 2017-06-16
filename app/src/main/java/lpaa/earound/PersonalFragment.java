package lpaa.earound;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class PersonalFragment extends Fragment implements View.OnClickListener {

    private final String TAG = "PersonalFragment";

    private View view;
    private HomeActivity parent;

    private Button logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: init UI");
        view = inflater.inflate(R.layout.personal_fragment, container, false);

        logout = (Button) view.findViewById(R.id.personal_logout);

        logout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: start");
        switch(v.getId()) {
            case R.id.personal_logout:
                logout();
                break;
        }
    }

    public void setParent(HomeActivity parent) {
        Log.d(TAG, "setParent: start");
        this.parent = parent;
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
