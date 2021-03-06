package lpaa.earound.home;

import android.app.Fragment;
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
import android.widget.Toast;

import java.util.ArrayList;

import lpaa.earound.R;
import lpaa.earound.database.DBTask;
import lpaa.earound.home.worker.DeleteListener;
import lpaa.earound.home.worker.FollowedImporterListener;
import lpaa.earound.type.Event;


public class FollowedFragment extends Fragment {

    private final String TAG = "FollowedFragment";

    private HomeActivity parent;

    private ScrollView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.parent = (HomeActivity) this.getActivity();

        View view = inflater.inflate(R.layout.followed_fragment, container, false);
        list = (ScrollView) view.findViewById(R.id.followed_list);

        eventsDrawer();

        return view;
    }

    public void initUI() {
        list.removeAllViews();
        eventsDrawer();
    }

    private void eventsDrawer() {
        ArrayList<Event> events = new DBTask(parent).getFollowedEvents();
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

                        TextView owner = new TextView(parent);
                        owner.setText(events.get(i).getOwner());
                        owner.setTextSize(getResources().getDimension(R.dimen.par_textSize));
                        owner.setTextColor(Color.DKGRAY);
                        owner.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                        owner.setPadding(
                                (int) getResources().getDimension(R.dimen.marginMin),
                                (int) getResources().getDimension(R.dimen.marginMin),
                                (int) getResources().getDimension(R.dimen.marginMin),
                                (int) getResources().getDimension(R.dimen.marginMin)
                        );
                        owner.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        eventLayout.addView(owner);

                        TextView delete = new TextView(parent);
                        delete.setText(R.string.delete);
                        delete.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        delete.setTextSize(getResources().getDimension(R.dimen.title2_textSize));
                        delete.setTypeface(delete.getTypeface(), Typeface.BOLD);
                        delete.setTextColor(Color.rgb(255, 128, 0));
                        delete.setPadding(
                                (int) getResources().getDimension(R.dimen.marginMin),
                                (int) getResources().getDimension(R.dimen.marginMin),
                                (int) getResources().getDimension(R.dimen.marginMin),
                                (int) getResources().getDimension(R.dimen.marginMin)
                        );
                        delete.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        DeleteListener listener = new DeleteListener(parent, this, events.get(i));
                        delete.setOnClickListener(listener);
                        eventLayout.addView(delete);


                        //TODO aprire schermata con dettagli evento e mappa
                        /*FollowedEventListener listener = new FollowedEventListener(events.get(i), this);
                        eventLayout.setOnClickListener(listener);*/

                        listevent.addView(eventLayout);
                    }
                } else {
                    LinearLayout noEventLayout = new LinearLayout(parent);
                    noEventLayout.setOrientation(LinearLayout.VERTICAL);
                    noEventLayout.setMinimumWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                    noEventLayout.setMinimumHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
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
                    message.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
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

            FollowedImporterListener importer = new FollowedImporterListener(this);
            endButton.setOnClickListener(importer);

            listevent.addView(endButton);
            list.addView(listevent);
        }
    }

    public void importedEvents(boolean result) {
        if(result) {
            Toast.makeText(parent, R.string.imported, Toast.LENGTH_SHORT).show();
            list.removeAllViews();
            eventsDrawer();
        } else {
            Toast.makeText(parent, R.string.error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
