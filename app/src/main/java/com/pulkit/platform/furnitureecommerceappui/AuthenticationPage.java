package com.pulkit.platform.furnitureecommerceappui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.SignInButton;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationPage extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;
    private TextView resetPasswordTextView; // Forget password link
    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;
    private FirebaseFirestore firestore;

    private static final int RC_SIGN_IN = 9001; // Request code for Google sign-in

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        // Remove the title from the toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance(); // Initialize Firestore

        // View Initialization
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        registerButton = findViewById(R.id.register_button);
        resetPasswordTextView = findViewById(R.id.reset_password);

        // Configure Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Get this ID from your Firebase console
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        // Google Sign-In Button
        SignInButton googleSignInButton = findViewById(R.id.google_sign_in_button);
        googleSignInButton.setOnClickListener(view -> signInWithGoogle());

        // Regular Login
        loginButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (!username.isEmpty() && !password.isEmpty()) {
                auth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = auth.getCurrentUser();

                                // Redirect to HomePage after successful login
                                Intent intent = new Intent(AuthenticationPage.this, HomePage.class);
                                startActivity(intent);
                                finish(); // Close AuthenticationPage if you don't want to return to it
                            } else {
                                Toast.makeText(AuthenticationPage.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(AuthenticationPage.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            }
        });

        // Register Button
        registerButton.setOnClickListener(view -> {
            // Navigate to Registration Activity
            Intent intent = new Intent(AuthenticationPage.this, RegisterPage.class);
            startActivity(intent);
        });

        // Reset Password (now redirects to ForgetPasswordPage)
        resetPasswordTextView.setOnClickListener(view -> {
            // Start Forget Password Activity
            Intent intent = new Intent(AuthenticationPage.this, ForgetPasswordPage.class);
            startActivity(intent);
        });
    }

    // Google Sign-In logic
    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            auth.signInWithCredential(credential)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();

                            // Save user profile to Firestore
                            saveUserProfileToFirestore(user);

                            // Redirect to HomePage after Google sign-in
                            Intent intent = new Intent(AuthenticationPage.this, HomePage.class);
                            startActivity(intent);
                            finish(); // Close AuthenticationPage if you don't want to return to it
                        } else {
                            Toast.makeText(AuthenticationPage.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (ApiException e) {
            Toast.makeText(this, "Google sign in failed: " + e.getStatusCode(), Toast.LENGTH_SHORT).show();
            Log.e("GoogleSignIn", "signInResult:failed code=" + e.getStatusCode(), e);
        }
    }

    private void saveUserProfileToFirestore(FirebaseUser user) {
        String email = user.getEmail();
        String name = user.getDisplayName();

        // Check if the email already exists in Firestore
        firestore.collection("userProfiles")
                .document(email) // Use email as document ID
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult().exists()) {
                        // User already exists
                        Log.d("Firestore", "User already exists: " + email);
                    } else {
                        // Save new user profile
                        Map<String, Object> userData = new HashMap<>();
                        userData.put("name", name);
                        userData.put("email", email);
                        userData.put("contact", ""); // Default blank
                        userData.put("description", ""); // Default blank
                        userData.put("profileImageUrl", ""); // Default blank

                        firestore.collection("userProfiles")
                                .document(email) // Use email as document ID
                                .set(userData)
                                .addOnSuccessListener(aVoid -> {
                                    Log.d("Firestore", "User profile created successfully.");
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("Firestore", "Failed to create user profile: ", e);
                                });
                    }
                });
    }
}
