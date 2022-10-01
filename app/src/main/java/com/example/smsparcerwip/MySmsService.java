package com.example.smsparcerwip;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MySmsService extends Service {

    private static final String TAG = "ServiceTag";
    //TODO: Add a proper comparing mechanism!
    private static final String allowedSenders = "Unibank";

    public MySmsService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Тут сделать все необходимое для работы с API
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String msgSender = intent.getExtras().getString("sender");
        if (allowedSenders.contains(msgSender)) {
            String msgBody = intent.getExtras().getString("body");
            Log.d(TAG, msgBody);
        }
        stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}