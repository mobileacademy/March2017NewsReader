package com.mobileacademy.newsreader.utils;

import com.mobileacademy.newsreader.models.Article;

import org.json.JSONObject;

/**
 * Created by danielastamati on 12/04/2018.
 */

public class Util {

    public static Article getArticleFromJson(JSONObject json){
        Article article = new Article();

        String title = json.optString("title");
        String url = json.optString("url");
        int id = json.optInt("id");

        article.setId(id);
        article.setUrl(url);
        article.setTitle(title);

        return article;
    }
}
