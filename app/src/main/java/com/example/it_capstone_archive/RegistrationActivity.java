package com.example.it_capstone_archive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.it_capstone_archive.models.User;
import com.example.it_capstone_archive.utils.AuthUtils;

public class RegistrationActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput, fullNameInput, departmentInput, studentIdInput;
    private Button registerButton, googleSignInButton;
    private ProgressBar progressBar;
    private AuthUtils authUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize views
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        fullNameInput = findViewById(R.id.fullNameInput);
        departmentInput = findViewById(R.id.departmentInput);
        studentIdInput = findViewById(R.id.studentIdInput);
        registerButton = findViewById(R.id.registerButton);
        googleSignInButton = findViewById(R.id.googleSignInButton);
        progressBar = findViewById(R.id.progressBar);

        // Initialize AuthUtils
        authUtils = new AuthUtils(this);

        // Set up click listener
        registerButton.setOnClickListener(v -> handleRegistration());
        googleSignInButton.setOnClickListener(v -> handleGoogleSignIn());
    }

    private void handleRegistration() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String fullName = fullNameInput.getText().toString().trim();
        String department = departmentInput.getText().toString().trim();
        String studentId = studentIdInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || fullName.isEmpty() || department.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        showLoading(true);
        authUtils.createUserWithEmailAndPassword(email, password, new AuthUtils.OnAuthCompleteListener() {
            @Override
            public void onAuthSuccess(User user) {
                showLoading(false);
                navigateToMainActivity(user);
            }

            @Override
            public void onAuthError(String error) {
                showLoading(false);
                Toast.makeText(RegistrationActivity.this, error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNewUser() {
                // Not applicable here
            }
        });
    }

    private void handleGoogleSignIn() {
        showLoading(true);
        authUtils.signInWithGoogle();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9001) { // RC_SIGN_IN
            authUtils.handleGoogleSignInResult(data, new AuthUtils.OnAuthCompleteListener() {
                @Override
                public void onAuthSuccess(User user) {
                    showLoading(false);
                    navigateToMainActivity(user);
                }

                @Override
                public void onAuthError(String error) {
                    showLoading(false);
                    Toast.makeText(RegistrationActivity.this, error, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNewUser() {
                    showLoading(false);
                    // Navigate to registration for new users
                    startActivity(new Intent(RegistrationActivity.this, RegistrationActivity.class));
                }
            });
        }
    }

    private void navigateToMainActivity(User user) {
        Intent intent;
        if ("TEACHER".equals(user.getUserType())) {
            intent = new Intent(this, TeacherDashboardActivity.class);
        } else {
            intent = new Intent(this, StudentMainActivity.class);
        }
        startActivity(intent);
        finish();
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        registerButton.setEnabled(!isLoading);
        googleSignInButton.setEnabled(!isLoading);
    }
} 