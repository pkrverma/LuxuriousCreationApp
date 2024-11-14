package com.pulkit.platform.furnitureecommerceappui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ModelDetailPage extends AppCompatActivity {

    private ImageView modelImage;
    private TextView nameText;
    private TextView priceText;
    private TextView shopText;
    private TextView ratingText;
    private TextView descriptionText;
    private Button buyNowButton;
    private Button viewArButton;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modeldetail);

        // Initialize views
        modelImage = findViewById(R.id.model_image);
        nameText = findViewById(R.id.name_text);
        priceText = findViewById(R.id.price_text);
        shopText = findViewById(R.id.shop_text);
        ratingText = findViewById(R.id.rating_text);
        descriptionText = findViewById(R.id.description_text);
        buyNowButton = findViewById(R.id.buyNow_button);
        viewArButton = findViewById(R.id.viewAr_button);
        backButton = findViewById(R.id.back_button);

        // Get data from the intent
        Intent intent = getIntent();
        if (intent != null) {
            int imageResId = intent.getIntExtra("imageResId", R.drawable.errormsg);
            String modelName = intent.getStringExtra("modelName");
            String price = intent.getStringExtra("price");
            String shopName = intent.getStringExtra("shopName");
            String rating = intent.getStringExtra("rating");
            String description = intent.getStringExtra("description");
            String arLink = intent.getStringExtra("arLink"); // Retrieve the AR link as a string

            // Set the data to views
            modelImage.setImageResource(imageResId);
            nameText.setText(modelName);
            priceText.setText(price);
            shopText.setText(shopName);
            ratingText.setText("Rating: " + rating);
            descriptionText.setText(description);

            // Set button listeners
            buyNowButton.setOnClickListener(v -> {
                // Handle Buy Now button click
            });

            // Set the onClick function for the AR button
            viewArButton.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(arLink));
                startActivity(browserIntent);
            });
        }

        // Add onClick functionality for the Profile button
        LinearLayout profileButton = findViewById(R.id.profile_button);
        profileButton.setOnClickListener(v -> {
            Intent Navintent = new Intent(ModelDetailPage.this, ProfilePage.class);
            startActivity(Navintent);
            overridePendingTransition(R.anim.slide_in, 0);  // No exit transition for BedsCategoryPage
        });

        // Add onClick functionality for the Favorite button
        FloatingActionButton favoriteButton = findViewById(R.id.favorite_button);
        favoriteButton.setOnClickListener(v -> {
            Intent Navintent = new Intent(ModelDetailPage.this, FavoritePage.class);
            startActivity(Navintent);
            overridePendingTransition(R.anim.slide_in, 0);  // No exit transition for BedsCategoryPage
        });

        LinearLayout homeLinearLayout = findViewById(R.id.home_button);
        homeLinearLayout.setOnClickListener(v -> {
            Intent Navintent = new Intent(ModelDetailPage.this, HomePage.class);
            Navintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Navintent);
            finish(); // Clear FavoritePage from the back stack
            // Apply only slide-in transition for HomePage
            overridePendingTransition(R.anim.slide_in, 0); // No exit transition for FavoritePage
        });

        // Back button listener
        backButton.setOnClickListener(v -> finish()); // Go back to the previous activity
    }
}
