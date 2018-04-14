package com.mobileacademy.newsreader.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

/**
 * Created by danielastamati on 14/04/2018.
 */

public class FetchPackagesIntentService extends IntentService {


    private static final String TAG = "FetchPackagesIntentServ";
    
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public FetchPackagesIntentService(String name) {
        super(name);
    }

    public FetchPackagesIntentService(){
        super("FetchPackagesIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        listPackages();
    }

    private void listPackages() {

        PackageManager pckManager = getPackageManager();

        List<ApplicationInfo> listApp
                = pckManager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo app : listApp) {
            Log.i(TAG, app.packageName);
        }
    }
}
