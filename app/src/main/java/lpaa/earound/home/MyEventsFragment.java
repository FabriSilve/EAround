package lpaa.earound.home;

import android.app.Fragment;
import android.app.FragmentContainer;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import lpaa.earound.R;
import lpaa.earound.database.DBTask;
import lpaa.earound.type.LocalEvent;


public class MyEventsFragment extends Fragment {

    private final String TAG = "MyEventsFragment";

    private HomeActivity parent;

    private ScrollView list;

    private int idEvent;
    private MyEventsEdit myEventsEdit;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        this.parent = (HomeActivity) this.getActivity();

        View view = inflater.inflate(R.layout.myevents_fragment, container, false);

        list = (ScrollView) view.findViewById(R.id.myevent_list);

        eventDrawer();

        idEvent = R.id.myevent_event;

        myEventsEdit = new MyEventsEdit();

        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(idEvent, myEventsEdit );
        fragmentTransaction.commit();

        return view;
    }

    private void eventDrawer() {
        Log.d(TAG, "eventDrawer: start");
        ArrayList<LocalEvent> events = new DBTask(getActivity()).getLocalEvents();
        LinearLayout listevent = new LinearLayout(getActivity());
        listevent.setMinimumWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        listevent.setMinimumHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        listevent.setOrientation(LinearLayout.VERTICAL);
        if (events != null && events.size() > 0) {
            for (int i =0; i < events.size(); i++) {
                try {
                    LinearLayout eventLayout = new LinearLayout(getActivity());
                    eventLayout.setMinimumWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                    eventLayout.setMinimumHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                    eventLayout.setOrientation(LinearLayout.VERTICAL);
                    //TODO trovare modo per rimuovere metodo deprecato
                    eventLayout.setBackground(getResources().getDrawable(R.drawable.lightbg));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(
                            0,
                            (int) getResources().getDimension(R.dimen.marginMin),
                            0,
                            (int) getResources().getDimension(R.dimen.marginMin)
                    );
                    eventLayout.setLayoutParams(params);

                    TextView name = new TextView(getActivity());
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
                    name.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    eventLayout.addView(name);

                    TextView data = new TextView(getActivity());
                    data.setText(events.get(i).getDayString());
                    data.setTextSize(getResources().getDimension(R.dimen.title2_textSize));
                    data.setTypeface(data.getTypeface(), Typeface.ITALIC);
                    data.setTextColor(Color.BLACK);
                    data.setPadding(
                            (int) getResources().getDimension(R.dimen.marginMin),
                            (int) getResources().getDimension(R.dimen.marginMin),
                            (int) getResources().getDimension(R.dimen.marginMin),
                            0
                    );
                    data.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    eventLayout.addView(data);

                    TextView address = new TextView(getActivity());
                    address.setText(events.get(i).getAddress());
                    address.setTextSize(getResources().getDimension(R.dimen.title2_textSize));
                    address.setTypeface(data.getTypeface(), Typeface.ITALIC);
                    address.setTextColor(Color.BLACK);
                    address.setPadding(
                            (int) getResources().getDimension(R.dimen.marginMin),
                            (int) getResources().getDimension(R.dimen.marginMin),
                            (int) getResources().getDimension(R.dimen.marginMin),
                            0
                    );
                    address.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    eventLayout.addView(address);

                    TextView desc = new TextView(getActivity());
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
                    desc.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    eventLayout.addView(desc);

                   /* LocalEventListListener listener = new LocalEventListListener(events.get(i), this);
                    eventLayout.setOnClickListener(listener);*/

                    listevent.addView(eventLayout);

                } catch (Exception e) {
                    Log.e(TAG, "eventDrawer: Exception \n", e);
                }
            }
        }
        list.addView(listevent);
    }

}
