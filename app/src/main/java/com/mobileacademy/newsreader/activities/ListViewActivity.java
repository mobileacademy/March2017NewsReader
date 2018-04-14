package com.mobileacademy.newsreader.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mobileacademy.newsreader.R;
import com.mobileacademy.newsreader.adapters.ArticleBaseAdapter;
import com.mobileacademy.newsreader.models.Article;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {
    private static final String TAG = "ListViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ListView listView = findViewById(R.id.list);
        final List<Article> list = getArticles();
        final ArticleBaseAdapter articleAdapter = new ArticleBaseAdapter(this, list);
        listView.setAdapter(articleAdapter);
        list.add(new Article("Mobile Academy1"));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListViewActivity.this, list.get(position - 1).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        listView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: ");
                list.add(new Article("Mobile Academy"));
                articleAdapter.notifyDataSetChanged();
            }
        }, 500);

        View header = getLayoutInflater().inflate(R.layout.header, null);
        listView.addHeaderView(header);

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


    private List<String> getPublications() {
        List<String> list = new ArrayList<>();

        list.add("Hacker News");
        list.add("Android Authority");
        list.add("Medium");
        list.add("Stack Overflow");
        list.add("Vogella");

        return list;
    }
}
