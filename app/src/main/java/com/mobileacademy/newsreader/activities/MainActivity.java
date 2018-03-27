package com.mobileacademy.newsreader.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mobileacademy.newsreader.R;
import com.mobileacademy.newsreader.adapters.ArticleAdapter;
import com.mobileacademy.newsreader.models.Article;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list);
        final List<Article> list = getArticles();
        ArticleAdapter articleAdapter = new ArticleAdapter(this, list);
        listView.setAdapter(articleAdapter);

        list.add(new Article("Mobile Academy", System.currentTimeMillis()));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, list.get(position - 1).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        View header = getLayoutInflater().inflate(R.layout.header, null);
        listView.addHeaderView(header);

    }


    private List<Article> getArticles() {

        List<Article> list = new ArrayList<>();
        list.add(new Article("Times New Roman", System.currentTimeMillis()));
        list.add(new Article("Times New Roman", System.currentTimeMillis()));
        list.add(new Article("Times New Roman", System.currentTimeMillis()));
        list.add(new Article("Times New Roman", System.currentTimeMillis()));
        list.add(new Article("Times New Roman", System.currentTimeMillis()));


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
