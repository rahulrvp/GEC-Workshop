package io.github.rahulrvp.hackernews.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.github.rahulrvp.hackernews.model.HackerNews;
import io.github.rahulrvp.hackernews.adapter.HackerNewsAdapter;
import io.github.rahulrvp.hackernews.R;

public class NewsListActivity extends AppCompatActivity {

    private HackerNewsAdapter mAdapter;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        setUpSearchView();

        mProgress = (ProgressBar) findViewById(R.id.hn_progress);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.hn_recycler_view);
        if (recyclerView != null) {
            mAdapter = new HackerNewsAdapter();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(mAdapter);
        }
    }

    private void setUpSearchView() {
        SearchView searchView = (SearchView) findViewById(R.id.hn_search_view);
        if (searchView != null) {
            searchView.setIconified(false);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    loadNews(query);

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
    }

    private void hideProgress() {
        if (mProgress != null) {
            mProgress.setVisibility(View.GONE);
        }
    }

    private void showProgress() {
        if (mProgress != null) {
            mProgress.setVisibility(View.VISIBLE);
        }
    }

    private void loadNews(String searchKey) {
        if (!TextUtils.isEmpty(searchKey)) {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://hn.algolia.com/api/v1/search?query=" + searchKey;

            StringRequest stringRequest = new StringRequest(
                    Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            hideProgress();

                            ArrayList<HackerNews> newses = parseResponse(response);
                            displayNewsList(newses);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hideProgress();

                            Toast.makeText(NewsListActivity.this, "No news found.", Toast.LENGTH_SHORT).show();
                        }
                    });

            queue.add(stringRequest);
            showProgress();
        } else {
            Toast.makeText(this, "Please provide a search category.", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<HackerNews> parseResponse(String response) {
        ArrayList<HackerNews> newsArrayList = null;

        try {
            if (!TextUtils.isEmpty(response)) {
                JSONObject root = null;
                root = new JSONObject(response);
                if (root.has("hits")) {
                    JSONArray hits = root.getJSONArray("hits");
                    if (hits.length() > 0) {
                        newsArrayList = new ArrayList<>();

                        for (int i = 0; i < hits.length(); i++) {
                            JSONObject news = hits.getJSONObject(i);
                            HackerNews hackerNews = new HackerNews(news);

                            newsArrayList.add(hackerNews);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newsArrayList;
    }

    private void displayNewsList(ArrayList<HackerNews> newses) {
        if (mAdapter != null) {
            mAdapter.setItems(newses);
        }
    }
}
