package lpaa.earound.home;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import lpaa.earound.R;


public class MyEventsEdit extends Fragment implements View.OnClickListener {

    private final String TAG = "MyEventEdit";

    private EditText name;
    private EditText address;
    private EditText day;
    private EditText description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.myevents_event_edit, container, false);

        name = (EditText) view.findViewById(R.id.myevent_edit_name);
        address = (EditText) view.findViewById(R.id.myevent_edit_address);
        day = (EditText) view.findViewById(R.id.myevent_edit_day);
        description = (EditText) view.findViewById(R.id.myevent_edit_description);
        Button modify = (Button) view.findViewById(R.id.myevent_edit_modify);

        modify.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");
        switch(v.getId()) {
            case R.id.myevent_edit_modify:
                modifyEvent();
                break;
        }
    }

    private void modifyEvent() {
        Log.d(TAG, "modifyEvent: ");

    }
}
