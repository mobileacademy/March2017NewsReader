package com.mobileacademy.newsreader.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Locale;

/**
 * Created by danielastamati on 13/04/2018.
 */

public class LocaleChangedReceiver extends BroadcastReceiver {
    private static final String TAG = "LocaleChangedReceiver";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        Locale locale = context.getResources().getConfiguration().locale;
        Log.d(TAG, "onReceive: " + locale.getDisplayLanguage());
    }
}
