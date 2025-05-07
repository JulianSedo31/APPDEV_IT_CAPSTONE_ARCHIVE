package com.example.appdev_buksu_it_capstone_archives;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText usernameInput, passwordInput;
    private TextInputLayout usernameLayout, passwordLayout;
    private MaterialButton loginBtn, googleSignInButton;
    private TextView registerLink, forgotPassword;
    private CircularProgressIndicator progressBar;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1000;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        initializeViews();
        setupGoogleSignIn();
        setupClickListeners();
        checkExistingSession();
    }

    private void initializeViews() {
        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        usernameLayout = findViewById(R.id.usernameLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        loginBtn = findViewById(R.id.loginBtn);
        registerLink = findViewById(R.id.registerLink);
        forgotPassword = findViewById(R.id.forgotPassword);
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
        // Email/Password Login
        loginBtn.setOnClickListener(v -> {
            if (validateInputs()) {
                performLogin();
            }
        });

        // Register redirect
        registerLink.setOnClickListener(v -> {
            navigateToRegister();
        });

        // Forgot Password
        forgotPassword.setOnClickListener(v -> {
            handleForgotPassword();
        });

        // Google Sign-In
        googleSignInButton.setOnClickListener(v -> {
            initiateGoogleSignIn();
        });
    }

    private void checkExistingSession() {
        if (mAuth.getCurrentUser() != null) {
            navigateToMain();
        }
    }

    private boolean validateInputs() {
        boolean isValid = true;
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // Reset errors
        usernameLayout.setError(null);
        passwordLayout.setError(null);

        // Validate username
        if (TextUtils.isEmpty(username)) {
            usernameLayout.setError("Username is required");
            isValid = false;
        }

        // Validate password
        if (TextUtils.isEmpty(password)) {
            passwordLayout.setError("Password is required");
            isValid = false;
        } else if (password.length() < 6) {
            passwordLayout.setError("Password must be at least 6 characters");
            isValid = false;
        }

        return isValid;
    }

    private void performLogin() {
        showLoading(true);
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(this, task -> {
                showLoading(false);
                if (task.isSuccessful()) {
                    navigateToMain();
                } else {
                    showError("Authentication failed: " + task.getException().getMessage());
                }
            });
    }

    private void initiateGoogleSignIn() {
        showLoading(true);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
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
                        navigateToMain();
                    } else {
                        showError("Authentication failed");
                    }
                });
    }

    private void handleForgotPassword() {
        String email = usernameInput.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            showError("Please enter your email address");
            return;
        }

        showLoading(true);
        mAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener(task -> {
                showLoading(false);
                if (task.isSuccessful()) {
                    showSuccess("Password reset email sent");
                } else {
                    showError("Failed to send reset email");
                }
            });
    }

    private void navigateToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        loginBtn.setEnabled(!isLoading);
        googleSignInButton.setEnabled(!isLoading);
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showSuccess(String message) {
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

    @Override
    protected void onStart() {
        super.onStart();
        checkExistingSession();
    }
}
