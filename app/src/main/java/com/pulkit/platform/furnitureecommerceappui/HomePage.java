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

    private GridLayout gridLayout;

    private final FurnitureModel[] furnitureModels = {
            new FurnitureModel(R.string.itemId1, R.string.model1_name, 950.00, R.drawable.model1, R.string.model1_desc, "", R.string.model1_arLink),
            new FurnitureModel(R.string.itemId2, R.string.model2_name, 13540.00, R.drawable.model2, R.string.model2_desc, "", R.string.model2_arLink),
            new FurnitureModel(R.string.itemId3, R.string.model3_name, 11200.00, R.drawable.model3, R.string.model3_desc, "", R.string.model3_arLink),
            new FurnitureModel(R.string.itemId4, R.string.model4_name, 9569.00, R.drawable.model4, R.string.model4_desc, "", R.string.model4_arLink),
            new FurnitureModel(R.string.itemId5, R.string.model5_name, 15450.00, R.drawable.model5, R.string.model5_desc, "", R.string.model5_arLink),
            new FurnitureModel(R.string.itemId6, R.string.model6_name, 2350.00, R.drawable.model6, R.string.model6_desc, "", R.string.model6_arLink),
            new FurnitureModel(R.string.itemId7, R.string.model7_name, 7420.00, R.drawable.model7, R.string.model7_desc, "", R.string.model7_arLink),
            new FurnitureModel(R.string.itemId8, R.string.model8_name, 8420.00, R.drawable.model8, R.string.model8_desc, "", R.string.model8_arLink),
            new FurnitureModel(R.string.itemId9, R.string.model9_name, 14490.00, R.drawable.model9, R.string.model9_desc, "", R.string.model9_arLink)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        gridLayout = findViewById(R.id.gridLayout);
        overridePendingTransition(R.anim.slide_in, 0);  // No exit transition for HomePage

        for (FurnitureModel model : furnitureModels) {
            addCardView(model);
        }

        setupNavigationButtons();
        setupCategoryCardClickListeners();
        setupFavoriteItemClickListener();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        overridePendingTransition(0, R.anim.fade_out);
    }

    private void addCardView(FurnitureModel model) {
        View cardView = getLayoutInflater().inflate(R.layout.card_model, gridLayout, false);

        ImageView imageView = cardView.findViewById(R.id.card_image);
        imageView.setImageResource(model.getImageResource());

        TextView titleTextView = cardView.findViewById(R.id.card_title);
        titleTextView.setText(model.getName());

        TextView priceTextView = cardView.findViewById(R.id.card_price);
        priceTextView.setText("₹" + model.getPrice());

        // Set an OnClickListener on the card to redirect to ModelDetailPage
        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, ModelDetailPage.class);
            intent.putExtra("imageResId", model.getImageResource()); // Change to match what ModelDetailPage expects
            intent.putExtra("modelName", getString(model.getName())); // Use getString to get actual name
            intent.putExtra("price", "₹" + model.getPrice()); // Format price
            intent.putExtra("shopName", model.getShopUrl()); // Assuming you want to show the shop URL here
            intent.putExtra("rating", "4.5"); // Set a default rating or retrieve it
            intent.putExtra("description", getString(model.getDesc())); // Use getString for description
            intent.putExtra("arLink", getString(model.getArUrl())); // Use getString for AR link
            startActivity(intent);
        });

        // Create layout parameters for the card view to fit within GridLayout's columns
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0; // Set width to 0 to allow weight to manage space
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // Span 1 column with equal width distribution
        params.setMargins(8, 8, 8, 8); // Set margin between cards

        cardView.setLayoutParams(params);
        gridLayout.addView(cardView);
    }

    private void setupNavigationButtons() {
        LinearLayout profileButton = findViewById(R.id.profile_button);
        profileButton.setOnClickListener(v -> {
            startActivity(new Intent(HomePage.this, ProfilePage.class));
            overridePendingTransition(R.anim.slide_in, 0);
        });

        FloatingActionButton favoriteButton = findViewById(R.id.favorite_button);
        favoriteButton.setOnClickListener(v -> {
            startActivity(new Intent(HomePage.this, FavoritePage.class));
            overridePendingTransition(R.anim.slide_in, 0);
        });
    }

    private void setupFavoriteItemClickListener() {
        ImageView cartIcon = findViewById(R.id.cart_icon);
        cartIcon.setOnClickListener(v -> {
            startActivity(new Intent(HomePage.this, AddCartPage.class));
        });
    }

    private void setupCategoryCardClickListeners() {
        addCategoryCardClickListener(R.id.bed_card_view, "Beds", BedsCategoryPage.class);
        addCategoryCardClickListener(R.id.table_card_view, "Tables", TablesCategoryPage.class);
        addCategoryCardClickListener(R.id.sofaset_card_view, "Sofa Sets", SofaSetsCategoryPage.class);
    }

    private void addCategoryCardClickListener(int viewId, String category, Class<?> targetActivity) {
        View cardView = findViewById(viewId);
        if (cardView != null) {
            cardView.setOnClickListener(v -> {
                Intent intent = new Intent(HomePage.this, targetActivity);
                intent.putExtra("CATEGORY", category);  // Pass the selected category
                startActivity(intent);
            });
        }
    }


    public static class FurnitureModel implements Serializable {
        private final int itemId; // Unique ID for each model
        private final int imageResource;
        private final int name;
        private final int desc;
        private final double price;
        private final int arLink;
        private final String shopUrl;

        public FurnitureModel(int itemId, int name, double price, int imageResource, int desc, String shopUrl, int arLink) {
            this.itemId = itemId;
            this.imageResource = imageResource;
            this.name = name;
            this.desc = desc;
            this.shopUrl = shopUrl;
            this.price = price;
            this.arLink = arLink;
        }

        public int getId() {
            return itemId;
        }

        public int getImageResource() {
            return imageResource;
        }

        public int getName() {
            return name;
        }

        public int getDesc() {
            return desc;
        }

        public double getPrice() {
            return price;
        }

        public String getShopUrl() {
            return shopUrl;
        }

        public int getArUrl() {
            return arLink;
        }

        // Override equals and hashCode to compare models by itemId
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            FurnitureModel that = (FurnitureModel) obj;
            return itemId == that.itemId;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(itemId);  // Directly return the hash code for the int itemId
        }
    }
}
