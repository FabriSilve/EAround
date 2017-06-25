package lpaa.earound.home.worker;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.ArrayList;

import lpaa.earound.database.DBTask;
import lpaa.earound.home.MyEventsFragment;
import lpaa.earound.type.Event;
import lpaa.earound.type.LocalEvent;


public class EventsImporter extends AsyncTask<Object, Object, ArrayList<LocalEvent>> {

    private final String TAG = "EventImporter";
    private final String URL = "http://wwww.lpaa17.altervista.org/eventsImporter.php";

    private MyEventsFragment fragment;


    public EventsImporter(MyEventsFragment fragment) {
        Log.d(TAG, "EventImporter: ");
        this.fragment = fragment;
    }

    @Override
    protected ArrayList<LocalEvent> doInBackground(Object... params) {
        Log.d(TAG, "doInBackground: start");
        URLConnection connection;
        OutputStreamWriter wr;
        String dat;
        String line = "";
        String owner = new DBTask(fragment.getActivity()).getUser();
        try {
            Log.d(TAG, "doInBackground: connection out");
            java.net.URL url = new URL(URL);
            connection = url.openConnection();
            dat = URLEncoder.encode("owner", "UTF-8") + "=" + URLEncoder.encode(owner, "UTF-8");
            connection.setDoOutput(true);
            wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(dat);
            wr.flush();
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                stringBuilder.append(nextLine);
            }
            line = stringBuilder.toString();
            wr.close();
        } catch(IOException e) {
            Log.e(TAG, "doInBackground: " + e);
        }
        try {
            Log.d(TAG, "doInBackground: Event adding local");
            ArrayList<LocalEvent> result = new ArrayList<>();
            if(!line.equals("")) {
                JSONArray events = new JSONArray(line);
                if (events.length() > 1) {
                    for (int i = 0; i < events.length() - 1; i++) {
                        JSONObject event = events.getJSONObject(i);
                        Log.d(TAG, "doInBackground: add " + event.getString("name"));
                        result.add(new LocalEvent(
                                event.getString("name"),
                                event.getString("description"),
                                Date.valueOf(event.getString("day")),
                                event.getString("address")
                        ));
                    }
                }
            }
            return result;

        } catch (Exception e) {
            Log.e(TAG, "doInBackground: Exception: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    protected void onPostExecute(ArrayList<LocalEvent> result) {
        Log.d(TAG, "onPostExecute: start ");

        new DBTask(fragment.getActivity()).importEvents(result);
        fragment.importedEvents();
    }
}
