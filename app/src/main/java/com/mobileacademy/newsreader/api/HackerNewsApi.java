package com.mobileacademy.newsreader.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by danielastamati on 11/04/2018.
 */

public class HackerNewsApi {

    private static final String TAG = "HackerNewsApi";
    private static final int MAX_ARTICLES_TO_LOAD = 15;

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


}
