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
import java.util.ArrayList;
import java.sql.Date;

import lpaa.earound.home.HomeActivity;
import lpaa.earound.type.Event;
import lpaa.earound.type.Search;
import lpaa.earound.database.DBTask;


public class EventSearcher extends AsyncTask<Object, Object, ArrayList<Event>>{

    private final String TAG = "EventSearcher";

    private static final String eventsSearcherUrl = "http://wwww.lpaa17.altervista.org/eventsSearcher.php";
    private HomeActivity main;
    private Search search;

    public EventSearcher(HomeActivity main, Search search) {
        Log.d(TAG, "EventSearcher: costructor");
        this.main = main;
        this.search = search;
    }

    @Override
    public ArrayList<Event> doInBackground(Object... params) {
        Log.d(TAG, "doInBackground: start");

        URLConnection connection;
        OutputStreamWriter wr;
        String dat;
        String line = "" +
                "";
        try {
            //TODO Strutturare ricerca con filtri del search
            Log.d(TAG, "doInBackground: connection output");
            URL url = new URL(eventsSearcherUrl);
            connection = url.openConnection();
            if (search != null) {
                Log.d(TAG, "doInBackground: with search obj");
                dat = URLEncoder.encode("position", "UTF-8") + "=" + URLEncoder.encode(search.getPosition(), "UTF-8") +
                        "&" + URLEncoder.encode("distance", "UTF-8") + "=" + URLEncoder.encode(search.getDistance(), "UTF-8") +
                        "&" + URLEncoder.encode("days", "UTF-8") + "=" + URLEncoder.encode(search.getDays(), "UTF-8");

            } else {
                Log.d(TAG, "doInBackground: without search obj");
                dat = URLEncoder.encode("position", "UTF-8") + "=" + URLEncoder.encode("default", "UTF-8");
            }
            connection.setDoOutput(true);
            wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(dat);
            wr.flush();
            StringBuilder stringBuilder = new StringBuilder();

            Log.d(TAG, "doInBackground: connection in");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                stringBuilder.append(nextLine);
            }
            line = stringBuilder.toString();
            wr.close();
        }catch (IOException e) {
            Log.e(TAG, "doInBackground: Exception \n",e );
        }
        try {
            Log.d(TAG, "doInBackground: Event adding local");
            ArrayList<Event> result = new ArrayList<>();
            JSONArray events = new JSONArray(line);
            for(int i = 0; i<events.length(); i++) {
                JSONObject event = events.getJSONObject(i);
                Log.d(TAG, "doInBackground: add " + event.getString("name"));
                result.add(new Event(
                        event.getInt("id"),
                        event.getString("name"),
                        event.getString("description"),
                        Date.valueOf(event.getString("date")),
                        event.getDouble("lat"),
                        event.getDouble("lon")
                ));
            }
            return result;
        } catch (Exception e) {
            Log.e(TAG, "doInBackground: Exception: " + "\n" + e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Event> events) {
        Log.d(TAG, "onPostExecute: start");
        new DBTask(main).updateEvents(events);
        main.searchDone();
    }
}
