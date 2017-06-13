package lpaa.earound;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
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
    private LinearLayout eventList;


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
        MapDrawer mapDrawer = new MapDrawer(view, map);
        mapView.getMapAsync(mapDrawer);

        eventList = (LinearLayout) view.findViewById(R.id.eventList);
        eventDrawer();
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
        ArrayList<Event> events = parent.getEvents();
        ArrayList<TextView> eventsView = new ArrayList<>();
        if(events != null && events.size() > 0 )
            for(int i = 0; i<events.size(); i++) {
                eventsView.add(i, new TextView(this.getActivity()));
                eventsView.get(i).setText(events.get(i).getName());
                eventsView.get(i).setTextSize(50);
                try{
                    eventList.addView(eventsView.get(i));
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

    }
}
