package lpaa.earound.home.worker;

import android.util.Log;
import android.view.View;

import lpaa.earound.home.FollowedFragment;
import lpaa.earound.home.MyEventsFragment;

/**
 * Created by Fabrizio on 25/06/2017.
 */

public class FollowedImporterListener  implements View.OnClickListener {

    private static final String TAG = "FollowedListener";

    private FollowedFragment fragment;

    public FollowedImporterListener(FollowedFragment fragment) {
        Log.d(TAG, "FollowedImporterListener: ");
        this.fragment = fragment;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");
        FollowedImporter importer = new FollowedImporter(fragment);
        importer.execute();
    }
}
