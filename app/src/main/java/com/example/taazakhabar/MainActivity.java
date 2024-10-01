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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecAdapter recAdapter;
    private SwipeRefreshLayout swipe;
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
        swipe=findViewById(R.id.swipeRefreshLayout);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.news_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(adapter);

        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = parent.getItemAtPosition(position).toString();
                if (category.equals("All")) {
                    fetchNews(null,null);  // Pass null to fetch all news
                } else {
                    fetchNews(category.toLowerCase(),null);}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){
                    //NO ACTION HERE
                }

        });

        CharSequence query = search_btn.getQuery();
        search_btn.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("mylog", "Query submitted: " + query);
                fetchNews(null, query);
                search_btn.clearFocus();
                return true;

            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchNews(null,null);
            }
        });

        fetchNews(null, null);

    }

    private void fetchNews(String category, String query) {
        ProgressBar progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        MyNewsApi service = MyRetrofitClient.getInstance().create(MyNewsApi.class);
        Call<Mynewsmodel> call;
        if (category == null && query==null) {
            // Fetch all news without a category filter or query
            call = service.getTopHeadlines("us", null, "ff1e7c989f2d43ae986844d54210275a");
        }
        else if (category== null || query!=null) {
            //get everything to search
            call = service.getEverything(query, "ff1e7c989f2d43ae986844d54210275a");
        }
        else {
            // Fetch filtered news by category
            call = service.getTopHeadlines("us", category, "ff1e7c989f2d43ae986844d54210275a");
        }

        call.enqueue(new Callback<Mynewsmodel>() {
            @Override
            public void onResponse(Call<Mynewsmodel> call, Response<Mynewsmodel> response) {
                progressBar.setVisibility(View.GONE);
                swipe.setRefreshing(false);
                recyclerView.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {
                    List<Article> articles = response.body().getArticles();
                    recAdapter.updateNews(articles);
                    Log.d("mylogs", "response working: " + articles.size());
                } else {
                    Log.e("mylogs", "Response not successful");
                }
            }

            @Override
            public void onFailure(Call<Mynewsmodel> call, Throwable t) {
                swipe.setRefreshing(false);
                Toast.makeText(MainActivity.this, "Failed to fetch news, maybe check your network", Toast.LENGTH_SHORT).show();
            }
        }); 
    }
}
