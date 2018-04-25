package com.mobileacademy.newsreader.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mobileacademy.newsreader.models.Article;

import java.util.List;

/**
 * Created by danielastamati on 25/04/2018.
 */

@Dao
public interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Article> articles);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Article article);

    @Query("SELECT * from article_table")
    LiveData<List<Article>> getAll();
}
