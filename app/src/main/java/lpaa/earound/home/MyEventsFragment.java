package lpaa.earound.home;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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
import lpaa.earound.home.worker.EventRemover;
import lpaa.earound.home.worker.EventUpdater;
import lpaa.earound.home.worker.EventsImporterListener;
import lpaa.earound.home.worker.LocalEventListListener;
import lpaa.earound.type.LocalEvent;


public class MyEventsFragment extends Fragment implements View.OnClickListener {

    private final String TAG = "MyEventsFragment";

    private HomeActivity parent;
    private LocalEvent oldEvent;

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
        initEdit();

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
        if (events != null) {
            try {
                if (events.size() > 0) {
                    for (int i = 0; i < events.size(); i++) {
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
                    }
                } else {
                    LinearLayout noEventLayout = new LinearLayout(parent);
                    noEventLayout.setOrientation(LinearLayout.VERTICAL);
                    noEventLayout.setMinimumWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                    noEventLayout.setMinimumHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                    //TODO rimuovere metodo deprecato
                    noEventLayout.setBackground(getResources().getDrawable(R.drawable.lightbg));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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
                    message.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    noEventLayout.addView(message);

                    listevent.addView(noEventLayout);
                }
            } catch (Exception e) {
                Log.e(TAG, "eventDrawer: Exception \n", e);
            }

            LinearLayout endButton = new LinearLayout(parent);
            endButton.setOrientation(LinearLayout.VERTICAL);
            endButton.setMinimumWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            endButton.setMinimumHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            //TODO rimuovere metodo deprecato
            endButton.setBackground(getResources().getDrawable(R.drawable.lightbg));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(
                    (int) getResources().getDimension(R.dimen.marginMiddle),
                    (int) getResources().getDimension(R.dimen.marginMiddle),
                    (int) getResources().getDimension(R.dimen.marginMiddle),
                    (int) getResources().getDimension(R.dimen.marginMiddle)
            );
            endButton.setLayoutParams(params);

            TextView importEvents = new TextView(parent);
            importEvents.setText(R.string.import_from_server);
            importEvents.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            importEvents.setTextSize(getResources().getDimension(R.dimen.title2_textSize));
            importEvents.setTypeface(importEvents.getTypeface(), Typeface.BOLD);
            importEvents.setTextColor(Color.rgb(255, 128, 0));
            importEvents.setPadding(
                    (int) getResources().getDimension(R.dimen.marginMin),
                    (int) getResources().getDimension(R.dimen.marginMin),
                    (int) getResources().getDimension(R.dimen.marginMin),
                    (int) getResources().getDimension(R.dimen.marginMin)
            );
            importEvents.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            endButton.addView(importEvents);

            EventsImporterListener importer = new EventsImporterListener(this);
            endButton.setOnClickListener(importer);

            listevent.addView(endButton);

            list.addView(listevent);
        }
    }


    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");
        switch(v.getId()) {
            case R.id.myevent_edit_modify:
                updateEvent();
                break;
            case R.id.myevent_edit_remove:
                removeEvent();
                break;
        }
    }

    private void updateEvent() {
        Log.d(TAG, "modifyEvent: ");
        eableButtons(false);

        LocalEvent newEvent = new LocalEvent(
                name.getText().toString(),
                description.getText().toString(),
                Date.valueOf(day.getText().toString()),
                address.getText().toString()
        );

        EventUpdater updater = new EventUpdater(this, newEvent, new DBTask(parent).getUser());
        updater.execute();
        Toast toast = Toast.makeText(parent, R.string.updating, Toast.LENGTH_SHORT);
        toast.show();
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

        EventRemover remover = new EventRemover(this, event, new DBTask(parent).getUser());
        remover.execute();
        Toast toast = Toast.makeText(parent, R.string.removing, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void initEdit() {
        setName("");
        setAddress("");
        setDay("");
        setDescription("");
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

    public void setOldEvent(LocalEvent oldEvent) {
        this.oldEvent = oldEvent;
    }

    public LocalEvent getOldEvent() {
        return oldEvent;
    }
    
    public void removedEvent(String result) {
        Log.d(TAG, "removedEvent: ");
        if(result.equals("true")) {
            Toast toast = Toast.makeText(parent, R.string.removed, Toast.LENGTH_SHORT);
            toast.show();
            initEdit();
            list.removeAllViews();
            eventDrawer();
        } else {
            Toast toast = Toast.makeText(parent, R.string.error, Toast.LENGTH_SHORT);
            toast.show();
            eableButtons(true);
        }
    }

    public void updatedEvent(String result) {
        Log.d(TAG, "updatedEvent: ");
        if (result.equals("true")) {
            Toast.makeText(parent, R.string.updated, Toast.LENGTH_SHORT);
            //initEdit();
            list.removeAllViews();
            eventDrawer();
        } else {
            Toast toast = Toast.makeText(parent, R.string.error, Toast.LENGTH_SHORT);
            toast.show();
            setName(oldEvent.getName());
            setDescription(oldEvent.getDescription());
            setDay(oldEvent.getDayString());
            setAddress(oldEvent.getAddress());
        }
        eableButtons(true);
    }

    public void importedEvents() {
        Log.d(TAG, "importedEvents: ");
        Toast.makeText(parent, R.string.imported, Toast.LENGTH_SHORT);
        initEdit();
        list.removeAllViews();
        eventDrawer();
    }

    //TODO inserire "Importa eventi da server"
    @Override
    public void onPause() {
        Log.d(TAG, "onPause: ");
        /*TODO migliore con o senza?
        parent.onPauseFragment("myevent_name", name.getText().toString());
        parent.onPauseFragment("myevent_address", address.getText().toString());
        parent.onPauseFragment("myevent_day", day.getText().toString());
        parent.onPauseFragment("myevent_description", description.getText().toString());
*/
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: ");
        /*TODO migliore con o senza?
        name.setText(parent.onResumeFragment("myevent_name", ""));
        address.setText(parent.onResumeFragment("myevent_address", ""));
        day.setText(parent.onResumeFragment("myevent_day", ""));
        description.setText(parent.onResumeFragment("myevent_description", ""));
        if(!name.getText().toString().equals(""))
            eableButtons(true);*/
        super.onResume();
    }

}
