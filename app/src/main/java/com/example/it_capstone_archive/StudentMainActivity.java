package com.example.it_capstone_archive;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StudentMainActivity extends AppCompatActivity {

    private RecyclerView capstoneRecyclerView;
    private CapstoneAdapter capstoneAdapter;
    private List<Capstone> capstoneList;
    private EditText searchInput;
    private Spinner filterSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        // Initialize UI components
        Button submitProjectButton = findViewById(R.id.submitProjectButton);
        Button viewStatusButton = findViewById(R.id.viewStatusButton);
        searchInput = findViewById(R.id.searchInput);
        filterSpinner = findViewById(R.id.filterSpinner);
        capstoneRecyclerView = findViewById(R.id.capstoneRecyclerView);

        // Initialize capstone list and adapter
        capstoneList = new ArrayList<>();
        capstoneAdapter = new CapstoneAdapter(capstoneList);
        capstoneRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        capstoneRecyclerView.setAdapter(capstoneAdapter);

        // Load capstone projects
        loadCapstoneProjects();

        // Set up search and filter
        setupSearchAndFilter();

        // Populate filter spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filter_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapter);

        // Handle filter selection
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedFilter = parent.getItemAtPosition(position).toString();
                applyFilter(selectedFilter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Set up click listeners
        submitProjectButton.setOnClickListener(v -> handleSubmitProject());
        viewStatusButton.setOnClickListener(v -> handleViewStatus());
    }

    private void loadCapstoneProjects() {
        // Load capstone projects from database or mock data
        // For now, let's add some mock data
        capstoneList.add(new Capstone("AI in Healthcare", "John Doe", "2023-01-15", "AI"));
        capstoneList.add(new Capstone("Machine Learning Basics", "Jane Smith", "2023-02-20", "Machine Learning"));
        capstoneAdapter.notifyDataSetChanged();
    }

    private void setupSearchAndFilter() {
        // Implement search and filter logic
        // This is a placeholder for actual implementation
    }

    private void applyFilter(String filter) {
        // Implement filter logic based on the selected filter
        // This is a placeholder for actual implementation
    }

    private void handleSubmitProject() {
        // Logic to submit a project
        Toast.makeText(this, "Submit Project clicked", Toast.LENGTH_SHORT).show();
    }

    private void handleViewStatus() {
        // Logic to view project status
        Toast.makeText(this, "View Status clicked", Toast.LENGTH_SHORT).show();
    }

    // Capstone class for demonstration
    private static class Capstone {
        String title, author, date, topic;

        Capstone(String title, String author, String date, String topic) {
            this.title = title;
            this.author = author;
            this.date = date;
            this.topic = topic;
        }
    }

    // CapstoneAdapter class for RecyclerView
    private class CapstoneAdapter extends RecyclerView.Adapter<CapstoneAdapter.CapstoneViewHolder> {
        private List<Capstone> capstoneList;

        CapstoneAdapter(List<Capstone> capstoneList) {
            this.capstoneList = capstoneList;
        }

        @Override
        public CapstoneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // Implementation of onCreateViewHolder method
        }

        @Override
        public void onBindViewHolder(CapstoneViewHolder holder, int position) {
            // Implementation of onBindViewHolder method
        }

        @Override
        public int getItemCount() {
            return capstoneList.size();
        }

        class CapstoneViewHolder extends RecyclerView.ViewHolder {
            // Implementation of CapstoneViewHolder class
        }
    }
} 