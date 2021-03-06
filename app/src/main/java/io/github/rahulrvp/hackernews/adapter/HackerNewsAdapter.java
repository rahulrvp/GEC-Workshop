package io.github.rahulrvp.hackernews.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import io.github.rahulrvp.hackernews.R;
import io.github.rahulrvp.hackernews.activity.WebViewActivity;
import io.github.rahulrvp.hackernews.model.HackerNews;

/**
 * @author Rahul Raveendran V P
 *         Created on 26/09/17 @ 3:42 PM
 *         https://github.com/rahulrvp
 */

public class HackerNewsAdapter extends RecyclerView.Adapter<HackerNewsAdapter.ViewHolder> {

    private final ArrayList<HackerNews> newsArrayList = new ArrayList<>();

    public void setItems(ArrayList<HackerNews> newses) {
        newsArrayList.clear();

        if (newses != null) {
            newsArrayList.addAll(newses);
        }

        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card, parent, false);

        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HackerNews hackerNews = newsArrayList.get(position);
        if (hackerNews != null && holder != null) {
            holder.setTitle(hackerNews.getTitle());
            holder.setAuthor(hackerNews.getAuthor());
            holder.setCreatedAt(hackerNews.getCreatedAt());
            holder.setOnClick(hackerNews.getUrl());
        }
    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private View contentView;
        private TextView titleText;
        private TextView authorText;
        private TextView createdAtText;

        ViewHolder(View itemView) {
            super(itemView);

            contentView = itemView;
            titleText = itemView.findViewById(R.id.title_text);
            authorText = itemView.findViewById(R.id.author_text);
            createdAtText = itemView.findViewById(R.id.time_text);
        }

        void setTitle(String text) {
            if (titleText != null && text != null) {
                titleText.setText(text);
            }
        }

        void setAuthor(String text) {
            if (authorText != null && text != null) {
                Context context = authorText.getContext();
                String result = context.getString(R.string.author, text);
                authorText.setText(result);
            }
        }

        void setCreatedAt(Long createdAt) {
            if (createdAt > 0 && createdAtText != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.getDefault());
                createdAtText.setText(sdf.format(createdAt * 1000));
            }
        }

        void setOnClick(final String url) {
            if (contentView != null && url != null) {
                contentView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        launchWebView(view.getContext(), url);
                    }
                });
            }
        }

        void launchWebView(Context context, String url) {
            if (context != null) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(WebViewActivity.URL, url);

                context.startActivity(intent);
            }
        }
    }
}
