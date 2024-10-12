package com.pulkit.platform.furnitureecommerceappui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ModelDetailPage extends AppCompatActivity {

    private boolean isNavigatedFromAnotherPage = false;  // Track if user came from another page
    private boolean isFavorite = false;  // Track if the model is favorite

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modeldetail);

        // Initialize the back button ImageView
        ImageView backButton = findViewById(R.id.back_button);

        // Check if the page was accessed from a previous activity
        Intent intent = getIntent();
        if (intent.getExtras() != null && intent.getExtras().containsKey("fromOtherPage")) {
            isNavigatedFromAnotherPage = true;
        }

        // Set click listener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBackNavigation();
            }
        });

        // Retrieve the data passed from HomePage
        int modelNameResId = intent.getIntExtra("model_name", R.string.default_name);
        double modelPrice = intent.getDoubleExtra("model_price", 0);
        int modelImageResId = intent.getIntExtra("model_image", R.drawable.errormsg);  // Provide a default image
        int modelDescriptionResId = intent.getIntExtra("model_description", R.string.default_description);
        String shopUrl = intent.getStringExtra("shop_url");
        int arUrlResId = intent.getIntExtra("ar_url", R.string.default_ar_url);

        // Fetch the actual string values from resource IDs
        String modelName = getString(modelNameResId);
        String modelDescription = getString(modelDescriptionResId);
        String arUrl = getString(arUrlResId);

        // Set the data into views
        ImageView imageView = findViewById(R.id.model_image);
        imageView.setImageResource(modelImageResId);

        TextView nameTextView = findViewById(R.id.name_text);
        nameTextView.setText(modelName);

        TextView priceTextView = findViewById(R.id.price_text);
        priceTextView.setText("₹" + modelPrice);

        TextView descriptionTextView = findViewById(R.id.description_text);
        descriptionTextView.setText(modelDescription);

        // Handle the "Buy Now" button click
        Button buyNowButton = findViewById(R.id.buyNow_button);
        buyNowButton.setOnClickListener(v -> {
            // Open the shop URL in a web browser
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(shopUrl));
            startActivity(browserIntent);
        });

        // Handle the "View in AR" button click
        Button viewArButton = findViewById(R.id.viewAr_button);
        viewArButton.setOnClickListener(v -> {
            // Open the AR view URL in a web browser
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(arUrl));
            startActivity(browserIntent);
        });

        // Initialize the favorite button (heart icon)
        ImageView heartIcon = findViewById(R.id.heart_icon);

        // Set click listener for the heart button
        heartIcon.setOnClickListener(v -> {
            if (isFavorite) {
                // If it is currently favorite, set it to unfavorite
                heartIcon.setImageResource(R.drawable.favorite_outline);  // Change the icon to unfilled heart
                Toast.makeText(ModelDetailPage.this, "Removed from favorites", Toast.LENGTH_SHORT).show();
            } else {
                // If it is currently unfavorite, set it to favorite
                heartIcon.setImageResource(R.drawable.favorite_filled);  // Change the icon to filled heart
                Toast.makeText(ModelDetailPage.this, "Added to favorites", Toast.LENGTH_SHORT).show();
            }
            // Toggle the favorite state
            isFavorite = !isFavorite;
        });

        // Add onClick functionality for the Profile button
        addProfileButtonFunctionality();

        // Add onClick functionality for the Favorite button
        addFavoriteButtonFunctionality();

        // Add onClick functionality for the Home button
        addHomeButtonFunctionality();
    }

    // Method to handle Profile button click
    private void addProfileButtonFunctionality() {
        LinearLayout profileButton = findViewById(R.id.profile_button);
        profileButton.setOnClickListener(v -> {
            Intent profileIntent = new Intent(ModelDetailPage.this, ProfilePage.class);
            startActivity(profileIntent);
            overridePendingTransition(R.anim.slide_in, 0);  // No exit transition for ModelDetailPage
        });
    }

    // Method to handle Favorite button click
    private void addFavoriteButtonFunctionality() {
        FloatingActionButton favoriteButton = findViewById(R.id.favorite_button);
        favoriteButton.setOnClickListener(v -> {
            Intent favoriteIntent = new Intent(ModelDetailPage.this, FavouritePage.class);
            startActivity(favoriteIntent);
            overridePendingTransition(R.anim.slide_in, 0);  // No exit transition for ModelDetailPage
        });
    }

    private void addHomeButtonFunctionality() {
        LinearLayout profileButton = findViewById(R.id.home_button);
        profileButton.setOnClickListener(v -> {
            Intent homeIntent = new Intent(ModelDetailPage.this, HomePage.class);
            startActivity(homeIntent);
            overridePendingTransition(R.anim.slide_in, 0);  // No exit transition for ModelDetailPage
        });
    }

    // Override onBackPressed to handle the back press event
    @Override
    public void onBackPressed() {
        handleBackNavigation();
    }

    // Handle back navigation logic
    private void handleBackNavigation() {
        if (isNavigatedFromAnotherPage) {
            // If navigated from another page, go back one step
            finish();
        } else {
            // Otherwise, go back to Homepage
            Intent intent = new Intent(ModelDetailPage.this, HomePage.class);
            startActivity(intent);
            finish();
        }
    }
}
