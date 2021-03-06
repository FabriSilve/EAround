package lpaa.earound.home.worker;

import android.app.Activity;
import android.util.Log;
import android.widget.CompoundButton;

import lpaa.earound.R;
import lpaa.earound.home.HomeFragment;
import lpaa.earound.type.Event;


public class OnChangeSignal implements CompoundButton.OnCheckedChangeListener {

    private final String TAG = "OnChangeSignal";

    private Activity parent;
    private HomeFragment fragment;
    private Event event;

    public OnChangeSignal(Activity parent, HomeFragment fragment, Event event) {
        this.parent = parent;
        this.fragment = fragment;
        this.event = event;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SignalSender sender = new SignalSender(parent, fragment, event, isChecked);
        sender.execute();
        buttonView.setClickable(false);
        buttonView.setText(R.string.signaled);
    }
}
