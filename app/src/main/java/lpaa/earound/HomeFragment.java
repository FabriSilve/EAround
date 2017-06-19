package lpaa.earound;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
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

        this.parent = (HomeActivity) this.getActivity();

        view = inflater.inflate(R.layout.home_fragment, container, false);

        Log.d(TAG, "onCreateView: init UI");
        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try {
            MapsInitializer.initialize(parent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        eventsList = (LinearLayout) view.findViewById(R.id.eventList);
        MapDrawer mapDrawer = new MapDrawer(this, view, map, parent);

        eventDrawer();
        mapView.getMapAsync(mapDrawer);
        return view;
    }

    public void eventDrawer() {
        Log.d(TAG, "eventDrawer: start");
        ArrayList<Event> events = new DBTask(parent).getEvents();
        //TODO prende eventi dal db
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
                    params.setMargins(
                            0,
                            (int) getResources().getDimension(R.dimen.marginMin),
                            0,
                            (int) getResources().getDimension(R.dimen.marginMin)
                    );
                    eventLayout.setLayoutParams(params);

                    TextView name = new TextView(parent);
                    name.setText(events.get(i).getName());
                    name.setTextSize(getResources().getDimension(R.dimen.title1_textSize));
                    name.setTypeface(name.getTypeface(), Typeface.BOLD);
                    name.setTextColor(Color.rgb(255, 128, 0));
                    name.setPadding(
                            (int) getResources().getDimension(R.dimen.marginMin),
                            (int) getResources().getDimension(R.dimen.marginMin),
                            (int) getResources().getDimension(R.dimen.marginMin),
                            0
                    );
                    name.setLayoutParams(new LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.WRAP_CONTENT));
                    eventLayout.addView(name);

                    TextView data = new TextView(parent);
                    data.setText(events.get(i).getDate().toString());
                    data.setTextSize(getResources().getDimension(R.dimen.title2_textSize));
                    data.setTypeface(data.getTypeface(), Typeface.ITALIC);
                    data.setTextColor(Color.BLACK);
                    data.setPadding(
                            (int) getResources().getDimension(R.dimen.marginMin),
                            (int) getResources().getDimension(R.dimen.marginMin),
                            (int) getResources().getDimension(R.dimen.marginMin),
                            0
                    );
                    data.setLayoutParams(new LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.WRAP_CONTENT));
                    eventLayout.addView(data);

                    TextView desc = new TextView(parent);
                    desc.setText(events.get(i).getDescription());
                    desc.setTextSize(getResources().getDimension(R.dimen.par_textSize));
                    //desc.setTypeface(data.getTypeface(), Typeface.DEFAULT);
                    desc.setTextColor(Color.BLACK);
                    desc.setPadding(
                            (int) getResources().getDimension(R.dimen.marginMin),
                            (int) getResources().getDimension(R.dimen.marginMin),
                            (int) getResources().getDimension(R.dimen.marginMin),
                            (int) getResources().getDimension(R.dimen.marginMin)
                    );
                    desc.setLayoutParams(new LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.WRAP_CONTENT));
                    eventLayout.addView(desc);

                    eventLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast toast = Toast.makeText(view.getContext(), getText(R.string.clicked), Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });

                    eventsList.addView(eventLayout);
                } catch (Exception e) {
                    Log.e(TAG, "eventDrawer: Exception \n",e);
                }
            }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged: start");
        super.onConfigurationChanged(newConfig);
    }
}
