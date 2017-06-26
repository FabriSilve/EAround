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
        this.main = main;
        this.search = search;
    }

    @Override
    public ArrayList<Event> doInBackground(Object... params) {
        URLConnection connection;
        OutputStreamWriter wr;
        String dat;
        String line = "";
        try {
            URL url = new URL(eventsSearcherUrl);
            connection = url.openConnection();
            if (search != null) {
                dat = URLEncoder.encode("position", "UTF-8") + "=" + URLEncoder.encode(search.getPosition(), "UTF-8") +
                        "&" + URLEncoder.encode("distance", "UTF-8") + "=" + URLEncoder.encode(search.getDistance(), "UTF-8") +
                        "&" + URLEncoder.encode("days", "UTF-8") + "=" + URLEncoder.encode(search.getDays(), "UTF-8");
                if(search.getLat() != 0.0 && search.getLon() != 0.0) {
                    dat += "&" + URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(search.getLat()), "UTF-8") +
                            "&" + URLEncoder.encode("lon", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(search.getLon()), "UTF-8");
                }
            } else {
                dat = URLEncoder.encode("position", "UTF-8") + "=" + URLEncoder.encode("default", "UTF-8");
            }
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
        }catch (IOException e) {
            Log.e(TAG, "doInBackground: Exception \n",e );
        }
        try {
            ArrayList<Event> result = new ArrayList<>();
            JSONArray events = new JSONArray(line);
            for(int i = 0; i<events.length(); i++) {
                JSONObject event = events.getJSONObject(i);
                result.add(new Event(
                        event.getInt("id"),
                        event.getString("name"),
                        event.getString("description"),
                        Date.valueOf(event.getString("day")),
                        event.getString("address"),
                        event.getDouble("lat"),
                        event.getDouble("lon"),
                        event.getString("owner")
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
        new DBTask(main).updateEvents(events);
        main.searchDone();
    }
}
