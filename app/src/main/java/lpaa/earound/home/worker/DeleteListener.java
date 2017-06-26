package lpaa.earound.home.worker;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import lpaa.earound.home.FollowedFragment;
import lpaa.earound.type.Event;


public class DeleteListener implements OnClickListener{

    private final String TAG = "DeleteListener";

    private Activity parent;
    private FollowedFragment fragment;
    private Event event;

    public DeleteListener(Activity parent, FollowedFragment fragment, Event event) {
        this.parent = parent;
        this.fragment = fragment;
        this.event = event;
    }

    @Override
    public void onClick(View v) {
        FollowUpdater updater = new FollowUpdater(parent,fragment, event, false);
        updater.execute();
    }
}
