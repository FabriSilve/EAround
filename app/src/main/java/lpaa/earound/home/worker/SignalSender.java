package lpaa.earound.home.worker;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import lpaa.earound.R;
import lpaa.earound.database.DBTask;
import lpaa.earound.home.FollowedFragment;
import lpaa.earound.type.Event;


public class SignalSender extends AsyncTask<Object, Object, String> {

    private final String TAG = "SignalSender";
    //TODO CREARE FILE SERVER
    private final String URL = "http://wwww.lpaa17.altervista.org/signalReciver.php";

    private Activity parent;
    private Fragment fragment;
    private Event event;
    private boolean isChecked;

    public SignalSender(Activity parent, Fragment fragment, Event event, boolean isChecked) {
        Log.d(TAG, "SignalSender: ");
        this.parent = parent;
        this.fragment = fragment;
        this.event = event;
        this.isChecked = isChecked;
    }

    @Override
    protected String doInBackground(Object... params) {
        Log.d(TAG, "doInBackground: start");
        URLConnection connection;
        OutputStreamWriter wr;
        String dat;
        String user = new DBTask(fragment.getActivity()).getUser();
        try{
            Log.d(TAG, "doInBackground: connection out");
            java.net.URL url = new URL(URL);
            connection = url.openConnection();
            dat = URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(event.getLat()), "UTF-8") +
                    "&"+ URLEncoder.encode("lon", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(event.getLon()), "UTF-8") +
                    "&"+ URLEncoder.encode("day", "UTF-8") + "=" + URLEncoder.encode(event.getDayString(), "UTF-8") +
                    "&"+ URLEncoder.encode("check", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(isChecked), "UTF-8") +
                    "&"+ URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
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

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: "+s);
        if(s.equals("true")) {
            Toast.makeText(parent, R.string.done, Toast.LENGTH_SHORT).show();
        } else if(s.equals("delete")) {

        } else {
            Toast.makeText(parent, R.string.error, Toast.LENGTH_SHORT).show();
        }
    }
}
