package com.pulkit.platform.furnitureecommerceappui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegisterPage extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText nameEditText;
    private Button registerButton;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        nameEditText = findViewById(R.id.name);
        registerButton = findViewById(R.id.register);

        registerButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String name = nameEditText.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty() && !name.isEmpty()) {
                checkEmailExists(email, name, password);
            } else {
                Toast.makeText(RegisterPage.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkEmailExists(String email, String name, String password) {
        firestore.collection("userProfiles")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            // Email already exists
                            Toast.makeText(RegisterPage.this, "Account with this email already exists!", Toast.LENGTH_SHORT).show();
                        } else {
                            // Email does not exist, proceed to register
                            registerUser(email, password, name);
                        }
                    } else {
                        Toast.makeText(RegisterPage.this, "Error checking email: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void registerUser(String email, String password, String name) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            saveUserDataToFirestore(user.getUid(), name, email);
                        }
                        Toast.makeText(RegisterPage.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                        finish(); // Close the registration activity
                    } else {
                        // Handle specific errors
                        if (task.getException() != null) {
                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                            if (errorCode.equals("EMAIL_EXISTS")) {
                                Toast.makeText(RegisterPage.this, "Account with this email already exists!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegisterPage.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterPage.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveUserDataToFirestore(String userId, String name, String email) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", name);
        userData.put("email", email);
        userData.put("contact", ""); // Blank field for contact
        userData.put("description", ""); // Blank field for description
        userData.put("profileImageUrl", ""); // Blank field for profile image

        firestore.collection("userProfiles").document(userId) // Using userId as document ID
                .set(userData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "User Registered Successfully.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to save user profile.", Toast.LENGTH_SHORT).show();
                });
    }
}
