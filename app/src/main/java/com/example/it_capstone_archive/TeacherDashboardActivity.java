package com.example.it_capstone_archive;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class TeacherDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView welcomeText;
    private Button btnReviewCapstone, btnStudentRecords, btnSubmitCapstone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        // Initialize views
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        welcomeText = findViewById(R.id.welcome_text);
        btnReviewCapstone = findViewById(R.id.btn_review_capstone);
        btnStudentRecords = findViewById(R.id.btn_student_records);
        btnSubmitCapstone = findViewById(R.id.btn_submit_capstone);

        // Set up navigation drawer
        navigationView.setNavigationItemSelectedListener(this);

        // Set welcome message (replace with actual user data)
        String userName = "Teacher"; // Replace with actual user name
        welcomeText.setText("Welcome, " + userName + "!");

        // Set click listeners for buttons
        btnReviewCapstone.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherDashboardActivity.this, ReviewCapstoneActivity.class);
            startActivity(intent);
        });

        btnStudentRecords.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherDashboardActivity.this, AdminStudentRecordsActivity.class);
            startActivity(intent);
        });

        btnSubmitCapstone.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherDashboardActivity.this, SubmitCapstoneActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, TeacherProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            // Handle logout logic here
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
