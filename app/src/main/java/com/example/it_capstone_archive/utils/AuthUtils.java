package com.example.it_capstone_archive.utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.it_capstone_archive.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthUtils {
    private static final int RC_SIGN_IN = 9001;
    private final FirebaseAuth mAuth;
    private final FirebaseFirestore db;
    private final Activity activity;
    private final GoogleSignInClient mGoogleSignInClient;

    public AuthUtils(Activity activity) {
        this.activity = activity;
        this.mAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("650745418524-xxxxxxxxxxxxxxxx.apps.googleusercontent.com") // Replace with your web client ID
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    public void signInWithEmailAndPassword(String email, String password, OnAuthCompleteListener listener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            getUserData(user.getUid(), listener);
                        }
                    } else {
                        listener.onAuthError(task.getException().getMessage());
                    }
                });
    }

    public void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void handleGoogleSignInResult(Intent data, OnAuthCompleteListener listener) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            firebaseAuthWithGoogle(account.getIdToken(), listener);
        } catch (ApiException e) {
            listener.onAuthError("Google sign in failed: " + e.getMessage());
        }
    }

    private void firebaseAuthWithGoogle(String idToken, OnAuthCompleteListener listener) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Check if user exists in Firestore
                            getUserData(user.getUid(), listener);
                        }
                    } else {
                        listener.onAuthError("Authentication failed: " + task.getException().getMessage());
                    }
                });
    }

    private void getUserData(String userId, OnAuthCompleteListener listener) {
        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        listener.onAuthSuccess(user);
                    } else {
                        // New user, needs registration
                        listener.onNewUser();
                    }
                })
                .addOnFailureListener(e -> listener.onAuthError("Failed to get user data: " + e.getMessage()));
    }

    public void registerNewUser(User user, OnAuthCompleteListener listener) {
        db.collection("users").document(user.getUserId())
                .set(user)
                .addOnSuccessListener(aVoid -> listener.onAuthSuccess(user))
                .addOnFailureListener(e -> listener.onAuthError("Failed to register user: " + e.getMessage()));
    }

    public void signOut() {
        mAuth.signOut();
        mGoogleSignInClient.signOut();
    }

    public interface OnAuthCompleteListener {
        void onAuthSuccess(User user);
        void onAuthError(String error);
        void onNewUser();
    }
} 