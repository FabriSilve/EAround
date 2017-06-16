package lpaa.earound;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;

import java.util.ArrayList;


public class HomeFragment extends Fragment{

    //TODO inizializzare valori della classe e la vista
    private final String TAG = "HomeFragment";

    private View view;
    private HomeActivity parent;
    private GoogleMap map;

    private MapView mapView;
    private LinearLayout eventsList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);

        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        eventsList = (LinearLayout) view.findViewById(R.id.eventList);
        eventDrawer();
        MapDrawer mapDrawer = new MapDrawer(this, view, map);
        mapView.getMapAsync(mapDrawer);
        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void setParent(HomeActivity parent) {
        this.parent = parent;
    }

    public void eventDrawer() {
        Log.d(TAG, "eventDrawer: events stamp");
        Context context = parent.getBaseContext();
        ArrayList<Event> events = parent.getEvents();
        //ArrayList<LinearLayout> eventsLayout = new ArrayList<>();
        if(events != null && events.size() > 0 )
            for(int i = 0; i<events.size(); i++) {

                LinearLayout eventLayout = new LinearLayout(parent);
                eventLayout.setMinimumWidth(400);
                eventLayout.setMinimumHeight(300);
                eventLayout.setOrientation(LinearLayout.VERTICAL);
                eventLayout.setBackgroundResource(R.drawable.lightbg);
                //margin?

                TextView name = new TextView(parent);
                name.setText(events.get(i).getName());
                name.setTextSize(getResources().getDimension(R.dimen.title1_textSize));
                //name.setTextColor(getResources().getColor(R.color.colorPrimary));
                eventLayout.addView(name, 0);

               /* TextView data = new TextView(parent);
                name.setText(events.get(i).getDate().toString());
                eventLayout.addView(data, 1);

                TextView desc = new TextView(parent);
                name.setText(events.get(i).getDescription());
                eventLayout.addView(desc, 2);*/

                /*TextView event = new TextView(parent);
                String text =
                        events.get(i).getName() + "\t" + events.get(i).getDate() + "\n"
                        + events.get(i).getDescription() + "\n";
                event.setText(text);
                eventlayout.add(i, event);*/

                //eventsView.get(i).setText(events.get(i).getName());
                //eventsView.get(i).setTextSize(50);
                try {
                   eventsList.addView(eventLayout);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }

    public ArrayList<Event> getParentEvents() {
        return parent.getEvents();
    }

    public HomeActivity getParent() {
        return parent;
    }
}
