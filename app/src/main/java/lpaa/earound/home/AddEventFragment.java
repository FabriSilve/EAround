package lpaa.earound.home;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;

import lpaa.earound.R;
import lpaa.earound.database.DBTask;
import lpaa.earound.home.worker.EventAdder;
import lpaa.earound.type.LocalEvent;


public class AddEventFragment extends Fragment implements View.OnClickListener {

    private final String TAG = "AddEventFragment";

    private HomeActivity parent;
    private LocalEvent newEvent;

    private EditText name;
    private EditText address;
    private EditText day;
    private EditText description;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: start");
        this.parent = (HomeActivity) this.getActivity();

        View view = inflater.inflate(R.layout.addevent_fragment, container, false);

        name = (EditText) view.findViewById(R.id.addevent_name);
        address = (EditText) view.findViewById(R.id.addevent_address);
        day = (EditText) view.findViewById(R.id.addevent_day);
        description = (EditText) view.findViewById(R.id.addevent_description);
        Button add = (Button) view.findViewById(R.id.addevent_add);

        day.setInputType(InputType.TYPE_NULL);

        day.setOnClickListener(this);
        add.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: start");
        switch(v.getId()) {
            case R.id.addevent_add:
                if (checkEvent()) {
                    newEvent = new LocalEvent(
                        name.getText().toString(),
                        description.getText().toString(),
                        Date.valueOf(day.getText().toString()),
                        address.getText().toString()
                    );
                    EventAdder eventAdder = new EventAdder(
                        this,
                        newEvent,
                        new DBTask(parent).getUser()
                    );
                    eventAdder.execute();
                    Toast toast = Toast.makeText(parent, R.string.sending, Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(parent, R.string.eventNotValid, Toast.LENGTH_LONG);
                    toast.show();
                }
                break;
            case R.id.addevent_day:
                showDatePickerDialog(day);
                break;
        }
    }

    private boolean checkEvent() {
        Log.d(TAG, "checkEvent: start");
        if(name.getText().toString().trim().isEmpty()) {
            name.setError(getText(R.string.empty_field));
            name.requestFocus();
            return false;
        }

        if(address.getText().toString().trim().isEmpty()) {
            address.setError(getText(R.string.empty_field));
            address.requestFocus();
            return false;
        }

        if(day.getText().toString().trim().isEmpty()) {
            day.setError(getText(R.string.empty_field));
            day.requestFocus();
            return false;
        }

        if(description.getText().toString().trim().isEmpty()) {
            description.setError(getText(R.string.empty_field));
            description.requestFocus();
            return false;
        }

        //TODO inserire input per data come data piker
        return true;
    }

    public void checkResult(String result) {
        if(result.equals("true")) {
            Toast.makeText(parent, R.string.added, Toast.LENGTH_SHORT).show();
            name.setText("");
            address.setText("");
            day.setText("");
            description.setText("");
            if(result.equals("true")) {
                new DBTask(getActivity()).insertLocalEvent(newEvent);
            }
        } else {
            Toast.makeText(parent, result, Toast.LENGTH_LONG).show();
        }
    }

    public void showDatePickerDialog( EditText day) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setDay(day);
        newFragment.show(parent.getFragmentManager(), "datePicker");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged: start");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause: ");
        parent.onPauseFragment("addevent_name", name.getText().toString());
        parent.onPauseFragment("addevent_address", String.valueOf(address.getText().toString()));
        parent.onPauseFragment("addevent_day", String.valueOf(day.getText().toString()));
        parent.onPauseFragment("addevent_description", String.valueOf(description.getText().toString()));

        super.onPause();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: ");
        name.setText(parent.onResumeFragment("addevent_name", ""));
        address.setText(parent.onResumeFragment("addevent_address", ""));
        day.setText(parent.onResumeFragment("addevent_day", ""));
        description.setText(parent.onResumeFragment("addevent_description", ""));
        super.onResume();
    }
}
