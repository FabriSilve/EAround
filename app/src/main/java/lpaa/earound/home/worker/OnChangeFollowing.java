package lpaa.earound.home.worker;


import android.app.Activity;
import android.util.Log;
import android.widget.CompoundButton;

import lpaa.earound.home.HomeFragment;
import lpaa.earound.type.Event;

public class OnChangeFollowing implements CompoundButton.OnCheckedChangeListener {

    private final String TAG = "OnChangeFollowing";

    private Activity parent;
    private HomeFragment fragment;
    private Event event;

    public OnChangeFollowing(Activity parent, HomeFragment fragment, Event event) {
        this.parent = parent;
        this.fragment = fragment;
        this.event = event;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        FollowUpdater updater = new FollowUpdater(parent, fragment, event, isChecked);
        updater.execute();
    }
}
