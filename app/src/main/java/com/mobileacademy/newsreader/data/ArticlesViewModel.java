package com.mobileacademy.newsreader.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.mobileacademy.newsreader.NewsReaderApp;
import com.mobileacademy.newsreader.models.Article;

import java.util.List;

/**
 * Created by danielastamati on 25/04/2018.
 */

public class ArticlesViewModel extends ViewModel{
    private ArticlesRepository articlesRepository;
    private LiveData<List<Article>> articlesList;

    public ArticlesViewModel (){
        articlesRepository = new ArticlesRepository(NewsReaderApp.getInstance());
        articlesList = articlesRepository.getArticlesList();
    }

    public LiveData<List<Article>> getArticlesList() {
        return articlesList;
    }

    public void insertAll(List<Article> articleList){
        articlesRepository.insert(articleList);
    }

    public void insert(Article article){
        articlesRepository.insert(article);
    }
}
