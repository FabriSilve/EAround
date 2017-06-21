package lpaa.earound;

import android.app.Fragment;
import android.content.res.Configuration;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Fabrizio on 21/06/2017.
 */

public class AddEventFragment extends Fragment implements View.OnClickListener {

    private final String TAG = "AddEventFragment";

    private View view;
    private HomeActivity parent;

    private EditText name;
    private EditText address;
    private EditText day;
    private EditText description;
    private Button add;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: start");
        this.parent = (HomeActivity) this.getActivity();

        view = inflater.inflate(R.layout.addevent_fragment, container, false);

        name = (EditText) view.findViewById(R.id.addevent_name);
        address = (EditText) view.findViewById(R.id.addevent_address);
        day = (EditText) view.findViewById(R.id.addevent_day);
        description = (EditText) view.findViewById(R.id.addevent_description);
        add = (Button) view.findViewById(R.id.addevent_add);

        add.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: start");
        switch(v.getId()) {
            case R.id.addevent_add:
                if (checkEvent()) {
                    EventAdder eventAdder = new EventAdder(this, name.getText().toString(), address.getText().toString(), day.getText().toString(), description.getText().toString());
                    eventAdder.execute();
                    Toast toast = Toast.makeText(parent, R.string.sending, Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(parent, R.string.eventNotValid, Toast.LENGTH_LONG);
                    toast.show();
                }
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
        } else {
            Toast.makeText(parent, result, Toast.LENGTH_LONG).show();
        }
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
