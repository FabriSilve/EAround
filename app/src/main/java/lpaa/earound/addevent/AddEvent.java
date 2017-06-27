package lpaa.earound.addevent;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import lpaa.earound.R;
import lpaa.earound.database.DBTask;
import lpaa.earound.home.DatePickerFragment;
import lpaa.earound.home.HomeActivity;
import lpaa.earound.home.worker.EventAdder;
import lpaa.earound.type.LocalEvent;

public class AddEvent extends ActionBarActivity implements OnClickListener{

    private int serverResponseCode = 0;
    private ProgressDialog dialog = null;

    private String upLoadServerUri = "http://wwww.lpaa17.altervista.org/fileUpload.php";
    private String imagepath=null;

    private LocalEvent newEvent;

    private EditText name;
    private EditText address;
    private EditText day;
    private EditText description;
    private ImageView photo;
    private Button add;
    private Button back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.addevent_fragment);
        name = (EditText) findViewById(R.id.addevent_name);
        address = (EditText) findViewById(R.id.addevent_address);
        day = (EditText) findViewById(R.id.addevent_day);
        description = (EditText) findViewById(R.id.addevent_description);
        photo = (ImageView) findViewById(R.id.addevent_photo);
        add = (Button) findViewById(R.id.addevent_add);
        back = (Button) findViewById(R.id.addevent_back);

        day.setInputType(InputType.TYPE_NULL);

        photo.setOnClickListener(this);
        day.setOnClickListener(this);
        add.setOnClickListener(this);
        back.setOnClickListener(this);

        ImageView img= new ImageView(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.addevent_add:
                addClick();
                break;
            case R.id.addevent_day:
                showDatePickerDialog(day);
                break;
            case R.id.addevent_photo:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
                break;
            case R.id.addevent_back:
                Intent openMain= new Intent(AddEvent.this,HomeActivity.class);
                startActivity(openMain);
                this.finish();
        }
    }

    private void addClick() {
        if (checkEvent()) {
            newEvent = new LocalEvent(
                    name.getText().toString(),
                    description.getText().toString(),
                    Date.valueOf(day.getText().toString()),
                    address.getText().toString()
            );
            /*EventAdder eventAdder = new EventAdder(
                    this,
                    newEvent,
                    new DBTask(this).getUser()
            );*/
            dialog = ProgressDialog.show(AddEvent.this, "", "Uploading file...", true);
            new Thread(new Runnable() {
                public void run() {
                    uploadFile(imagepath);
                }
            }).start();
            /*eventAdder.execute();
            Toast toast = Toast.makeText(this, R.string.sending, Toast.LENGTH_SHORT);
            toast.show();*/
        } else {
            Toast toast = Toast.makeText(this, R.string.eventNotValid, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private boolean checkEvent() {
        if(name.getText().toString().trim().isEmpty()) {
            name.setError(getText(R.string.empty_field));
            name.requestFocus();
            return false;
        }

        if(address.getText().toString().trim().isEmpty()) {
            address.setError(getText(R.string.empty_field));
            address.requestFocus();
            return false;
        }

        if(day.getText().toString().trim().isEmpty()) {
            day.setError(getText(R.string.empty_field));
            day.requestFocus();
            return false;
        }

        if(description.getText().toString().trim().isEmpty()) {
            description.setError(getText(R.string.empty_field));
            description.requestFocus();
            return false;
        }
        return true;
    }

    public void showDatePickerDialog( EditText day) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setDay(day);
        newFragment.show(getFragmentManager(), "datePicker");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 1:
                    Uri selectedImage = data.getData();
                    imagepath = getPath(selectedImage);
                    Bitmap bitmap=BitmapFactory.decodeFile(imagepath);
                    photo.setImageBitmap(bitmap);
                    break;
            }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public int uploadFile(String sourceFileUri) {

        //sourceFileUri.replace(sourceFileUri, "ashifaq");
        //

        int day, month, year;
        int second, minute, hour;
        GregorianCalendar date = new GregorianCalendar();

        day = date.get(Calendar.DAY_OF_MONTH);
        month = date.get(Calendar.MONTH);
        year = date.get(Calendar.YEAR);

        second = date.get(Calendar.SECOND);
        minute = date.get(Calendar.MINUTE);
        hour = date.get(Calendar.HOUR);

        String name=(hour+""+minute+""+second+""+day+""+(month+1)+""+year);
        String tag=name+".jpg";
        String fileName = sourceFileUri.replace(sourceFileUri,tag);

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            dialog.dismiss();

            Log.e("uploadFile", "Source File not exist :"+imagepath);

            new android.app.AlertDialog.Builder(this).setMessage("Source File not exist :"+ imagepath).show();
            /*runOnUiThread(new Runnable() {
                public void run() {
                    new android.app.AlertDialog.Builder().setMessage("Connection failed. Error code: "+connectionResult.getErrorCode()).show();
                    AlertDialog("Source File not exist :"+ imagepath).show();
                }
            });*/

            return 0;

        }
        else
        {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);




                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){

                    runOnUiThread(new Runnable() {
                        public void run() {
                            /*String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                    +" C:/wamp/wamp/www/uploads";*/
                            Toast.makeText(AddEvent.this, "File Upload Complete.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                dialog.dismiss();
                ex.printStackTrace();

                /*runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(AddEvent.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                    }
                });*/

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                dialog.dismiss();
                e.printStackTrace();

                /*runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(AddEvent.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                    }
                });*/
                Log.e("Upload file to server", "Exception : "  + e.getMessage(), e);
            }
            dialog.dismiss();
            return serverResponseCode;

        }
    }
}

