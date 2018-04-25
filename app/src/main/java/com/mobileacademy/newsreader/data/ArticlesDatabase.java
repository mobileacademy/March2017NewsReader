package com.mobileacademy.newsreader.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.mobileacademy.newsreader.models.Article;

/**
 * Created by danielastamati on 25/04/2018.
 */

@Database(entities = {Article.class}, version = 1)
public abstract class ArticlesDatabase extends RoomDatabase {
    public abstract ArticlesDao articlesDao();

    private static ArticlesDatabase INSTANCE;

    public static ArticlesDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    ArticlesDatabase.class, "articles_database").build();
        }

        return INSTANCE;
    }
}
