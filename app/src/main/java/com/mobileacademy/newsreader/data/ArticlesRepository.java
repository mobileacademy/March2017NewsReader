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
    private static ArticlesRepository INSTANCE;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private ArticlesRepository(Context context) {
        ArticlesDatabase db = ArticlesDatabase.getDatabase(context.getApplicationContext());
        articlesDao = db.articlesDao();
        articlesList = articlesDao.getAll();
    }

    public static ArticlesRepository getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE =  new ArticlesRepository(context);
        }

        return INSTANCE;
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

    public void updateData(final List<Article> articles) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                articlesDao.updateData(articles);
            }
        });
    }

    public void deleteAll(){

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                articlesDao.deleteAll();
            }
        });
    }
}
