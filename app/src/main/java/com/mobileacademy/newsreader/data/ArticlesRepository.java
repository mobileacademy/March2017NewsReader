package com.mobileacademy.newsreader.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.mobileacademy.newsreader.models.Article;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by danielastamati on 25/04/2018.
 */

public class ArticlesRepository {

    private ArticlesDao articlesDao;
    private LiveData<List<Article>> articlesList;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public ArticlesRepository(Application context) {
        ArticlesDatabase db = ArticlesDatabase.getDatabase(context);
        articlesDao = db.articlesDao();
        articlesList = articlesDao.getAll();
    }

    public LiveData<List<Article>> getArticlesList() {
        return articlesList;
    }

    public void insert(final Article article) {

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                articlesDao.insert(article);
            }
        });
    }

    public void insert(final List<Article> articles) {

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                articlesDao.insertAll(articles);
            }
        });
    }
}
