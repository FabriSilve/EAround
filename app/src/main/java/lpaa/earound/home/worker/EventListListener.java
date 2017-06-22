package lpaa.earound.home.worker;

import android.util.Log;
import android.view.View;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;

import lpaa.earound.home.HomeFragment;
import lpaa.earound.type.Event;


public class EventListListener implements View.OnClickListener {

    private static final String TAG = "EventListListener";

    private Event event;
    private HomeFragment homeFragment;

    public EventListListener(Event event, HomeFragment homeFragment) {
        this.event = event;
        this.homeFragment = homeFragment;
    }

    @Override
    public void onClick(View v) {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(event.getPosition()).zoom(18).build();
        if(homeFragment.getMap() != null)
            homeFragment.getMap().animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        Log.d(TAG, "onClick: onclick");
    }
}
