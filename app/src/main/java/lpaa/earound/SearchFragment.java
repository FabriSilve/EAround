package lpaa.earound;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class SearchFragment extends Fragment implements View.OnClickListener {
    //TODO inizializzare componenti layout
    //TODO caricare in locale gli eventi cercati
    //TODO classe ricerca ultimi eventi aggiunti e li carica in locale

    private final String TAG = "SearchFragment";

    private View view;
    private HomeActivity parent;
    private Button search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_fragment, container, false);
        search = (Button) view.findViewById(R.id.search_find);

        search.setOnClickListener(this);
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
        EventSearcher searcher = new EventSearcher(parent, this, null);
        parent.setEvents(searcher.search());
    }

    public void searchDone() {
        Log.d(TAG, "searchDone: research complete");
        parent.goToHome();
    }
}
