package lpaa.earound;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class EventAdder extends AsyncTask<Object, Object, String> {

    private final String TAG = "EventAdder";
    private final String URL = "http://wwww.lpaa17.altervista.org/eventAdder.php";

    private AddEventFragment fragment;
    private String name;
    private String address;
    private String day;
    private String description;

    public EventAdder(AddEventFragment fragment, String name, String address, String day, String description) {
        this.fragment = fragment;
        this.name = name;
        this.address = address;
        this.day = day;
        this.description = description;
    }

    //TODO tenere traccia dell'utente che crea l'evento

    @Override
    protected String doInBackground(Object... params) {
        Log.d(TAG, "doInBackground: start");
        URLConnection connection;
        OutputStreamWriter wr;
        String dat;
        try{
            Log.d(TAG, "doInBackground: connection out");
            URL url = new URL(URL);
            connection = url.openConnection();
            Log.d(TAG, name + "; " + address + "; " + day);
            dat = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") +
                    "&"+ URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8") +
                    "&"+ URLEncoder.encode("day", "UTF-8") + "=" + URLEncoder.encode(day, "UTF-8") +
                    "&"+ URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(description, "UTF-8");
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
            return line;

        } catch (Exception e) {
            Log.e(TAG, "doInBackground: Exception: " + e.getMessage());
            return "error";
        }
    }

    protected void onPostExecute(String result) {
        Log.d(TAG, "onPostExecute: start");
        //TODO aggiungere evento creato in db locale
        fragment.checkResult(result);
    }
}
