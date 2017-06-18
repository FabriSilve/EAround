package lpaa.earound;

import android.app.Fragment;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class CheckLogin extends AsyncTask<Object, Object, Boolean> {

    private final String TAG =" CheckLogin";

    private LoginFragment fragment;
    private String username;
    private String password;

    private static final String checkLoginUrl = "http://wwww.lpaa17.altervista.org/checkLogin.php"; //"http://webdev.disi.unige.it/~S4110217/Lab3.1/login_success.php";

    public CheckLogin(LoginFragment fragment, String username, String password)
    {
        Log.d(TAG, "CheckLogin: costructor");
        this.fragment = fragment;
        this.username = username;
        this.password = password;
    }

    @Override
    protected Boolean doInBackground(Object... params) {
        /*TODO rimuovere debug senza rete
        debug senza rete*/
        return username.equals("faber");

        /*CORRETTO
        Log.d(TAG, "doInBackground: start");
        String dati;
        try {
            URLConnection connection;
            OutputStreamWriter wr;

            Log.d(TAG, "doInBackground: URL connection output");
            URL url = new URL(checkLoginUrl);
            connection = url.openConnection();
            dati = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") +
                    "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            connection.setDoOutput(true);
            wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(dati);
            wr.flush();

            Log.d(TAG, "doInBackground: URL connection input");
            String line;
            StringBuilder stringBuilder = new StringBuilder();
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
        }*/
    }

    @Override
    protected void onPostExecute(Boolean result){
        Log.d(TAG, "onPostExecute: start");
        fragment.checkResult(result);
    }
}
