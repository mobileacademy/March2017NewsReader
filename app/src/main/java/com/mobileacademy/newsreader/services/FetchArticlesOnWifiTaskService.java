package com.mobileacademy.newsreader.services;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.gcm.TaskParams;
import com.mobileacademy.newsreader.api.HackerNewsApi;
import com.mobileacademy.newsreader.data.ArticlesRepository;
import com.mobileacademy.newsreader.models.Article;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by danielastamati on 27/04/2018.
 */

public class FetchArticlesOnWifiTaskService extends GcmTaskService {

    private static final long EXECUTION_FREQUENCY_S = 2; //TODO: TimeUnit.HOURS.toSeconds(12);
    private static final String TAG = "FetchArticlesOnWifiTask";

    public static void schedule(Application context){

        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS && resultCode != ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED) {
            Log.e(TAG, "schedule: " + "GOOGLE PLAY SERVICES UNAVAILABLE");
            return;
        }

        PeriodicTask task = new PeriodicTask.Builder()
                .setService(FetchArticlesOnWifiTaskService.class)
                .setPeriod(EXECUTION_FREQUENCY_S)
                .setRequiredNetwork(Task.NETWORK_STATE_UNMETERED)
                .setRequiresCharging(true)
                .setTag(TAG)
                .setUpdateCurrent(true)
                .setPersisted(true)
                .build();
        GcmNetworkManager.getInstance(context).schedule(task);

        Log.d(TAG, FetchArticlesOnWifiTaskService.class.getSimpleName() + " scheduled");
    }

    @Override
    public int onRunTask(TaskParams taskParams) {

        Log.d(TAG, "onRunTask: ");

        final ArticlesRepository articlesRepository = ArticlesRepository.getInstance(getApplication());

        List<Article> articleList = HackerNewsApi.getTopStoriesSync(this.getApplication());

        if(articleList == null || articleList.size() == 0){
            return  GcmNetworkManager.RESULT_RESCHEDULE;
        }

        articlesRepository.updateData(articleList);
        Log.d(TAG, "onRunTask: " + Arrays.asList(articleList));
        return GcmNetworkManager.RESULT_SUCCESS;
    }
}
