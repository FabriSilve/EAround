package lpaa.earound;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;


public class SearchFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    //TODO inizializzare componenti layout
    //TODO caricare in locale gli eventi cercati
    //TODO classe ricerca ultimi eventi aggiunti e li carica in locale

    private final String TAG = "SearchFragment";

    private View view;
    private HomeActivity parent;

    private EditText position;
    private SeekBar distance;
    private TextView distanceLabel;
    private SeekBar days;
    private TextView daysLabel;
    /*private CheckBox party;
    private CheckBox cultural;
    private CheckBox sport;
    private CheckBox music;*/
    private Button find;
    private CheckBox rememberPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_fragment, container, false);

        position = (EditText) view.findViewById(R.id.search_position);
        distance = (SeekBar) view.findViewById(R.id.search_distanceBar);
        distanceLabel = (TextView) view.findViewById(R.id.search_distanceLabel);
        days = (SeekBar) view.findViewById(R.id.search_daysBar);
        daysLabel = (TextView) view.findViewById(R.id.search_daysLabel);
        /*party = (CheckBox) view.findViewById(R.id.search_partyBox);
        cultural = (CheckBox) view.findViewById(R.id.search_culturalBox);
        sport = (CheckBox) view.findViewById(R.id.search_sportBox);
        music = (CheckBox) view.findViewById(R.id.search_musicBox);*/
        find = (Button) view.findViewById(R.id.search_find);
        rememberPosition = (CheckBox) view.findViewById(R.id.search_rememberPosition);

        distance.setOnSeekBarChangeListener(this);
        days.setOnSeekBarChangeListener(this);
        find.setOnClickListener(this);

        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void setParent(HomeActivity parent) {
        this.parent = parent;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.search_find:
                startSearch();
                break;
        }
    }

    private void startSearch() {
        Search search = new Search(
                position.getText().toString(),
                distance.getProgress(),
                days.getProgress()
                /*party.isChecked(),
                cultural.isChecked(),
                sport.isChecked(),
                music.isChecked()*/
        );

        //TODO se rememberPOsition è checkato salvare la posizione in locale

        EventSearcher searcher = new EventSearcher(parent, this, search);
        searcher.execute();
    }

    public void searchDone() {
        Log.d(TAG, "searchDone: research complete");
        parent.goToHome();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        seekBarUpdate(seekBar);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //seekBarUpdate(seekBar);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //seekBarUpdate(seekBar);
    }

    private void seekBarUpdate(SeekBar seekBar) {
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
}
