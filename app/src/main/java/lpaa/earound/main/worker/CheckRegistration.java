package lpaa.earound.main.worker;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import lpaa.earound.main.RegisterFragment;


public class CheckRegistration extends AsyncTask<Object, Object, Boolean> {

    private final String TAG = "CheckRegistration";

    private RegisterFragment fragment;
    protected String username;
    private String password;
    private String email;
    private static final String checkRegistrationUrl = "http://wwww.lpaa17.altervista.org/checkRegistration.php";


    public CheckRegistration(RegisterFragment fragment, String username, String password, String email)
    {
        Log.d(TAG, "CheckRegistration: costructor");
        this.fragment = fragment;
        this.username = username;
        this.password = password;
        this.email = email;
    }


    @Override
    protected Boolean doInBackground(Object... params) {
        Log.d(TAG, "doInBackground: start");
        URLConnection connection;
        OutputStreamWriter wr;
        String dat;
        try{
            Log.d(TAG, "doInBackground: connection out");
            URL url = new URL(checkRegistrationUrl);
            connection = url.openConnection();
            Log.d("RegistrationCheck", username + "; " + email + "; " + password);
            dat = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") +
                    "&"+ URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") +
                    "&"+ URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
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

            return line.equals("true");

        } catch (Exception e) {
            Log.e(TAG, "doInBackground: Exception: " + e.getMessage());
            return false;
        }
    }

    protected void onPostExecute(Boolean result) {
        Log.d(TAG, "onPostExecute: start");
        fragment.checkResult(result);
    }
}
