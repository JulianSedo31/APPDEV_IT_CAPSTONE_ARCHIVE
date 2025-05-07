package com.example.appdev_buksu_it_capstone_archives;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText usernameInput, passwordInput, confirmPasswordInput;
    private TextInputLayout usernameLayout, passwordLayout, confirmPasswordLayout;
    private MaterialButton registerBtn, googleSignInButton;
    private TextView loginLink;
    private ImageButton backButton;
    private CircularProgressIndicator progressBar;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        initializeViews();
        setupGoogleSignIn();
        setupClickListeners();
    }

    private void initializeViews() {
        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        confirmPasswordInput = findViewById(R.id.confirmPassword);
        usernameLayout = findViewById(R.id.usernameLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        confirmPasswordLayout = findViewById(R.id.confirmPasswordLayout);
        registerBtn = findViewById(R.id.registerBtn);
        loginLink = findViewById(R.id.loginLink);
        backButton = findViewById(R.id.backButton);
        googleSignInButton = findViewById(R.id.googleSignInButton);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void setupClickListeners() {
        // Back button
        backButton.setOnClickListener(v -> onBackPressed());

        // Register button
        registerBtn.setOnClickListener(v -> {
            if (validateInputs()) {
                performRegistration();
            }
        });

        // Login link
        loginLink.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        // Google Sign-In
        googleSignInButton.setOnClickListener(v -> {
            showLoading(true);
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }

    private boolean validateInputs() {
        boolean isValid = true;

        // Reset errors
        usernameLayout.setError(null);
        passwordLayout.setError(null);
        confirmPasswordLayout.setError(null);

        // Check username
        String username = usernameInput.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            usernameLayout.setError("Username is required");
            isValid = false;
        } else if (username.length() < 4) {
            usernameLayout.setError("Username must be at least 4 characters");
            isValid = false;
        }

        // Check password
        String password = passwordInput.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            passwordLayout.setError("Password is required");
            isValid = false;
        } else if (password.length() < 6) {
            passwordLayout.setError("Password must be at least 6 characters");
            isValid = false;
        }

        // Check confirm password
        String confirmPassword = confirmPasswordInput.getText().toString().trim();
        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordLayout.setError("Please confirm your password");
            isValid = false;
        } else if (!confirmPassword.equals(password)) {
            confirmPasswordLayout.setError("Passwords do not match");
            isValid = false;
        }

        return isValid;
    }

    private void performRegistration() {
        showLoading(true);
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // Simulate network delay
        registerBtn.postDelayed(() -> {
            // TODO: Implement actual registration logic
            showSuccess("Registration successful!");
            showLoading(false);
            
            // Navigate back to login after delay
            registerBtn.postDelayed(() -> {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }, 1500);
        }, 1000);
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        registerBtn.setEnabled(!isLoading);
        googleSignInButton.setEnabled(!isLoading);
    }

    private void showSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(task);
        }
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account.getIdToken());
        } catch (ApiException e) {
            showLoading(false);
            showError("Google Sign-In failed: " + e.getStatusCode());
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    showLoading(false);
                    if (task.isSuccessful()) {
                        // Sign in success
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    } else {
                        // Sign in failed
                        showError("Authentication failed");
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
