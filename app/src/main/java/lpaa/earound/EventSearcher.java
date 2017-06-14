package lpaa.earound;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.sql.Date;


public class EventSearcher{

    private final String TAG = "EventSearcher";

    private String eventsSearcherUrl = "http://wwww.lpaa17.altervista.org/eventsSearcher.php";
    private HomeActivity main;
    private SearchFragment searchFragment;
    private Search search;

    public EventSearcher(HomeActivity main, SearchFragment searchFragment, Search search) {
        this.main = main;
        this.searchFragment = searchFragment;
        this.search = search;
    }

    public ArrayList<Event> search() {
        URLConnection connection;
        OutputStreamWriter wr;
        String dat;
        try {
            /* TODO test offline
            //TODO Strutturare ricerca con filtri del search
            URL url = new URL(eventsSearcherUrl);
            connection = url.openConnection();
            //dat = URLEncoder.encode("posizione", "UTF-8") + "=" + URLEncoder.encode(search.getPosition(), "UTF-8"); //Posizione fittizia
            connection.setDoOutput(true);
            wr = new OutputStreamWriter(connection.getOutputStream());
            //wr.write(dat);
            wr.flush();
            String line;
            StringBuilder stringBuilder = new StringBuilder();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                stringBuilder.append(nextLine);
            }
            line = stringBuilder.toString();
            wr.close();*/

            //TODO rimuobere line inizializzata qui sotto per test offline
            String line =
                    "[" +
                    "   {\"id\":\"1\",\"name\":\"n1\",\"description\":'d1',\"date\":\"2017-06-30\",\"lat\":\"44.425704\",\"lon\":\"8.848104\",\"image\":\"http://montenisa.com/GALLERIE/flyersE/calici_sotto_le_stelle.jpg\"}," +
                    "   {\"id\":\"2\",\"name\":\"n2\",\"description\":\"d2\",\"date\":\"2017-06-30\",\"lat\":\"44.423704\",\"lon\":\"8.847104\",\"image\":\"http://montenisa.com/GALLERIE/flyersE/calici_sotto_le_stelle.jpg\"}" +
                    "]";

            ArrayList<Event> result = new ArrayList<>();
            //JSONObject json = new JSONObject(line);
            JSONArray events = new JSONArray(line);
            for(int i = 0; i<events.length(); i++) {
                JSONObject event = events.getJSONObject(i);
                result.add(new Event(
                        event.getInt("id"),
                        event.getString("name"),
                        event.getString("description"),
                        null, //new Date(event.getString("date")),
                        event.getDouble("lat"),
                        event.getDouble("lon"),
                        event.getString("image")
                ));
            }
            return result;
        } catch (Exception e) {
            //result = false;
            Log.e(TAG, "search: Exception: " + e.getMessage());
            return new ArrayList<Event>();
        }
    }
}
