package com.example.taazakhabar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
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
        Spinner myspinner= findViewById(R.id.spinner_category);
        SearchView search_btn=findViewById(R.id.search_button);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.news_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(adapter);

        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = parent.getItemAtPosition(position).toString();
                if (category.equals("All")) {
                    fetchNews(null);  // Pass null to fetch all news
                } else {
                    fetchNews(category.toLowerCase());}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){
                    //NO ACTION HERE
                }

        });

        CharSequence query = search_btn.getQuery();
        query.toString();
        search_btn.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        fetchNews(null);

    }

    private void fetchNews(String category) {
        ProgressBar progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        MyNewsApi service = MyRetrofitClient.getInstance().create(MyNewsApi.class);
        Call<Mynewsmodel> call;
        if (category == null) {
            // Fetch all news without a category filter
            call = service.getTopHeadlines("us", null, "ff1e7c989f2d43ae986844d54210275a");
        } else {
            // Fetch filtered news by category
            call = service.getTopHeadlines("us", category, "ff1e7c989f2d43ae986844d54210275a");
        }

        call.enqueue(new Callback<Mynewsmodel>() {
            @Override
            public void onResponse(Call<Mynewsmodel> call, Response<Mynewsmodel> response) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {
                    List<Article> articles = response.body().getArticles();
                    recAdapter.updateNews(articles);
                    Log.d("mylogs", "response working: " + articles.size());
                } else {
                    Log.e("mylogs", "Response not successful");
                    try {
                        Log.e("mylogs", "Error Body: " + response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Mynewsmodel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to fetch news, maybe check your network", Toast.LENGTH_SHORT).show();
            }
        }); 
    }
}
