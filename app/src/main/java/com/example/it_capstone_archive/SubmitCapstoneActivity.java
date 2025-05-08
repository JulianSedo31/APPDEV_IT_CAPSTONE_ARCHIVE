package com.example.it_capstone_archive;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class SubmitCapstoneActivity extends AppCompatActivity {

    private EditText capstoneTitle, capstoneAbstract;
    private Button btnUploadFile, btnSubmitCapstone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_capstone);

        // Initialize views
        capstoneTitle = findViewById(R.id.capstoneTitle);
        capstoneAbstract = findViewById(R.id.capstoneAbstract);
        btnUploadFile = findViewById(R.id.btnUploadFile);
        btnSubmitCapstone = findViewById(R.id.btnSubmitCapstone);

        // Set click listeners
        btnUploadFile.setOnClickListener(v -> {
            // Handle file upload logic here
        });

        btnSubmitCapstone.setOnClickListener(v -> {
            // Handle submission logic here
        });
    }
}