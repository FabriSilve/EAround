package lpaa.earound;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class SearchFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private final String TAG = "SearchFragment";

    private HomeActivity parent;

    private EditText position;
    private SeekBar distance;
    private TextView distanceLabel;
    private SeekBar days;
    private TextView daysLabel;
    private Button find;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: init UI");

        this.parent = (HomeActivity) this.getActivity();
        View view = inflater.inflate(R.layout.search_fragment, container, false);

        position = (EditText) view.findViewById(R.id.search_position);
        distance = (SeekBar) view.findViewById(R.id.search_distanceBar);
        distanceLabel = (TextView) view.findViewById(R.id.search_distanceLabel);
        days = (SeekBar) view.findViewById(R.id.search_daysBar);
        daysLabel = (TextView) view.findViewById(R.id.search_daysLabel);
        find = (Button) view.findViewById(R.id.search_find);
        find.setClickable(true);

        //TODO Estrarre listener
        distance.setOnSeekBarChangeListener(this);
        days.setOnSeekBarChangeListener(this);
        find.setOnClickListener(this);

        onResume();

        return view;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: start");
        switch(v.getId()) {
            case R.id.search_find:
                find.setClickable(false);
                startSearch();
                break;
        }
    }

    private void startSearch() {
        Log.d(TAG, "startSearch: start");
        Search search = new Search(
                position.getText().toString(),
                distance.getProgress(),
                days.getProgress()
        );

        EventSearcher searcher = new EventSearcher(parent, search);
        searcher.execute();
        Toast toast = Toast.makeText(parent, "Searching", Toast.LENGTH_SHORT);
        toast.show();
        parent.postSearch();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.d(TAG, "onProgressChanged: start");
        seekBarUpdate(seekBar);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.d(TAG, "onStartTrackingTouch: start");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.d(TAG, "onStopTrackingTouch: start");
    }

    private void seekBarUpdate(SeekBar seekBar) {
        Log.d(TAG, "seekBarUpdate: ");
        String text;
        switch (seekBar.getId()) {
            case R.id.search_distanceBar:
                text = distance.getProgress()*5 + " km";
                distanceLabel.setText(text);
                break;
            case R.id.search_daysBar:
                text = "In the next " + (days.getProgress()+1) + " day(s)";
                daysLabel.setText(text);
                break;
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
        parent.onPauseFragment("search_position", position.getText().toString());
        parent.onPauseFragment("search_distance", String.valueOf(distance.getProgress()));
        parent.onPauseFragment("search_distance", String.valueOf(days.getProgress()));

        super.onPause();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: ");
        position.setText(parent.onResumeFragment("search_position", ""));
        distance.setProgress(Integer.valueOf(parent.onResumeFragment("search_distance", "1")));
        days.setProgress(Integer.valueOf(parent.onResumeFragment("search_days", "1")));
        super.onResume();
    }
}
