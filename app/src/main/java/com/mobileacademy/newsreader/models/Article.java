package com.mobileacademy.newsreader.models;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Daniela on 3/24/18.
 */

@Entity(tableName = "article_table")
public class Article {

    @PrimaryKey
    private Integer id;

    private String title;
    private String url;


    public Article() {}

    public Article(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    //...


    @Override
    public String toString() {
//           %[argument_index$][flags][width][.precision]conversion
        return String.format("[%1s: %2s]", id, title.substring(0, 7));
    }
}
