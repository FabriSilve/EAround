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
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private final String TAG = "HomeFragment";

    private View view;
    private HomeActivity parent;
    private GoogleMap map;

    private MapView mapView;
    private LinearLayout eventsList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: start");
        view = inflater.inflate(R.layout.home_fragment, container, false);

        Log.d(TAG, "onCreateView: init UI");
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
        MapDrawer mapDrawer = new MapDrawer(this, view, map, parent.getApplicationContext());
        mapView.getMapAsync(mapDrawer);
        return view;
    }

    public void setParent(HomeActivity parent) {
        Log.d(TAG, "setParent: start");
        this.parent = parent;
    }

    public void eventDrawer() {
        Log.d(TAG, "eventDrawer: start");
        ArrayList<Event> events;
        DBTask dbTask = new DBTask(parent.getApplicationContext());
        events = dbTask.getEvents();
        //ArrayList<LinearLayout> eventsLayout = new ArrayList<>();
        if (events != null && events.size() > 0)
            for (int i = 0; i < events.size(); i++) {
                try {
                    LinearLayout eventLayout = new LinearLayout(parent);
                    eventLayout.setId(i);
                    eventLayout.setMinimumWidth(LayoutParams.MATCH_PARENT);
                    eventLayout.setMinimumHeight(LayoutParams.WRAP_CONTENT);
                    eventLayout.setOrientation(LinearLayout.VERTICAL);
                    eventLayout.setBackground(getResources().getDrawable(R.drawable.lightbg));
                    LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, 10, 0, 10);
                    eventLayout.setLayoutParams(params);
                    //margin?

                    TextView name = new TextView(parent);
                    name.setText(events.get(i).getName());
                    name.setTextSize(getResources().getDimension(R.dimen.title1_textSize));
                    name.setTextColor(Color.rgb(255, 128, 0));
                    name.setPadding(10, 10, 10, 10);
                    eventLayout.addView(name, 0);

                    TextView data = new TextView(parent);
                    name.setText(events.get(i).getDate().toString());
                    eventLayout.addView(data, 1);

                    TextView desc = new TextView(parent);
                    name.setText(events.get(i).getDescription());
                    eventLayout.addView(desc, 2);

                    eventLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast toast = Toast.makeText(view.getContext(), getText(R.string.clicked), Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });

                    /*TextView event = new TextView(parent);
                    String text =
                            events.get(i).getName() + "\t" + events.get(i).getDate() + "\n"
                            + events.get(i).getDescription() + "\n";
                    event.setText(text);
                    eventlayout.add(i, event);*/

                    //eventsView.get(i).setText(events.get(i).getName());
                    //eventsView.get(i).setTextSize(50);

                    eventsList.addView(eventLayout);
                } catch (Exception e) {
                    Log.e(TAG, "eventDrawer: Exception \n",e);
                }
            }
    }

    /*public ArrayList<Event> getParentEvents() {
        Log.d(TAG, "getParentEvents: start");
        //TODO Correggere qui
        //return parent == null? new ArrayList<Event>() : ((HomeActivity) this.getActivity()).getEvents();
        return new ArrayList<>();
    }*/

    public HomeActivity getParent() {
        Log.d(TAG, "getParent: start");
        return parent;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged: start");
        super.onConfigurationChanged(newConfig);
    }
}
