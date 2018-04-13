package com.mobileacademy.newsreader.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.mobileacademy.newsreader.activities.MainActivity;

public class CounterService extends Service {

    private static final String TAG = "CounterService";
    
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        startCountingThread();
        return START_STICKY;
    }

    private void startCountingThread() {

        //the code the will run() inside the thread
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                count();
                stopSelf();
            }
        };

        new Thread(runnable).start();

    }


    private void count() {
        int i = 0;
        while (i < 5) {
            i++;
            Log.d(TAG, "second: " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        Intent intent = new Intent(MainActivity.INTENT_ACTION_TIMES_UP);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
