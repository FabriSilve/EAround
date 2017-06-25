package lpaa.earound.home.worker;


import android.util.Log;
import android.widget.CompoundButton;

import lpaa.earound.home.HomeFragment;
import lpaa.earound.type.Event;

public class OnChangeFollowing implements CompoundButton.OnCheckedChangeListener {

    private final String TAG = "OnChangeFollowing";

    private HomeFragment fragment;
    private Event event;

    public OnChangeFollowing(HomeFragment fragment, Event event) {
        Log.d(TAG, "OnChangeFollowing: ");
        this.fragment = fragment;
        this.event = event;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.d(TAG, "onCheckedChanged: "+String.valueOf(isChecked));
        FollowUpdater updater = new FollowUpdater(fragment, event, isChecked);
        updater.execute();
    }
}
