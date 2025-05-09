package com.example.it_capstone_archive;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.navigation.NavigationView;
import android.view.View;
import android.widget.TextView;

public class TeacherProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        // Get references to header views
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView headerName = headerView.findViewById(R.id.header_name);
        TextView headerEmail = headerView.findViewById(R.id.header_email);

        // Update header with actual user data
        headerName.setText("Dr. Jane Smith");
        headerEmail.setText("jane.smith@school.edu");
    }
}
