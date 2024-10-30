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

        // Retrieve the passed FurnitureModel object
        HomePage.FurnitureModel model = (HomePage.FurnitureModel) getIntent().getSerializableExtra("furnitureModel");

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

        if (model != null) {
            // Set data from the FurnitureModel object
            ImageView imageView = findViewById(R.id.model_image);
            imageView.setImageResource(model.getImageResource());

            TextView nameTextView = findViewById(R.id.name_text);
            nameTextView.setText(model.getName());

            TextView priceTextView = findViewById(R.id.price_text);
            priceTextView.setText("₹" + model.getPrice());

            TextView descriptionTextView = findViewById(R.id.description_text);
            descriptionTextView.setText(model.getDescription());

            // Handle the "Buy Now" button click
            Button buyNowButton = findViewById(R.id.buyNow_button);
            buyNowButton.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getShopUrl()));
                startActivity(browserIntent);
            });

            // Handle the "View in AR" button click
            Button viewArButton = findViewById(R.id.viewAr_button);
            viewArButton.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(model.getArUrl())));
                startActivity(browserIntent);
            });
        }

        // Initialize the favorite button (heart icon)
        ImageView heartIcon = findViewById(R.id.heart_icon);

        // Set click listener for the heart button
        heartIcon.setOnClickListener(v -> {
            if (isFavorite) {
                heartIcon.setImageResource(R.drawable.favorite_outline);
                Toast.makeText(ModelDetailPage.this, "Removed from favorites", Toast.LENGTH_SHORT).show();
            } else {
                heartIcon.setImageResource(R.drawable.favorite_filled);
                Toast.makeText(ModelDetailPage.this, "Added to favorites", Toast.LENGTH_SHORT).show();
            }
            isFavorite = !isFavorite;
        });

        // Add onClick functionality for the Profile button
        addProfileButtonFunctionality();

        // Add onClick functionality for the Favorite button
        addFavoriteButtonFunctionality();

        // Add onClick functionality for the Home button
        addHomeButtonFunctionality();
    }

    private void addProfileButtonFunctionality() {
        LinearLayout profileButton = findViewById(R.id.profile_button);
        profileButton.setOnClickListener(v -> {
            Intent profileIntent = new Intent(ModelDetailPage.this, ProfilePage.class);
            startActivity(profileIntent);
            overridePendingTransition(R.anim.slide_in, 0);
        });
    }

    private void addFavoriteButtonFunctionality() {
        FloatingActionButton favoriteButton = findViewById(R.id.favorite_button);
        favoriteButton.setOnClickListener(v -> {
            Intent favoriteIntent = new Intent(ModelDetailPage.this, FavoritePage.class);
            startActivity(favoriteIntent);
            overridePendingTransition(R.anim.slide_in, 0);
        });
    }

    private void addHomeButtonFunctionality() {
        LinearLayout profileButton = findViewById(R.id.home_button);
        profileButton.setOnClickListener(v -> {
            Intent homeIntent = new Intent(ModelDetailPage.this, HomePage.class);
            startActivity(homeIntent);
            overridePendingTransition(R.anim.slide_in, 0);
        });
    }

    @Override
    public void onBackPressed() {
        handleBackNavigation();
    }

    private void handleBackNavigation() {
        if (isNavigatedFromAnotherPage) {
            finish();
        } else {
            Intent intent = new Intent(ModelDetailPage.this, HomePage.class);
            startActivity(intent);
            finish();
        }
    }
}
