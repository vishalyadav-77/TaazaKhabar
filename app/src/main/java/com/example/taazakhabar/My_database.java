package com.example.taazakhabar;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MyNewsArticle.class}, version = 1, exportSchema = false)
public abstract class My_database extends RoomDatabase {
    public abstract NewsArticleDAO newsArticleDao();

    private static volatile My_database INSTANCE;

    public static My_database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (My_database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    My_database.class, "news_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
