package lpaa.earound.home.worker;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import lpaa.earound.home.MyEventsFragment;
import lpaa.earound.type.LocalEvent;


public class LocalEventListListener implements OnClickListener {

    private static final String TAG = "LocalEventListListener";

    private LocalEvent event;
    private MyEventsFragment fragment;

    public LocalEventListListener(LocalEvent event, MyEventsFragment fragment) {
        this.event = event;
        this.fragment = fragment;
    }

    @Override
    public void onClick(View v) {
        fragment.setName(event.getName());
        fragment.setDay(event.getDayString());
        fragment.setDescription(event.getDescription());
        fragment.setAddress(event.getAddress());
        fragment.setOldEvent(
                new LocalEvent(
                    event.getName(),
                    event.getDescription(),
                    event.getDay(),
                    event.getAddress()
                )
        );
        fragment.eableButtons(true);
    }


}
