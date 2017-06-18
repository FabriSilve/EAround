package lpaa.earound;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.sql.Date;


public class EventSearcher extends AsyncTask<Object, Object, ArrayList<Event>>{

    private final String TAG = "EventSearcher";

    //TODO estrarre variabili come url in un file a parte
    private static final String eventsSearcherUrl = "http://wwww.lpaa17.altervista.org/eventsSearcher.php";
    private HomeActivity main;
    private Search search;

    //TODO servono tutti questi campi??
    public EventSearcher(HomeActivity main, Search search) {
        Log.d(TAG, "EventSearcher: costructor");
        this.main = main;
        this.search = search;
    }

    @Override
    public ArrayList<Event> doInBackground(Object... params) {
        Log.d(TAG, "doInBackground: start");
        /*CORRETTO
        URLConnection connection;
        OutputStreamWriter wr;
        String dat;
        try {
            //TODO Strutturare ricerca con filtri del search
            Log.d(TAG, "doInBackground: connection output");
            URL url = new URL(eventsSearcherUrl);
            connection = url.openConnection();
            if(search != null) {
                Log.d(TAG, "doInBackground: with search obj");
                dat = URLEncoder.encode("position", "UTF-8") + "=" + URLEncoder.encode(search.getPosition(), "UTF-8") +
                    "&" + URLEncoder.encode("distance","UTF-8") + "=" + URLEncoder.encode(search.getDistance(), "UTF-8") +
                    "&" + URLEncoder.encode("days","UTF-8") + "=" + URLEncoder.encode(search.getDays(), "UTF-8")/* +
                    "&" + URLEncoder.encode("party","UTF-8") + "=" + URLEncoder.encode(search.getParty(), "UTF-8") +
                    "&" + URLEncoder.encode("cultural","UTF-8") + "=" + URLEncoder.encode(search.getCultural(), "UTF-8") +
                    "&" + URLEncoder.encode("sport","UTF-8") + "=" + URLEncoder.encode(search.getSport(), "UTF-8") +
                    "&" + URLEncoder.encode("music","UTF-8") + "=" + URLEncoder.encode(search.getMusic(), "UTF-8")*/;

            /*} else {
                Log.d(TAG, "doInBackground: without search obj");
                dat = URLEncoder.encode("position", "UTF-8") + "=" + URLEncoder.encode("default", "UTF-8");
            }
            connection.setDoOutput(true);
            wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(dat);
            wr.flush();
            String line;
            StringBuilder stringBuilder = new StringBuilder();

            Log.d(TAG, "doInBackground: connection in");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                stringBuilder.append(nextLine);
            }
            line = stringBuilder.toString();
            wr.close();
            */

        //TODO rimuovere debug senza connessione
        try{
            //TODO rimuobere line inizializzata qui sotto per test offline
            String line =
                "[" +
                "   {\"id\":\"1\",\"name\":\"n1\",\"description\":'d1',\"date\":\"2017-06-30\"}," +
                "   {\"id\":\"2\",\"name\":\"n2\",\"description\":\"d2\",\"date\":\"2017-06-30\"}" +
                "]";
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
                        Date.valueOf(event.getString("date"))
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
        //TODO aggiornare DB qui e poi chiamare aggiornamento della ui
        new DBTask(main).insertEvents(events);

        main.searchDone();
    }
}
