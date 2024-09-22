package com.example.taazakhabar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.NewsViewHolder> {
    private List<Article> articles;
    private Context context;
    public RecAdapter(Context context, List<Article> articles) {
        this.articles = articles;
        this.context=context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_items, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.titleTextView.setText(article.getTitle());
        holder.descriptionTextView.setText(article.getDescription());
        // Load image with Glide or Picasso
        Glide.with(context).load(article.getUrlToImage()).into(holder.imageThumbnail);

        //NEWS ITEM CLICK EVENT
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, News_Details.class);
            intent.putExtra("title", article.getTitle());
            intent.putExtra("thumbnail", article.getUrlToImage());
            intent.putExtra("content", article.getContent());
            intent.putExtra("url", article.getUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {

        return articles.size();
    }

    public void updateNews(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        ImageView imageThumbnail;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            imageThumbnail = itemView.findViewById(R.id.thumbnailImageView);
        }
    }
}
