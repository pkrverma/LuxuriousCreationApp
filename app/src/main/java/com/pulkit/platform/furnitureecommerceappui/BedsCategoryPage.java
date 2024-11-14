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

import java.io.Serializable;

public class BedsCategoryPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beds);

        // Apply entry transition only when entering BedsCategoryPage
        overridePendingTransition(R.anim.slide_in, 0);  // No exit transition for BedsCategoryPage

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (BedsModel model : bedModels) {
            addCardView(model, gridLayout);
        }

        // Add onClick functionality for the Profile button
        LinearLayout profileButton = findViewById(R.id.profile_button);
        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(BedsCategoryPage.this, ProfilePage.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, 0);  // No exit transition for BedsCategoryPage
        });

        // Add onClick functionality for the Favorite button
        FloatingActionButton favoriteButton = findViewById(R.id.favorite_button);
        favoriteButton.setOnClickListener(v -> {
            Intent intent = new Intent(BedsCategoryPage.this, FavoritePage.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, 0);  // No exit transition for BedsCategoryPage
        });

        LinearLayout homeLinearLayout = findViewById(R.id.home_button);
        homeLinearLayout.setOnClickListener(v -> {
            Intent intent = new Intent(BedsCategoryPage.this, HomePage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Clear FavoritePage from the back stack
            // Apply only slide-in transition for HomePage
            overridePendingTransition(R.anim.slide_in, 0); // No exit transition for FavoritePage
        });

        // Initialize the back button ImageView
        ImageView backButton = findViewById(R.id.back_item_button);

        // Set click listener for the back button
        backButton.setOnClickListener(v -> finish());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BedsCategoryPage.this, HomePage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Finish BedsCategoryPage to remove it from the back stack
    }

    private BedsModel[] bedModels = {
            new BedsModel(R.string.itemId9, R.string.model9_name, 14490.00, R.drawable.model9, R.string.model9_desc, "", R.string.model9_arLink),
            new BedsModel(R.string.itemId2, R.string.model2_name, 13540.00, R.drawable.model2, R.string.model2_desc, "", R.string.model2_arLink),
            new BedsModel(R.string.itemId3, R.string.model3_name, 13540.00, R.drawable.model3, R.string.model3_desc, "", R.string.model3_arLink),
            new BedsModel(R.string.itemId4, R.string.model4_name, 11200.00, R.drawable.model4, R.string.model4_desc, "", R.string.model4_arLink),
            new BedsModel(R.string.itemId5, R.string.model5_name, 9569.00, R.drawable.model5, R.string.model5_desc, "", R.string.model5_arLink),
            new BedsModel(R.string.itemId6, R.string.model6_name, 15450.00, R.drawable.model6, R.string.model6_desc, "", R.string.model6_arLink)
    };

    private void addCardView(BedsModel model, GridLayout gridLayout) {
        // Inflate the card_item_beds layout
        View cardView = getLayoutInflater().inflate(R.layout.card_item_section, gridLayout, false);

        // Set furniture image
        ImageView imageView = cardView.findViewById(R.id.card_item_image);
        imageView.setImageResource(model.getImageResource());

        // Set heart icon click functionality
        ImageView heartIcon = cardView.findViewById(R.id.heart_item_icon);
        heartIcon.setImageResource(model.isFavorite() ? R.drawable.favorite_filled : R.drawable.favorite_outline);

        heartIcon.setOnClickListener(v -> {
            // Toggle the favorite state
            model.setFavorite(!model.isFavorite());
            heartIcon.setImageResource(model.isFavorite() ? R.drawable.favorite_filled : R.drawable.favorite_outline);

            // Update the FavoritesManager
            if (model.isFavorite()) {
                FavoritesManager.getInstance().addFavorite(model);
            } else {
                FavoritesManager.getInstance().removeFavorite(model);
            }

            Toast.makeText(BedsCategoryPage.this, model.isFavorite() ? "Added to favorites" : "Removed from favorites", Toast.LENGTH_SHORT).show();
        });

        // Set furniture title
        TextView titleTextView = cardView.findViewById(R.id.card_item_title);
        titleTextView.setText(getString(model.getName()));

        // Set furniture description
        TextView descriptionTextView = cardView.findViewById(R.id.card_item_description);
        descriptionTextView.setText(getString(model.getDesc()));

        // Set furniture price
        TextView priceTextView = cardView.findViewById(R.id.card_item_price);
        priceTextView.setText("₹" + model.getPrice());

        // Set AR view button click
        Button viewInARButton = cardView.findViewById(R.id.view_in_ar_item_button);
        viewInARButton.setOnClickListener(v -> {
            String arUrl = getString(model.getArUrl());
            if (arUrl != null && !arUrl.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(arUrl));
                startActivity(intent);
            } else {
                Toast.makeText(BedsCategoryPage.this, "AR link not available", Toast.LENGTH_SHORT).show();
            }
        });

        // Set Add to Cart button click
        Button addToCartButton = cardView.findViewById(R.id.add_to_cart_item_button);
        addToCartButton.setOnClickListener(v -> {
            Toast.makeText(BedsCategoryPage.this, "Added to cart", Toast.LENGTH_SHORT).show();
        });

        // Set click listener for the card view to navigate to ModelDetailPage
        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(BedsCategoryPage.this, ModelDetailPage.class);
            intent.putExtra("imageResId", model.getImageResource());
            intent.putExtra("modelName", getString(model.getName()));
            intent.putExtra("price", "₹" + model.getPrice());
            intent.putExtra("shopName", "Your Shop Name");  // Set shop name if applicable
            intent.putExtra("rating", "Rating: 4.5"); // Update with actual rating if available
            intent.putExtra("description", getString(model.getDesc()));
            intent.putExtra("arLink", getString(model.getArUrl())); // Pass the AR link
            startActivity(intent);
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

    public static class BedsModel implements Serializable { // Implement Serializable
        private int itemId;
        private int name;
        private double price;
        private int imageResource;
        private int description;
        private String shopUrl;
        private int arUrl;
        private boolean isFavorite;

        public BedsModel(int itemId,int name, double price, int imageResource, int description, String shopUrl, int arUrl) {
            this.itemId = itemId;
            this.name = name;
            this.price = price;
            this.imageResource = imageResource;
            this.description = description;
            this.shopUrl = shopUrl;
            this.arUrl = arUrl;
            this.isFavorite = false; // Initialize favorite state to false
        }

        public int getItemId(){
            return itemId;
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

        public int getDesc() {
            return description;
        }

        public String getShopUrl() {
            return shopUrl;
        }

        public int getArUrl() {
            return arUrl;
        }

        public boolean isFavorite() { // Getter for favorite state
            return isFavorite;
        }

        public void setFavorite(boolean favorite) { // Setter for favorite state
            isFavorite = favorite;
        }
    }
}
