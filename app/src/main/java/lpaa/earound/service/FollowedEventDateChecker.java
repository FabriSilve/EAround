package lpaa.earound.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import lpaa.earound.R;
import lpaa.earound.database.DBTask;
import lpaa.earound.home.HomeActivity;

public class FollowedEventDateChecker extends AsyncTask<Object, Object, Boolean> {

    private final String TAG = "EventDateChecker";

    private Context context;

    public FollowedEventDateChecker(Context context) {
        Log.d(TAG, "FollowedEventDateChecker: ");
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Object... params) {
        Log.d(TAG, "doInBackground: ");
        return new DBTask(context).thereAreEventsToday();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        Log.d(TAG, "onPostExecute: ");
        if(result) {
            Log.d(TAG, "onPostExecute: notifica");
            Intent notificationInten = new Intent(context, HomeActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            int flags = PendingIntent.FLAG_UPDATE_CURRENT;
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationInten, flags);

            int icon = R.mipmap.ic_launcher;
            CharSequence tickerText = "Attenzione! Hai eventi oggi";
            CharSequence contentTitle = "EAround";
            CharSequence contentText = "Ci sono degli eventi che segui che si svolgono oggi";

            Notification notification = new NotificationCompat.Builder(context)
                    .setSmallIcon(icon)
                    .setTicker(tickerText)
                    .setContentTitle(contentTitle)
                    .setContentText(contentText)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();

            NotificationManager manager  = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            final int NOTIFICATION_ID = 1;
            manager.notify(NOTIFICATION_ID, notification);

        } else {
            Log.d(TAG, "onPostExecute: niente notifica");
        }
    }
}
