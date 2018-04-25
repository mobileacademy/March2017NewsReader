package com.mobileacademy.newsreader;

import android.app.Application;

/**
 * Created by danielastamati on 25/04/2018.
 */

public class NewsReaderApp extends Application {

    private static NewsReaderApp INSTANCE;

    public NewsReaderApp getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }

    public static NewsReaderApp getInstance(){
        return INSTANCE;
    }
}
