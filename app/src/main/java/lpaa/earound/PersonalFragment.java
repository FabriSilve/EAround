package lpaa.earound;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class PersonalFragment extends Fragment implements View.OnClickListener {

    private View view;
    private HomeActivity parent;

    private Button logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.personal_fragment, container, false);

        logout = (Button) view.findViewById(R.id.personal_logout);

        logout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.personal_logout:
                logout();
                break;
        }
    }

    public void setParent(HomeActivity parent) {
        this.parent = parent;
    }

    private void logout() {
        parent.logoutUser();
    }
}
