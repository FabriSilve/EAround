package lpaa.earound.home.worker;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import lpaa.earound.home.AddEventFragment;
import lpaa.earound.type.LocalEvent;


public class EventAdder extends AsyncTask<Object, Object, String> {

    private static final String TAG = "EventAdder";
    private static final String URL = "http://wwww.lpaa17.altervista.org/eventAdder.php";

    private AddEventFragment fragment;
    private LocalEvent event;
    private String owner;

    public EventAdder(AddEventFragment fragment, LocalEvent event, String owner) {
        this.fragment = fragment;
        this.event = event;
        this.owner = owner;
    }

    @Override
    protected String doInBackground(Object... params) {
        URLConnection connection;
        OutputStreamWriter wr;
        String dat;
        try{
            URL url = new URL(URL);
            connection = url.openConnection();
            Log.d(TAG, event.getName() + "; " + event.getAddress() + "; " + event.getDayString());
            dat = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(event.getName(), "UTF-8") +
                    "&"+ URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(event.getAddress(), "UTF-8") +
                    "&"+ URLEncoder.encode("day", "UTF-8") + "=" + URLEncoder.encode(event.getDayString(), "UTF-8") +
                    "&"+ URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(event.getDescription(), "UTF-8") +
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
        Log.i(TAG, "onPostExecute: "+result);

        //TODO tradurre messaggi server in messaggi per utente
        fragment.checkResult(result);

    }
}
