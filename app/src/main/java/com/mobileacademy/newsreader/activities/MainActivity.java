package com.mobileacademy.newsreader.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mobileacademy.newsreader.R;
import com.mobileacademy.newsreader.adapters.ArticlesRecyclerAdapter;
import com.mobileacademy.newsreader.models.Article;
import com.mobileacademy.newsreader.services.CounterService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.rv_articles);
//        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List list = getArticles();
        ArticlesRecyclerAdapter adapter = new ArticlesRecyclerAdapter(this, list, recyclerView);
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        setupDrawer();
    }


    private void setupDrawer(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
//        onBackPressed()

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
                Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
                Intent service = new Intent(this, CounterService.class);
                startService(service);
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

    private List<Article> getArticles() {

        List<Article> list = new ArrayList<>();
        list.add(new Article("Hacker News"));
        list.add(new Article("Android Authority"));
        list.add(new Article("Medium"));
        list.add(new Article("Stack Overflow"));
        list.add(new Article("Vogella"));


        return list;
    }

}
