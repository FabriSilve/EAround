package lpaa.earound.home.worker;

import android.util.Log;
import android.view.View;

import lpaa.earound.home.FollowedFragment;


public class FollowedImporterListener  implements View.OnClickListener {

    private static final String TAG = "FollowedListener";

    private FollowedFragment fragment;

    public FollowedImporterListener(FollowedFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onClick(View v) {
        FollowedImporter importer = new FollowedImporter(fragment);
        importer.execute();
    }
}
