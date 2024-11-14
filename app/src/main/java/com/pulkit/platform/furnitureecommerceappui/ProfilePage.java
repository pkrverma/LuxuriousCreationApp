package com.pulkit.platform.furnitureecommerceappui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfilePage extends AppCompatActivity {

    private static final String TAG = "ProfilePage";
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private TextView nameTextView;
    private TextView descriptionTextView;
    private TextView contactTextView;
    private TextView emailTextView; // New TextView for email
    private ImageView profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        // Initialize Firebase instances
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Find views
        nameTextView = findViewById(R.id.profile_name);
        descriptionTextView = findViewById(R.id.profile_description);
        contactTextView = findViewById(R.id.profile_contact);
        emailTextView = findViewById(R.id.profile_email); // Initialize email TextView
        profileImageView = findViewById(R.id.profile_image);

        // Load user data from Firestore
        loadUserProfile();

        // Display logged-in user's email
        displayUserEmail();

        // Apply only slide-in transition for ProfilePage (no exit transition)
        overridePendingTransition(R.anim.slide_in, 0);

        // Setup navigation buttons
        setupNavigationButtons();

        // Setup Edit Profile button
        Button editProfileButton = findViewById(R.id.editProfileButton); // Assume you have a button in your layout
        editProfileButton.setOnClickListener(v -> openEditProfileDialog());
    }

    private void setupNavigationButtons() {
        LinearLayout homeLinearLayout = findViewById(R.id.home_button);
        homeLinearLayout.setOnClickListener(v -> navigateToHomePage());

        FloatingActionButton favoriteButton = findViewById(R.id.favorite_button);
        favoriteButton.setOnClickListener(v -> navigateToFavoritePage());
    }

    private void loadUserProfile() {
        // Get the current user's UID
        String uid = auth.getCurrentUser().getUid();
        if (uid != null) {
            // Fetch user profile data from Firestore
            db.collection("userProfiles").whereEqualTo("email", auth.getCurrentUser().getEmail()).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (!task.getResult().isEmpty()) {
                                    DocumentSnapshot document = task.getResult().getDocuments().get(0);
                                    // User's profile found, retrieve and display data
                                    String name = document.getString("name");
                                    String description = document.getString("description");
                                    String contact = document.getString("contact");
                                    String profileImageUrl = document.getString("profileImageUrl");

                                    nameTextView.setText(isNullOrBlank(name) ? "Unknown User" : name);
                                    descriptionTextView.setText(isNullOrBlank(description) ? "Add description here" : description);
                                    contactTextView.setText(isNullOrBlank(contact) ? "+91xxxxxxxxxx" : contact);

                                    // Load profile image with Glide
                                    if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
                                        Glide.with(ProfilePage.this)
                                                .load(profileImageUrl)
                                                .into(profileImageView);
                                    } else {
                                        profileImageView.setImageResource(R.drawable.default_profile); // Placeholder image
                                    }
                                } else {
                                    // User's document doesn't exist, display default values
                                    setDefaultProfileValues();
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                                Toast.makeText(ProfilePage.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNullOrBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    private void displayUserEmail() {
        // Display the email from the current user
        String email = auth.getCurrentUser().getEmail();
        emailTextView.setText(email != null ? email : "Email not available");
    }

    private void navigateToHomePage() {
        Intent intent = new Intent(ProfilePage.this, HomePage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in, 0); // No exit transition for ProfilePage
    }

    private void navigateToFavoritePage() {
        Intent intent = new Intent(ProfilePage.this, FavoritePage.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, 0); // No exit transition for ProfilePage
    }

    private void openEditProfileDialog() {
        // Inflate the dialog layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_edit_profile, null);

        // Find views in the dialog layout
        EditText editName = dialogView.findViewById(R.id.edit_name);
        EditText editDescription = dialogView.findViewById(R.id.edit_description);
        EditText editContact = dialogView.findViewById(R.id.edit_contact);

        // Pre-fill existing data or set to blank if the TextView is empty
        String name = nameTextView.getText().toString().trim();
        String description = descriptionTextView.getText().toString().trim();
        String contact = contactTextView.getText().toString().trim();

        editName.setText(isNullOrBlank(name) ? "" : name);
        editDescription.setText(isNullOrBlank(description) ? "" : description);
        editContact.setText(isNullOrBlank(contact) ? "" : contact);

        // Create the dialog
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Edit Profile")
                .setView(dialogView)
                .setPositiveButton("Save", (dialogInterface, i) -> {
                    // Save changes to Firestore
                    saveProfileChanges(editName.getText().toString(), editDescription.getText().toString(), editContact.getText().toString());
                })
                .setNegativeButton("Cancel", null)
                .create();

        dialog.show();
    }

    private void saveProfileChanges(String name, String description, String contact) {
        String uid = auth.getCurrentUser().getUid();
        if (uid != null) {
            // Update user profile in Firestore
            db.collection("userProfiles").whereEqualTo("email", auth.getCurrentUser().getEmail()).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                            String documentId = document.getId(); // Get the document ID for updating
                            db.collection("userProfiles").document(documentId)
                                    .update("name", name, "description", description, "contact", contact)
                                    .addOnCompleteListener(updateTask -> {
                                        if (updateTask.isSuccessful()) {
                                            // Update UI with new values
                                            nameTextView.setText(name);
                                            descriptionTextView.setText(description);
                                            contactTextView.setText(contact);
                                            Toast.makeText(ProfilePage.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            // Log the exception for more insight
                                            Log.e(TAG, "Error updating profile: ", updateTask.getException());
                                            Toast.makeText(ProfilePage.this, "Failed to update profile: " + updateTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(ProfilePage.this, "User profile not found", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private void setDefaultProfileValues() {
        nameTextView.setText("Unknown User");
        descriptionTextView.setText("Add description here");
        contactTextView.setText("+91xxxxxxxxxx");
        profileImageView.setImageResource(R.drawable.default_profile); // Placeholder image
    }

    @Override
    public void onBackPressed() {
        navigateToHomePage();
        overridePendingTransition(R.anim.fade_in, 0); // No exit transition for ProfilePage
    }
}
