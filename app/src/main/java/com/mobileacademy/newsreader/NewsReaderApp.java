package com.mobileacademy.newsreader;

import android.app.Application;

import com.mobileacademy.newsreader.services.FetchArticlesOnWifiTaskService;

/**
 * Created by danielastamati on 25/04/2018.
 */

public class NewsReaderApp extends Application {

    private static NewsReaderApp INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

    }

    public static NewsReaderApp getInstance(){
        return INSTANCE;
    }
}
