package lpaa.earound;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by cogor on 18/05/2017.
 */

public class CheckRegistration extends AsyncTask<Object, Object, Boolean> {

    private RegisterFragment fragment;
    protected String username;
    private String password;
    private String email;
    private String checkRegistrationUrl = "http://wwww.lpaa17.altervista.org/checkRegistration.php";
    //private boolean result;


    public CheckRegistration(RegisterFragment fragment, String username, String password, String email)
    {
        this.fragment = fragment;
        this.username = username;
        this.password = password;
        this.email = email;
    }


    @Override
    protected Boolean doInBackground(Object... params) {
        /*TODO rimuovere debug senza rete
        //debug senza rete
        return email.equals("fabri@mail.it");*/

        /*CORRETTO*/
        URLConnection connection;
        OutputStreamWriter wr;
        String dat;
        try{
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

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                stringBuilder.append(nextLine);
            }
            line = stringBuilder.toString();
            wr.close();

            //result = true;
            Log.d("CheckLogin", "doInBackground: "+line);
            return line.equals("true");

        } catch (Exception e) {
            //result = false;
            Log.e("CheckLogin", "doInBackground: Exception: " + e.getMessage());
            return false;
        }
    }

    protected void onPostExecute(Boolean result) {
        Log.d("CheckRegistration", "onPostExecute");
        fragment.checkResult(result);
    }
}
