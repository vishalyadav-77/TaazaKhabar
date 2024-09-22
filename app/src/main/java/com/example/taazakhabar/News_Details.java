package com.example.taazakhabar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class News_Details extends AppCompatActivity {
    TextView titleview, contentview, readbutton;
    ImageView thumnailview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        titleview = findViewById(R.id.title2);
        contentview = findViewById(R.id.content);
        thumnailview = findViewById(R.id.imageView);
        readbutton = findViewById(R.id.readmore);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String thumbnail = intent.getStringExtra("thumbnail");
        String content = intent.getStringExtra("content");
        String url= intent.getStringExtra("url");

        if (content.contains("[+")) {
            // Handle truncated content
            readbutton.setVisibility(View.VISIBLE);
            readbutton.setOnClickListener(view -> {
                // Open full article in a WebView or browser
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            });
        } else {
            readbutton.setVisibility(View.GONE);
        }

        titleview.setText(title);
        contentview.setText(content);
        Glide.with(this).load(thumbnail).into(thumnailview);
    }
}