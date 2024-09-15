package com.example.taazakhabar;

import java.util.List;

public class Mynewsmodel {
    private String status;         // Status of the API response, e.g., "ok"
    private int totalResults;      // Total number of results
    private List<Article> articles;// List of articles returned by the API

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
