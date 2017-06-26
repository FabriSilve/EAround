package lpaa.earound.main.worker;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import lpaa.earound.main.LoginFragment;


public class CheckLogin extends AsyncTask<Object, Object, Boolean> {

    private final String TAG =" CheckLogin";

    private LoginFragment fragment;
    private String username;
    private String password;

    private static final String checkLoginUrl = "http://wwww.lpaa17.altervista.org/checkLogin.php";

    public CheckLogin(LoginFragment fragment, String username, String password) {
        this.fragment = fragment;
        this.username = username;
        this.password = password;
    }

    @Override
    protected Boolean doInBackground(Object... params) {
        String dati;
        try {
            URLConnection connection;
            OutputStreamWriter wr;

            URL url = new URL(checkLoginUrl);
            connection = url.openConnection();
            dati = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") +
                    "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            connection.setDoOutput(true);
            wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(dati);
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

            return line.equals("true");
        } catch (Exception e) {
            Log.e(TAG, "doInBackground: Exception: " + e.getMessage());
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result){
        fragment.checkResult(result);
    }
}
