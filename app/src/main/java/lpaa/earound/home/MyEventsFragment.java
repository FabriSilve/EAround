package lpaa.earound.home;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;

import lpaa.earound.R;
import lpaa.earound.database.DBTask;
import lpaa.earound.home.worker.EventSearcher;
import lpaa.earound.home.worker.LocalEventListListener;
import lpaa.earound.type.LocalEvent;
import lpaa.earound.type.Search;


public class MyEventsFragment extends Fragment implements View.OnClickListener {

    private final String TAG = "MyEventsFragment";

    private HomeActivity parent;

    private ScrollView list;
    private EditText name;
    private EditText address;
    private EditText day;
    private EditText description;
    private Button modify;
    private Button remove;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        this.parent = (HomeActivity) this.getActivity();

        View view = inflater.inflate(R.layout.myevents_fragment, container, false);

        list = (ScrollView) view.findViewById(R.id.myevent_list);

        name = (EditText) view.findViewById(R.id.myevent_edit_name);
        address = (EditText) view.findViewById(R.id.myevent_edit_address);
        day = (EditText) view.findViewById(R.id.myevent_edit_day);
        description = (EditText) view.findViewById(R.id.myevent_edit_description);
        modify = (Button) view.findViewById(R.id.myevent_edit_modify);
        remove = (Button) view.findViewById(R.id.myevent_edit_remove);
        modify.setOnClickListener(this);
        remove.setOnClickListener(this);
        eableButtons(false);

        eventDrawer();

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

                    LocalEventListListener listener = new LocalEventListListener(events.get(i), this);
                    eventLayout.setOnClickListener(listener);

                    listevent.addView(eventLayout);

                } catch (Exception e) {
                    Log.e(TAG, "eventDrawer: Exception \n", e);
                }
            }
        }
        list.addView(listevent);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");
        switch(v.getId()) {
            case R.id.myevent_edit_modify:
                modifyEvent();
                break;
            case R.id.myevent_edit_remove:
                removeEvent();
                break;
        }
    }

    private void modifyEvent() {
        Log.d(TAG, "modifyEvent: ");
    }

    private void removeEvent() {
        Log.d(TAG, "removeEvent: ");
        eableButtons(false);
        LocalEvent event = new LocalEvent(
                name.getText().toString(),
                description.getText().toString(),
                Date.valueOf(day.getText().toString()),
                address.getText().toString()
        );

        /*EventRemover remover = new EventRemover(parent, event);
        remover.execute();
        Toast toast = Toast.makeText(parent, R.string.removing, Toast.LENGTH_SHORT);
        toast.show();*/
    }

    public void setName(String name) {
        Log.d(TAG, "setName: ");
        this.name.setText(name);
    }

    public void setAddress(String address) {
        Log.d(TAG, "setAddress: ");
        this.address.setText(address);
    }

    public void setDay(String day) {
        Log.d(TAG, "setDay: ");
        this.day.setText(day);
    }

    public void setDescription(String description) {
        Log.d(TAG, "setDescription: ");
        this.description.setText(description);
    }

    public void eableButtons(boolean value) {
        Log.d(TAG, "eableButtons: ");
        modify.setClickable(value);
        remove.setClickable(value);
    }

    //TODO inserire "Importa eventi da server"
    @Override
    public void onPause() {
        Log.d(TAG, "onPause: ");
        parent.onPauseFragment("myevent_name", name.getText().toString());
        parent.onPauseFragment("myevent_address", address.getText().toString());
        parent.onPauseFragment("myevent_day", day.getText().toString());
        parent.onPauseFragment("myevent_description", description.getText().toString());

        super.onPause();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: ");
        name.setText(parent.onResumeFragment("myevent_name", ""));
        address.setText(parent.onResumeFragment("myevent_address", ""));
        day.setText(parent.onResumeFragment("myevent_day", ""));
        description.setText(parent.onResumeFragment("myevent_description", ""));
        super.onResume();
    }

}
