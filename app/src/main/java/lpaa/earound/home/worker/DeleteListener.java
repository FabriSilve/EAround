package lpaa.earound.home.worker;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import lpaa.earound.home.FollowedFragment;
import lpaa.earound.type.Event;

/**
 * Created by Fabrizio on 25/06/2017.
 */

public class DeleteListener implements OnClickListener{

    private final String TAG = "DeleteListener";

    private Activity parent;
    private FollowedFragment fragment;
    private Event event;

    public DeleteListener(Activity parent, FollowedFragment fragment, Event event) {
        Log.d(TAG, "DeleteListener: ");
        this.parent = parent;
        this.fragment = fragment;
        this.event = event;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");
        FollowUpdater updater = new FollowUpdater(parent,fragment, event, false);
        updater.execute();
    }
}
