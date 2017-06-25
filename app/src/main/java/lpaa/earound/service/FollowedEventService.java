package lpaa.earound.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;


public class FollowedEventService extends Service {

    private final String TAG = "FollowedService";

    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        context = this.getApplication().getApplicationContext();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "run: ");
                FollowedEventDateChecker checker = new FollowedEventDateChecker(context);
                checker.execute();
            }
        };
        Timer timer = new Timer(true);
        timer.schedule(task, 100000, 100000);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
