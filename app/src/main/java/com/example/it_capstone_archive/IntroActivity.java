package com.example.it_capstone_archive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // Initialize UI components
        Button signUpButton = findViewById(R.id.signUpButton);
        Button logInButton = findViewById(R.id.logInButton);

        // Set up click listeners
        signUpButton.setOnClickListener(v -> navigateToRegistration());
        logInButton.setOnClickListener(v -> navigateToLogin());
    }

    private void navigateToRegistration() {
        Intent intent = new Intent(IntroActivity.this,
                com.example.it_capstone_archive.RegistrationActivity.class);
        startActivity(intent);
    }

    private void navigateToLogin() {
        Intent intent = new Intent(IntroActivity.this,
                com.example.it_capstone_archive.LoginActivity.class);
        startActivity(intent);
    }
} 