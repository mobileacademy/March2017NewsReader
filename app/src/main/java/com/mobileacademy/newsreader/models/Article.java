package com.mobileacademy.newsreader.models;

/**
 * Created by Daniela on 3/24/18.
 */

public class Article {
    private String name;
    private Long date;
    private String iconUrl;
    private String url;

    public Article(String name, Long date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
