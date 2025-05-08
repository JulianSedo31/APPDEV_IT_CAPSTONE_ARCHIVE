package com.example.it_capstone_archive;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class AdminStudentRecordsActivity extends AppCompatActivity {

    private RecyclerView studentRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_student_records);

        // Initialize RecyclerView
        studentRecycler = findViewById(R.id.studentRecycler);
        // Set up RecyclerView adapter and data
    }
}