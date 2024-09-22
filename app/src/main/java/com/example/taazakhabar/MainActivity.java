package com.example.taazakhabar;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecAdapter recAdapter;
    private List<Article> articleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter with an empty list
        recAdapter = new RecAdapter(this, articleList);
        recyclerView.setAdapter(recAdapter);

        fetchNews();

    }

    private void fetchNews() {
        MyNewsApi service = MyRetrofitClient.getInstance().create(MyNewsApi.class);
        Call<Mynewsmodel> call = service.getTopHeadlines("us", "ff1e7c989f2d43ae986844d54210275a");

        call.enqueue(new Callback<Mynewsmodel>() {
            @Override
            public void onResponse(Call<Mynewsmodel> call, Response<Mynewsmodel> response) {
                if (response.isSuccessful())
                    recAdapter.updateNews(response.body().getArticles());

            }

            @Override
            public void onFailure(Call<Mynewsmodel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to fetch news", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
