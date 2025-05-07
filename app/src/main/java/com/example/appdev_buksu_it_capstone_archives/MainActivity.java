package com.example.appdev_buksu_it_capstone_archives;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CapstoneAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private CapstoneAdapter adapter;
    private EditText searchBar;
    private LinearLayout emptyState;
    private Toolbar toolbar;

    private List<CapstoneModel> capstoneList;
    private List<CapstoneModel> filteredList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupToolbar();
        setupRecyclerView();
        initializeFirestore();
        loadCapstoneData();
        setupSearch();
        showWelcomeMessage();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerViewCapstones);
        searchBar = findViewById(R.id.searchBar);
        emptyState = findViewById(R.id.emptyState);
        toolbar = findViewById(R.id.toolbar);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        capstoneList = new ArrayList<>();
        filteredList = new ArrayList<>();
        adapter = new CapstoneAdapter(this, capstoneList, this);
        recyclerView.setAdapter(adapter);
    }

    private void initializeFirestore() {
        db = FirebaseFirestore.getInstance();
    }

    private void loadCapstoneData() {
        db.collection("capstones")
            .get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                capstoneList.clear();
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    CapstoneModel capstone = document.toObject(CapstoneModel.class);
                    capstoneList.add(capstone);
                }
                filteredList.addAll(capstoneList);
                adapter.notifyDataSetChanged();
                updateEmptyState();
            })
            .addOnFailureListener(e -> {
                showError("Failed to load capstone data");
                // Load sample data as fallback
                loadSampleData();
            });
    }

    private void loadSampleData() {
        capstoneList.clear();
        // Add sample data
        capstoneList.add(new CapstoneModel(
            "1",
            "Smart Flood Detector",
            "Juan Dela Cruz, Maria Reyes",
            "IoT",
            "A real-time flood monitoring system using IoT sensors and mobile app integration.",
            "https://example.com/flood-detector.pdf",
            new Date(),
            "Dr. John Smith",
            "Completed",
            Arrays.asList("iot", "flood", "sensors", "monitoring")
        ));

        // Add more sample data...
        filteredList.addAll(capstoneList);
        adapter.notifyDataSetChanged();
        updateEmptyState();
    }

    private void setupSearch() {
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filter(String text) {
        filteredList.clear();
        
        if (text.isEmpty()) {
            filteredList.addAll(capstoneList);
        } else {
            String searchText = text.toLowerCase();
            for (CapstoneModel item : capstoneList) {
                if (matchesSearch(item, searchText)) {
                    filteredList.add(item);
                }
            }
        }

        adapter.updateList(filteredList);
        updateEmptyState();
    }

    private boolean matchesSearch(CapstoneModel item, String searchText) {
        return item.getTitle().toLowerCase().contains(searchText) ||
               item.getCategory().toLowerCase().contains(searchText) ||
               item.getAuthors().toLowerCase().contains(searchText) ||
               item.hasKeyword(searchText);
    }

    private void updateEmptyState() {
        if (filteredList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyState.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyState.setVisibility(View.GONE);
        }
    }

    private void showWelcomeMessage() {
        Snackbar.make(recyclerView, "Welcome to BukSu IT Capstone Archives", Snackbar.LENGTH_LONG)
                .setAction("Dismiss", v -> {})
                .show();
    }

    private void showError(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_LONG)
                .setAction("Retry", v -> loadCapstoneData())
                .show();
    }

    @Override
    public void onItemClick(CapstoneModel capstone) {
        Intent intent = new Intent(this, CapstoneDetailActivity.class);
        intent.putExtra("id", capstone.getId());
        intent.putExtra("title", capstone.getTitle());
        intent.putExtra("authors", capstone.getAuthors());
        intent.putExtra("category", capstone.getCategory());
        intent.putExtra("abstract", capstone.getAbstract());
        intent.putExtra("pdfUrl", capstone.getPdfUrl());
        intent.putExtra("submissionDate", capstone.getSubmissionDate().getTime());
        intent.putExtra("advisor", capstone.getAdvisor());
        intent.putExtra("status", capstone.getStatus());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clean up any resources
        if (adapter != null) {
            adapter.clearList();
        }
    }
}
