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

    private Fragment fragment;
    private String username;
    private String password;

    private String checkLoginUrl = "http://wwww.lpaa17.altervista.org/checkLogin.php"; //"http://webdev.disi.unige.it/~S4110217/Lab3.1/login_success.php";
   // private boolean result;

    public CheckLogin(LoginFragment fragment, String username, String password)
    {
        //Acquisisco le credenziali dal front-end
        this.fragment = fragment;
        this.username = username;
        this.password = password;
    }

    @Override
    protected Boolean doInBackground(Object... params) {
        /*TODO rimuovere debug senza rete
        debug senza rete
        return username.equals("faber");*/

        /*CORRETTO*/
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

            //result = true;
            Log.d("CheckLogin", "doInBackground: "+line);
            return line.equals("true");
        } catch (Exception e) {
            //result = false;
            Log.e("CheckLogin", "doInBackground: Exception: " + e.getMessage());
            return false;
        }


    }

    @Override
    protected void onPostExecute(Boolean result){
        Log.d("CheckLogin", result.toString());
        ((LoginFragment)fragment).checkResult(result);
    }
}
