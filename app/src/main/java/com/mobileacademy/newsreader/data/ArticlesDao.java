package com.mobileacademy.newsreader.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.mobileacademy.newsreader.models.Article;

import java.util.List;

/**
 * Created by danielastamati on 25/04/2018.
 */

@Dao
public abstract class ArticlesDao {

    @Transaction
    void updateData(List<Article> articles){
        deleteAll();
        insertAll(articles);
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertAll(List<Article> articles);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insert(Article article);

    @Query("SELECT * from article_table")
    abstract LiveData<List<Article>> getAll();

    @Delete
    abstract void deleteArticle(Article article);

    @Query("DELETE FROM article_table")
    abstract void deleteAll();
}
