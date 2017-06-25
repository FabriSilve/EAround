package lpaa.earound.home;

import android.app.Fragment;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;

import java.util.ArrayList;

import lpaa.earound.home.worker.EventListListener;
import lpaa.earound.home.worker.MapDrawer;
import lpaa.earound.home.worker.OnChangeFollowing;
import lpaa.earound.type.Event;
import lpaa.earound.R;
import lpaa.earound.database.DBTask;


public class HomeFragment extends Fragment {

    private final String TAG = "HomeFragment";

    private HomeActivity parent;
    private GoogleMap map;
    private LinearLayout eventsList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: start");

        this.parent = (HomeActivity) this.getActivity();

        View view = inflater.inflate(R.layout.home_fragment, container, false);

        Log.d(TAG, "onCreateView: init UI");
        MapView mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try {
            MapsInitializer.initialize(parent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MapDrawer mapDrawer = new MapDrawer(this, map, parent);
        mapView.getMapAsync(mapDrawer);

        eventsList = (LinearLayout) view.findViewById(R.id.eventList);
        eventDrawer();

        return view;
    }

    public void eventDrawer() {
        Log.d(TAG, "eventDrawer: start");
        ArrayList<Event> events = new DBTask(parent).getEvents();
        if (events != null && events.size() > 1) {
            for (int i =1; i < events.size(); i++) {
                try {
                    LinearLayout eventLayout = new LinearLayout(parent);
                    eventLayout.setId(i-1);
                    //eventLayout.setTag(events.get(i).getId());
                    eventLayout.setMinimumWidth(LayoutParams.MATCH_PARENT);
                    eventLayout.setMinimumHeight(LayoutParams.WRAP_CONTENT);
                    eventLayout.setOrientation(LinearLayout.VERTICAL);
                    //TODO trovare modo per rimuovere metodo deprecato
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
                    data.setText(events.get(i).getAddress()+ " - " +events.get(i).getDay().toString());
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

                    TextView owner = new TextView(parent);
                    owner.setText(events.get(i).getOwner());
                    owner.setTextSize(getResources().getDimension(R.dimen.par_textSize));
                    //desc.setTypeface(data.getTypeface(), Typeface.DEFAULT);
                    owner.setTextColor(Color.DKGRAY);
                    owner.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                    owner.setPadding(
                            (int) getResources().getDimension(R.dimen.marginMin),
                            (int) getResources().getDimension(R.dimen.marginMin),
                            (int) getResources().getDimension(R.dimen.marginMin),
                            (int) getResources().getDimension(R.dimen.marginMin)
                    );
                    owner.setLayoutParams(new LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.WRAP_CONTENT));
                    eventLayout.addView(owner);


                    CheckBox follow = new CheckBox(parent);
                    follow.setBackground(getResources().getDrawable(R.drawable.lightbg));
                    follow.setLayoutParams(new LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.WRAP_CONTENT));
                    follow.setTextColor(getResources().getColor(R.color.colorPrimary));
                    follow.setText(R.string.follow);
                    follow.setTextSize(getResources().getDimension(R.dimen.checkBox_textSize));
                    follow.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                    //TODO allineare al centro il tasto
                    OnChangeFollowing follower = new OnChangeFollowing(this, events.get(i));
                    follow.setOnCheckedChangeListener(follower);
                    eventLayout.addView(follow);

                    EventListListener listener = new EventListListener(events.get(i), this);
                    eventLayout.setOnClickListener(listener);

                    eventsList.addView(eventLayout);
                } catch (Exception e) {
                    Log.e(TAG, "eventDrawer: Exception \n", e);
                }
            }
        } else {
            LinearLayout noEventLayout = new LinearLayout(parent);
            noEventLayout.setOrientation(LinearLayout.VERTICAL);
            noEventLayout.setMinimumWidth(LayoutParams.MATCH_PARENT);
            noEventLayout.setMinimumHeight(LayoutParams.WRAP_CONTENT);
            //TODO rimuovere metodo deprecato
            noEventLayout.setBackground(getResources().getDrawable(R.drawable.lightbg));
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            params.setMargins(
                    (int) getResources().getDimension(R.dimen.marginMiddle),
                    (int) getResources().getDimension(R.dimen.marginMax),
                    (int) getResources().getDimension(R.dimen.marginMiddle),
                    (int) getResources().getDimension(R.dimen.marginMin)
            );
            noEventLayout.setLayoutParams(params);

            TextView message = new TextView(parent);
            message.setText(R.string.noEvents);
            message.setTextSize(getResources().getDimension(R.dimen.title1_textSize));
            message.setTypeface(message.getTypeface(), Typeface.BOLD);
            message.setTextColor(Color.rgb(255, 128, 0));
            message.setPadding(
                    (int) getResources().getDimension(R.dimen.marginMin),
                    (int) getResources().getDimension(R.dimen.marginMin),
                    (int) getResources().getDimension(R.dimen.marginMin),
                    0
            );
            message.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            noEventLayout.addView(message);

            eventsList.addView(noEventLayout);
        }
    }

    public void setMap(GoogleMap map) {
        this.map = map;
    }

    public GoogleMap getMap() { return map; }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged: start");
        super.onConfigurationChanged(newConfig);
    }
}
