package com.example.taazakhabar;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NewsArticleDAO {
    @Insert
    void insertArticle(List<MyNewsArticle> articles);

    @Query("SELECT * FROM news_articles")
    List<MyNewsArticle> getAllArticles();

    @Query("DELETE FROM news_articles")
    void clearAllArticles();
}
