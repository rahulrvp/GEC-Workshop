package io.github.rahulrvp.hackernews.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Rahul Raveendran V P
 *         Created on 26/09/17 @ 3:29 PM
 *         https://github.com/rahulrvp
 */

public class HackerNews {

    private String title;
    private String url;
    private String author;
    private Long createdAt;

    public HackerNews(JSONObject jsonObject) {
        if (jsonObject != null) {
            this.title = getString(jsonObject, "title");
            this.url = getString(jsonObject, "url");
            this.author = getString(jsonObject, "author");
            this.createdAt = getLong(jsonObject, "created_at_i");
        }
    }

    private String getString(JSONObject jsonObject, String key) {
        String value = "";

        if (jsonObject != null && key != null && jsonObject.has(key)) {
            try {
                value = jsonObject.getString(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return value;
    }

    private Long getLong(JSONObject jsonObject, String key) {
        Long value = 0L;

        if (jsonObject != null && key != null && jsonObject.has(key)) {
            try {
                value = jsonObject.getLong(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return value;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthor() {
        return author;
    }

    public Long getCreatedAt() {
        return createdAt;
    }
}
