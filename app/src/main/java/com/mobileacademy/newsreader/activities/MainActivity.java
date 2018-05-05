package com.mobileacademy.newsreader.activities;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mobileacademy.newsreader.R;
import com.mobileacademy.newsreader.adapters.ArticlesRecyclerAdapter;
import com.mobileacademy.newsreader.api.HackerNewsApi;
import com.mobileacademy.newsreader.api.OkHttpSample;
import com.mobileacademy.newsreader.data.ArticlesViewModel;
import com.mobileacademy.newsreader.models.Article;
import com.mobileacademy.newsreader.utils.Util;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String INTENT_ACTION_TIMES_UP = "Time's up";

    private DrawerLayout drawerLayout;
    private List<Article> list;
    private ArticlesRecyclerAdapter adapter;
    private ArticlesViewModel articlesViewModel;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(INTENT_ACTION_TIMES_UP)) {
                Toast.makeText(MainActivity.this, "Time's up!", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.rv_articles);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList();
        adapter = new ArticlesRecyclerAdapter(this, list, recyclerView);
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        articlesViewModel = ViewModelProviders.of(this).get(ArticlesViewModel.class);
        articlesViewModel.getArticlesList().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articleList) {
                list.clear();
                list.addAll(articleList);
                adapter.notifyDataSetChanged();
            }
        });

        setupDrawer();
        getStories();
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(INTENT_ACTION_TIMES_UP);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);

        super.onStart();
    }

    private void setupDrawer() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //TODO: Implement click listeners
                return false;
            }
        });


        drawerLayout = findViewById(R.id.drawer);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

    }

    @Override
    public boolean onSupportNavigateUp() {
        drawerLayout.openDrawer(GravityCompat.START);
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_item_refresh: {
                articlesViewModel.deleteAll();
                getStories();
                return true;
            }

            case R.id.menu_item_settings: {
                item.setChecked(!item.isChecked());
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            }

            case R.id.menu_item_whats_new: {
                item.setChecked(!item.isChecked());
                Toast.makeText(this, "What's new", Toast.LENGTH_SHORT).show();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }


    private void getStories() {

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse: " + response);

                Article article = Util.getArticleFromJson(response);
                articlesViewModel.insert(article);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ", error);
            }
        };

        HackerNewsApi.getTopStories(this, listener, errorListener, this);
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        //cancel all requests issued by the current activity
        HackerNewsApi.getRequestQueue(this).cancelAll(this);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();
    }
}

//Async task example
class FetchListAsyncTask extends AsyncTask<String, Void, okhttp3.Response> {

    private static final String TAG = "FetchListAsyncTask";
    WeakReference<Activity> activityRef;

    public FetchListAsyncTask(Activity activity) {
        activityRef = new WeakReference(activity);
    }

    @Override
    protected void onPreExecute() {

        Activity activity = activityRef.get();
        if(activity!=null) {
            ((TextView) activity.findViewById(R.id.tv_status)).setText("Pending...");
            super.onPreExecute();
        }
    }

    @Override
    protected okhttp3.Response doInBackground(String... strings) {

        return OkHttpSample.getStoryIdsSyncOkhttp(strings[0]);
    }

    @Override
    protected void onPostExecute(okhttp3.Response response) {
        Activity activity = activityRef.get();
        if(activity!=null) {
            ((TextView) activity.findViewById(R.id.tv_status)).setText("Finished");
            Log.d(TAG, "onPostExecute: " + response);
            super.onPostExecute(response);
        }
    }
}
