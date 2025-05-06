package com.example.appdev_buksu_it_capstone_archives;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CapstoneDetailActivity extends AppCompatActivity {
    TextView title, authors, category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capstone_detail);

        title = findViewById(R.id.detailTitle);
        authors = findViewById(R.id.detailAuthors);
        category = findViewById(R.id.detailCategory);

        title.setText(getIntent().getStringExtra("title"));
        authors.setText(getIntent().getStringExtra("authors"));
        category.setText(getIntent().getStringExtra("category"));
    }
}

