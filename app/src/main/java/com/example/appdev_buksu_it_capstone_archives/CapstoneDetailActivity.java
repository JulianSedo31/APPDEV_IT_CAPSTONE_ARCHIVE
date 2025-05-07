package com.example.appdev_buksu_it_capstone_archives;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.button.MaterialButton;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CapstoneDetailActivity extends AppCompatActivity {

    private TextView title, authors, category, abstractText, advisor, status, submissionDate;
    private MaterialButton downloadButton;
    private String pdfUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capstone_detail);

        initializeViews();
        setupToolbar();
        loadDataFromIntent();
        setupDownloadButton();
    }

    // Initialize all views from the layout
    private void initializeViews() {
        title = findViewById(R.id.detailTitle);
        authors = findViewById(R.id.detailAuthors);
        category = findViewById(R.id.detailCategory);
        abstractText = findViewById(R.id.detailAbstract);
        downloadButton = findViewById(R.id.downloadButton);
    }

    // Set up the toolbar and back button
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackPressed());
    }

    // Load data from intent extras and handle nulls gracefully
    private void loadDataFromIntent() {
        Intent intent = getIntent();
        if (intent == null) return;

        setTextOrFallback(title, intent.getStringExtra("title"), "No Title Provided");
        setTextOrFallback(authors, intent.getStringExtra("authors"), "No Authors Provided");
        setTextOrFallback(category, intent.getStringExtra("category"), "No Category Provided");
        setTextOrFallback(abstractText, intent.getStringExtra("abstract"), "No Abstract Provided");
        setTextOrFallback(advisor, intent.getStringExtra("advisor"), "No Advisor Provided");
        setTextOrFallback(status, intent.getStringExtra("status"), "No Status Provided");

        long dateMillis = intent.getLongExtra("submissionDate", 0);
        if (dateMillis > 0) {
            Date date = new Date(dateMillis);
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
            submissionDate.setText(sdf.format(date));
        } else {
            submissionDate.setText("Date not available");
        }

        pdfUrl = intent.getStringExtra("pdfUrl");
        downloadButton.setVisibility(pdfUrl != null && !pdfUrl.isEmpty() ? View.VISIBLE : View.GONE);
    }

    // Helper to set text or fallback if null or empty
    private void setTextOrFallback(TextView view, String value, String fallback) {
        if (value == null || value.trim().isEmpty()) {
            view.setText(fallback);
        } else {
            view.setText(value);
        }
    }

    // Set up the download button to open the PDF URL
    private void setupDownloadButton() {
        downloadButton.setOnClickListener(v -> {
            if (pdfUrl != null && !pdfUrl.isEmpty()) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl));
                    startActivity(browserIntent);
                } catch (Exception e) {
                    Toast.makeText(this, "Error opening PDF link", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "PDF not available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

