package lpaa.earound.home.worker;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import lpaa.earound.database.DBTask;
import lpaa.earound.home.MyEventsFragment;
import lpaa.earound.type.LocalEvent;


public class EventUpdater extends AsyncTask<Object, Object, String> {

    private final String TAG = "EventUpdater";
    private final String URL = "http://wwww.lpaa17.altervista.org/eventUpdater.php";

    private MyEventsFragment fragment;
    private LocalEvent newEvent;
    private LocalEvent oldEvent;
    private String owner;

    public EventUpdater(MyEventsFragment fragment, LocalEvent newEvent, String owner) {
        Log.d(TAG, "EventUpdater: ");
        this.fragment = fragment;
        this.newEvent = newEvent;
        this.oldEvent = fragment.getOldEvent();
        this.owner = owner;
        Log.d(TAG, "EventUpdater: old name= "+oldEvent.getName());
    }

    @Override
    protected String doInBackground(Object... params) {
        Log.d(TAG, "doInBackground: start");
        URLConnection connection;
        OutputStreamWriter wr;
        String dat;
        try{
            Log.d(TAG, "doInBackground: connection out");
            java.net.URL url = new URL(URL);
            connection = url.openConnection();
            dat = URLEncoder.encode("newName", "UTF-8") + "=" + URLEncoder.encode(newEvent.getName(), "UTF-8") +
                    "&"+ URLEncoder.encode("newAddress", "UTF-8") + "=" + URLEncoder.encode(newEvent.getAddress(), "UTF-8") +
                    "&"+ URLEncoder.encode("newDay", "UTF-8") + "=" + URLEncoder.encode(newEvent.getDayString(), "UTF-8") +
                    "&"+ URLEncoder.encode("newDescription", "UTF-8") + "=" + URLEncoder.encode(newEvent.getDescription(), "UTF-8") +
                    "&"+ URLEncoder.encode("oldName", "UTF-8") + "=" + URLEncoder.encode(oldEvent.getName(), "UTF-8") +
                    "&"+ URLEncoder.encode("oldAddress", "UTF-8") + "=" + URLEncoder.encode(oldEvent.getAddress(), "UTF-8") +
                    "&"+ URLEncoder.encode("oldDay", "UTF-8") + "=" + URLEncoder.encode(oldEvent.getDayString(), "UTF-8") +
                    "&"+ URLEncoder.encode("oldDescription", "UTF-8") + "=" + URLEncoder.encode(oldEvent.getDescription(), "UTF-8") +
                    "&"+ URLEncoder.encode("owner", "UTF-8") + "=" + URLEncoder.encode(owner, "UTF-8");
            connection.setDoOutput(true);
            wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(dat);
            wr.flush();
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                stringBuilder.append(nextLine);
            }
            line = stringBuilder.toString();
            wr.close();
            return line;

        } catch (Exception e) {
            Log.e(TAG, "doInBackground: Exception: " + e.getMessage());
            return "error";
        }
    }

    protected void onPostExecute(String result) {
        Log.d(TAG, "onPostExecute: start ");
        Log.d(TAG, "onPostExecute: "+result);

        //TODO Aggiornare eventi in locale
        if (result.equals("true"))
            new DBTask(fragment.getActivity()).updateLocalEvent(oldEvent, newEvent);

        //TODO tradurre messaggi server in messaggi per utente
        fragment.updatedEvent(result);
    }
}
