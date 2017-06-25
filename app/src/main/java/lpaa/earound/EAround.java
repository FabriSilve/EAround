package lpaa.earound;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import lpaa.earound.service.FollowedEventService;


public class EAround extends Application {

    private final String TAG = "EAround";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");

        Intent followedService = new Intent (this, FollowedEventService.class);
        startService(followedService);
    }
}
