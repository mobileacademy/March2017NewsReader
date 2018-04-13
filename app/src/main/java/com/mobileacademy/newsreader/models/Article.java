package com.mobileacademy.newsreader.models;

/**
 * Created by Daniela on 3/24/18.
 */

public class Article {
    private String title;
    private String url;
    private Integer id;


    public Article(){

    }

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
}
