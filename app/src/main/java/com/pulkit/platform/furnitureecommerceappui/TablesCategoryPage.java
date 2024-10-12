package com.pulkit.platform.furnitureecommerceappui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TablesCategoryPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        // Apply entry transition only when entering BedsCategoryPage
        overridePendingTransition(R.anim.slide_in, 0);  // No exit transition for BedsCategoryPage

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (TablesModel model : tableModels) {
            addCardView(model, gridLayout);
        }

        // Add onClick functionality for the Profile button
        LinearLayout profileButton = findViewById(R.id.profile_button);
        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(TablesCategoryPage.this, ProfilePage.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, 0);  // No exit transition for BedsCategoryPage
        });

        // Add onClick functionality for the Favorite button
        FloatingActionButton favoriteButton = findViewById(R.id.favorite_button);
        favoriteButton.setOnClickListener(v -> {
            Intent intent = new Intent(TablesCategoryPage.this, FavouritePage.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, 0);  // No exit transition for BedsCategoryPage
        });

        LinearLayout homeLinearLayout = findViewById(R.id.home_button);
        homeLinearLayout.setOnClickListener(v -> {
            Intent intent = new Intent(TablesCategoryPage.this, HomePage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Clear FavouritePage from the back stack
            // Apply only slide-in transition for HomePage
            overridePendingTransition(R.anim.slide_in, 0); // No exit transition for FavouritePage
        });

        // Initialize the back button ImageView
        ImageView backButton = findViewById(R.id.back_item_button);

        // Set click listener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current activity and go back to the previous one
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TablesCategoryPage.this, HomePage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Finish BedsCategoryPage to remove it from the back stack
    }

    private TablesModel[] tableModels = {
            new TablesModel(R.string.model8_name, 8420.00, R.drawable.model8, R.string.model8_desc, "", R.string.model8_arLink),
            new TablesModel(R.string.model6_name, 15450.00, R.drawable.model6, R.string.model6_desc, "", R.string.model6_arLink),
            new TablesModel(R.string.model2_name, 13540.00, R.drawable.model2, R.string.model2_desc, "", R.string.model2_arLink),
            new TablesModel(R.string.model3_name, 13540.00, R.drawable.model3, R.string.model3_desc, "", R.string.model3_arLink),
            new TablesModel(R.string.model4_name, 11200.00, R.drawable.model4, R.string.model4_desc, "", R.string.model4_arLink),
            new TablesModel(R.string.model5_name, 9569.00, R.drawable.model5, R.string.model5_desc, "", R.string.model5_arLink),
            new TablesModel(R.string.model9_name, 14490.00, R.drawable.model9, R.string.model9_desc, "", R.string.model9_arLink)
    };

    private void addCardView(TablesModel model, GridLayout gridLayout) {
        // Inflate the card_item_beds layout
        View cardView = getLayoutInflater().inflate(R.layout.card_item_section, null);

        // Set furniture image
        ImageView imageView = cardView.findViewById(R.id.card_item_image);
        imageView.setImageResource(model.getImageResource());

        // Set heart icon click functionality
        ImageView heartIcon = cardView.findViewById(R.id.heart_item_icon);

        // Add a boolean variable to track the favorite state
        final boolean[] isFavorite = {false}; // Initially set to not favorite

        heartIcon.setOnClickListener(v -> {
            if (isFavorite[0]) {
                // If it is currently favorite, set it to unfavorite
                heartIcon.setImageResource(R.drawable.favorite_outline); // Change the icon to unfavorite (unfilled heart)
                Toast.makeText(TablesCategoryPage.this, "Removed from favorites", Toast.LENGTH_SHORT).show();
            } else {
                // If it is currently unfavorite, set it to favorite
                heartIcon.setImageResource(R.drawable.favorite_filled); // Change the icon to favorite (filled heart)
                Toast.makeText(TablesCategoryPage.this, "Added to favorites", Toast.LENGTH_SHORT).show();
            }

            // Toggle the favorite state
            isFavorite[0] = !isFavorite[0];
        });

        // Set furniture title
        TextView titleTextView = cardView.findViewById(R.id.card_item_title);
        titleTextView.setText(getString(model.getName()));

        // Set furniture description
        TextView descriptionTextView = cardView.findViewById(R.id.card_item_description);
        descriptionTextView.setText(getString(model.getDescription()));

        TextView priceTextView = cardView.findViewById(R.id.card_item_price);
        priceTextView.setText("₹" + model.getPrice());

        // Set AR view button click
        Button viewInARButton = cardView.findViewById(R.id.view_in_ar_item_button);
        viewInARButton.setOnClickListener(v -> {
            // Fetch AR link from string resources
            String arUrl = getString(model.getArUrl());  // Assuming arUrl is an integer string resource ID
            if (arUrl != null && !arUrl.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(arUrl));
                startActivity(intent);
            } else {
                Toast.makeText(TablesCategoryPage.this, "AR link not available", Toast.LENGTH_SHORT).show();
            }
        });

        // Set Add to Cart button click
        Button addToCartButton = cardView.findViewById(R.id.add_to_cart_item_button);
        addToCartButton.setOnClickListener(v -> {
            Toast.makeText(TablesCategoryPage.this, "Added to cart", Toast.LENGTH_SHORT).show();
        });

        // Define layout parameters for the card view
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;
        params.height = GridLayout.LayoutParams.WRAP_CONTENT;
        params.setMargins(16, 16, 16, 16);
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);  // Set equal width for columns
        cardView.setLayoutParams(params);

        // Add the card to the GridLayout
        gridLayout.addView(cardView);
    }

    public static class TablesModel {
        private int name;
        private double price;
        private int imageResource;
        private int description;
        private String shopUrl;
        private int arUrl;

        public TablesModel(int name, double price, int imageResource, int description, String shopUrl, int arUrl) {
            this.name = name;
            this.price = price;
            this.imageResource = imageResource;
            this.description = description;
            this.shopUrl = shopUrl;
            this.arUrl = arUrl;
        }

        public int getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public int getImageResource() {
            return imageResource;
        }

        public int getDescription() {
            return description;
        }

        public String getShopUrl() {
            return shopUrl;
        }

        public int getArUrl() {
            return arUrl;
        }
    }
}
