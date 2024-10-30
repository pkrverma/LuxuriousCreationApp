package com.pulkit.platform.furnitureecommerceappui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;

public class HomePage extends AppCompatActivity {

    private FurnitureModel[] furnitureModels = {
            new FurnitureModel(R.string.model1_name, 950.00, R.drawable.model1, R.string.model1_desc, "", R.string.model1_arLink),
            new FurnitureModel(R.string.model2_name, 13540.00, R.drawable.model2, R.string.model2_desc, "", R.string.model2_arLink),
            new FurnitureModel(R.string.model3_name, 11200.00, R.drawable.model3, R.string.model3_desc, "", R.string.model3_arLink),
            new FurnitureModel(R.string.model4_name, 9569.00, R.drawable.model4, R.string.model4_desc, "", R.string.model4_arLink),
            new FurnitureModel(R.string.model5_name, 15450.00, R.drawable.model5, R.string.model5_desc, "", R.string.model5_arLink),
            new FurnitureModel(R.string.model6_name, 2350.00, R.drawable.model6, R.string.model6_desc, "", R.string.model6_arLink),
            new FurnitureModel(R.string.model7_name, 7420.00, R.drawable.model7, R.string.model7_desc, "", R.string.model7_arLink),
            new FurnitureModel(R.string.model8_name, 8420.00, R.drawable.model8, R.string.model8_desc, "", R.string.model8_arLink),
            new FurnitureModel(R.string.model9_name, 14490.00, R.drawable.model9, R.string.model9_desc, "", R.string.model9_arLink)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Apply entry transition only when entering HomePage
        overridePendingTransition(R.anim.slide_in, 0);  // No exit transition for HomePage

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (FurnitureModel model : furnitureModels) {
            addCardView(model, gridLayout);
        }

        LinearLayout profileButton = findViewById(R.id.profile_button);
        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, ProfilePage.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, 0);
        });

        FloatingActionButton favoriteButton = findViewById(R.id.favorite_button);
        favoriteButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, FavoritePage.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, 0);
        });

        // Set up category card click listeners with reusable method
        addCategoryCardClickListener(R.id.bed_card_view, BedsCategoryPage.class);
        addCategoryCardClickListener(R.id.table_card_view, TablesCategoryPage.class);
        addCategoryCardClickListener(R.id.sofaset_card_view, SofaSetsCategoryPage.class);

        // Favorite item icon click listener
        favoriteItemClickListener();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        overridePendingTransition(0, R.anim.fade_out);
    }

    private void addCardView(FurnitureModel model, GridLayout gridLayout) {
        // Inflate the card view layout from XML
        View cardView = getLayoutInflater().inflate(R.layout.card_model, null);

        // Set image, title, and price for the card view
        ImageView imageView = cardView.findViewById(R.id.card_image);
        imageView.setImageResource(model.getImageResource());

        TextView titleTextView = cardView.findViewById(R.id.card_title);
        titleTextView.setText(getString(model.getName()));

        TextView priceTextView = cardView.findViewById(R.id.card_price);
        priceTextView.setText("₹" + model.getPrice());

        // Set OnClickListener for the card to open ModelDetailPage with the selected model data
        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, ModelDetailPage.class);
            intent.putExtra("furnitureModel", model);  // Pass the entire model as Serializable
            startActivity(intent);
        });

        // Create layout parameters for the card view to fit within GridLayout's columns
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0; // Set width to 0 to allow weight to manage space
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // Span 1 column with equal width distribution
        params.setMargins(8, 8, 8, 8); // Set margin between cards

        // Apply the layout parameters to the card view and add it to the grid
        cardView.setLayoutParams(params);
        gridLayout.addView(cardView);
    }


    private void favoriteItemClickListener() {
        ImageView cartIcon = findViewById(R.id.cart_icon);
        cartIcon.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, FavoritePage.class);
            startActivity(intent);
        });
    }

    // Reusable method to set up category card click listeners
    private void addCategoryCardClickListener(int viewId, Class<?> targetActivity) {
        View cardView = findViewById(viewId);
        if (cardView != null) {
            cardView.setOnClickListener(v -> {
                Intent intent = new Intent(HomePage.this, targetActivity);
                startActivity(intent);
            });
        }
    }

    public static class FurnitureModel implements Serializable {
        private int name;
        private double price;
        private int imageResource;
        private int description;
        private String shopUrl;
        private int arUrl;

        public FurnitureModel(int name, double price, int imageResource, int description, String shopUrl, int arUrl) {
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
