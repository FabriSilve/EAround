package lpaa.earound.home.worker;

import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;

import lpaa.earound.home.HomeFragment;
import lpaa.earound.home.MyEventsFragment;
import lpaa.earound.type.Event;


public class EventsImporterListener implements View.OnClickListener {

    private static final String TAG = "EventsImporterListener";

    private MyEventsFragment fragment;

    public EventsImporterListener(MyEventsFragment fragment) {
        Log.d(TAG, "EventsImporterListener: ");
        this.fragment = fragment;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");
        EventsImporter importer = new EventsImporter(fragment);
        importer.execute();
    }
}
