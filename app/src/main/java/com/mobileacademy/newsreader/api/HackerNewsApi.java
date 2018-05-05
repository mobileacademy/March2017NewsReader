package com.mobileacademy.newsreader.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.mobileacademy.newsreader.models.Article;
import com.mobileacademy.newsreader.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by danielastamati on 11/04/2018.
 */

public class HackerNewsApi {

    private static final String TAG = "HackerNewsApi";
    private static final int MAX_ARTICLES_TO_LOAD = 15;
    private static final int TIMEOUT_S = 5;

    public static final String BASE_ENDPOINT = "https://hacker-news.firebaseio.com/v0/";
    public static final String TOP_STORIES_ENDPOINT = BASE_ENDPOINT + "topstories.json";
    public static final String NEW_STORIES_ENDPOINT = BASE_ENDPOINT + "newstories.json";
    public static final String ITEM_ENDPOINT = BASE_ENDPOINT + "item/";

    public static String buildArticleUrl(String articleId) {
        return ITEM_ENDPOINT + articleId + ".json";
    }


    private static RequestQueue requestQueue;

    public static RequestQueue getRequestQueue(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }

        return requestQueue;
    }


    public static void getTopStories(final Context context,
                                     final Response.Listener<JSONObject> listener,
                                     final Response.ErrorListener errorListener,
                                     final Object tag) {

        Response.Listener<JSONArray> successListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i < MAX_ARTICLES_TO_LOAD; i++) {
                        String itemUrl = buildArticleUrl(response.getString(i));
                        loadArticle(context, itemUrl, listener, errorListener, tag);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: ", e);
                }
            }
        };


        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                TOP_STORIES_ENDPOINT,
                null,
                successListener, errorListener);
        if (tag != null) request.setTag(tag);

        getRequestQueue(context).add(request);

    }


    public static List<Article> getTopStoriesSync(Context context){

        List<Article> articles = new ArrayList<>();
        RequestFuture<JSONArray> futureArray = RequestFuture.newFuture();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                TOP_STORIES_ENDPOINT,
                null, futureArray, futureArray);
        getRequestQueue(context).add(request);

        try{
            JSONArray idsJsonArray = futureArray.get(TIMEOUT_S, TimeUnit.SECONDS);

            for (int i = 0; i < MAX_ARTICLES_TO_LOAD; i++) {
                RequestFuture<JSONObject> futureArticle = RequestFuture.newFuture();
                String itemUrl = buildArticleUrl(idsJsonArray.getString(i));
                loadArticleSync(context, itemUrl, futureArticle);
                JSONObject articleJson = futureArticle.get(TIMEOUT_S, TimeUnit.SECONDS);
                articles.add(Util.getArticleFromJson(articleJson));
            }
        }catch (InterruptedException | ExecutionException | TimeoutException | JSONException e){
            Log.e(TAG, "getTopStoriesSync: ", e);
            return articles;
        }

        return articles;
    }


    public static void loadArticle(Context context, String url,
                                   Response.Listener<JSONObject> listener,
                                   Response.ErrorListener errorListener, Object tag) {

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                listener, errorListener);
        if (tag != null) request.setTag(tag);

        getRequestQueue(context).add(request);
    }

    public static void loadArticleSync(Context context, String url, RequestFuture<JSONObject> future) {

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                future, future);

        getRequestQueue(context).add(request);
    }


}
