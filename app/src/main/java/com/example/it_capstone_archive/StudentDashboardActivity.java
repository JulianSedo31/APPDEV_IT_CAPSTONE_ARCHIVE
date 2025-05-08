package com.example.it_capstone_archive;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class StudentDashboardActivity extends AppCompatActivity {

    private TextView studentName, studentId;
    private Button btnCapstoneArchives, btnSubmitThesis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        // Initialize views
        studentName = findViewById(R.id.studentName);
        studentId = findViewById(R.id.studentId);
        btnCapstoneArchives = findViewById(R.id.btnCapstoneArchives);
        btnSubmitThesis = findViewById(R.id.btnSubmitThesis);

        // Set sample data (replace with actual user data)
        studentName.setText("John Doe");
        studentId.setText("ID: 2023-001");

        // Set click listeners
        btnCapstoneArchives.setOnClickListener(v -> {
            Intent intent = new Intent(StudentDashboardActivity.this, StudentMainActivity.class);
            startActivity(intent);
        });

        btnSubmitThesis.setOnClickListener(v -> {
            Intent intent = new Intent(StudentDashboardActivity.this, SubmitCapstoneActivity.class);
            startActivity(intent);
        });

        // Example usage of MongoDB
        MongoCollection<Document> studentsCollection = MongoDBAtlasHelper.getCollection("students");

        // Insert a document
        Document studentDoc = new Document("name", "John Doe")
                .append("email", "john.doe@student.buksu.edu.ph")
                .append("role", "student");
        studentsCollection.insertOne(studentDoc);

        // Query documents
        for (Document doc : studentsCollection.find()) {
            Log.d("MongoDB", "Student: " + doc.toJson());
        }
    }
}