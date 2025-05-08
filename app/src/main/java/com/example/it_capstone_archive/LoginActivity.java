package com.example.it_capstone_archive;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.it_capstone_archive.models.User;
import com.example.it_capstone_archive.utils.AuthUtils;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput;
    private MaterialButton loginButton, googleSignInButton;
    private ProgressBar progressBar;
    private AuthUtils authUtils;
    private TextView signupText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        googleSignInButton = findViewById(R.id.googleSignInButton);
        progressBar = findViewById(R.id.progressBar);
        signupText = findViewById(R.id.signupText);

        // Initialize AuthUtils
        authUtils = new AuthUtils(this);

        // Setup login and Google sign-in buttons
        loginButton.setOnClickListener(v -> handleEmailLogin());
        googleSignInButton.setOnClickListener(v -> handleGoogleSignIn());

        // Setup SignUp Text as clickable
        setSignUpTextClickable();
    }

    private void setSignUpTextClickable() {
        String text = "Not yet registered? SignUp Now";
        SpannableString spannable = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE); // Make "SignUp Now" blue
                ds.setUnderlineText(false); // Optional
            }
        };

        spannable.setSpan(clickableSpan, 21, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signupText.setText(spannable);
        signupText.setMovementMethod(LinkMovementMethod.getInstance());
        signupText.setHighlightColor(Color.TRANSPARENT);
    }

    private void handleEmailLogin() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        showLoading(true);
        authUtils.signInWithEmailAndPassword(email, password, new AuthUtils.OnAuthCompleteListener() {
            @Override
            public void onAuthSuccess(User user) {
                showLoading(false);
                navigateToMainActivity(user);
            }

            @Override
            public void onAuthError(String error) {
                showLoading(false);
                Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNewUser() {
                showLoading(false);
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
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
        if (requestCode == 9001) {
            authUtils.handleGoogleSignInResult(data, new AuthUtils.OnAuthCompleteListener() {
                @Override
                public void onAuthSuccess(User user) {
                    showLoading(false);
                    navigateToMainActivity(user);
                }

                @Override
                public void onAuthError(String error) {
                    showLoading(false);
                    Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNewUser() {
                    showLoading(false);
                    startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                }
            });
        }
    }

    private void navigateToMainActivity(User user) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("USER_TYPE", user.getUserType());
        startActivity(intent);
        finish();
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        loginButton.setEnabled(!isLoading);
        googleSignInButton.setEnabled(!isLoading);
    }
}
