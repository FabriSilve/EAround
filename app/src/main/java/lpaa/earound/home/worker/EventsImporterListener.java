package lpaa.earound.home.worker;

import android.view.View;

import lpaa.earound.home.MyEventsFragment;



public class EventsImporterListener implements View.OnClickListener {

    private static final String TAG = "EventsImporterListener";

    private MyEventsFragment fragment;

    public EventsImporterListener(MyEventsFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onClick(View v) {
        EventsImporter importer = new EventsImporter(fragment);
        importer.execute();
    }
}
