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


public class MenuFragment extends Fragment implements View.OnClickListener {

    private final String TAG = "MenuFragment";

    private View view;
    private HomeActivity parent;

    private Button addEvent;
    private Button myEvent;
    private Button followed;
    private Button logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: init UI");

        this.parent = (HomeActivity) this.getActivity();

        view = inflater.inflate(R.layout.menu_fragment, container, false);

        addEvent = (Button) view.findViewById(R.id.personal_addEvent);
        myEvent = (Button) view.findViewById(R.id.personal_myEvent);
        followed = (Button) view.findViewById(R.id.personal_followed);
        logout = (Button) view.findViewById(R.id.personal_logout);

        addEvent.setOnClickListener(this);
        myEvent.setOnClickListener(this);
        followed.setOnClickListener(this);
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
            case R.id.personal_followed:
                parent.goToFollowed();
                break;
            case R.id.personal_logout:
                parent.logoutUser();
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged: start");
        super.onConfigurationChanged(newConfig);
    }
}
