package lpaa.earound.home.worker;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
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
import lpaa.earound.home.FollowedFragment;
import lpaa.earound.type.Event;


public class FollowedImporter extends AsyncTask<Object, Object, ArrayList<Event>> {

    private final String TAG = "FollowedImporter";
    private final String URL = "http://wwww.lpaa17.altervista.org/followedImporter.php";

    private FollowedFragment fragment;
    private boolean isOk = true;


    public FollowedImporter(FollowedFragment fragment) {
        Log.d(TAG, "FollowedImporter: ");
        this.fragment = fragment;
    }

    @Override
    protected ArrayList<Event> doInBackground(Object... params) {
        Log.d(TAG, "doInBackground: start");
        URLConnection connection;
        OutputStreamWriter wr;
        String dat;
        String line = "";
        String user = new DBTask(fragment.getActivity()).getUser();
        try {
            Log.d(TAG, "doInBackground: connection out");
            java.net.URL url = new URL(URL);
            connection = url.openConnection();
            dat = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
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
            isOk = false;
        }
        try {
            Log.d(TAG, "doInBackground: Event adding local");
            ArrayList<Event> result = new ArrayList<>();
            if(!line.equals("")) {
                JSONArray events = new JSONArray(line);
                for (int i = 0; i < events.length() - 1; i++) {
                    JSONObject event = events.getJSONObject(i);
                    Log.d(TAG, "doInBackground: add " + event.getString("name"));
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
            }
            return result;

        } catch (Exception e) {
            Log.e(TAG, "doInBackground: Exception: " + e);
            e.printStackTrace();
            isOk = false;
            return new ArrayList<>();
        }
    }

    protected void onPostExecute(ArrayList<Event> result) {
        Log.d(TAG, "onPostExecute: start ");

        if (isOk) {
            new DBTask(fragment.getActivity()).importFollowedEvents(result);
        }
        fragment.importedEvents(isOk);
    }
}
