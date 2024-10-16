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

public class Main2Activity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main2);

        // Apply entry transition only when entering Main2Activity
        overridePendingTransition(R.anim.slide_in, 0);  // No exit transition for Main2Activity

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (FurnitureModel model : furnitureModels) {
            addCardView(model, gridLayout);
        }

        LinearLayout profileButton = findViewById(R.id.profile_button);
        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
            startActivity(intent);
            // Apply only the slide-in transition when navigating to Main3Activity
            overridePendingTransition(R.anim.slide_in, 0);  // No exit transition for Main2Activity
        });

        FloatingActionButton favoriteButton = findViewById(R.id.favorite_button);
        favoriteButton.setOnClickListener(v -> {
            Intent intent = new Intent(Main2Activity.this, Main4Activity.class);
            startActivity(intent);
            // Apply only the slide-in transition when navigating to Main4Activity
            overridePendingTransition(R.anim.slide_in, 0);  // No exit transition for Main2Activity
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();  // Close all activities and exit the app
        // Apply both fade-in and fade-out transitions when exiting the app
        overridePendingTransition(0, R.anim.fade_out);  // Fade-out transition for app exit
    }

    private void addCardView(FurnitureModel model, GridLayout gridLayout) {
        View cardView = getLayoutInflater().inflate(R.layout.card_item, null);
        ImageView imageView = cardView.findViewById(R.id.card_image);
        imageView.setImageResource(model.getImageResource());

        TextView titleTextView = cardView.findViewById(R.id.card_title);
        titleTextView.setText(model.getName());

        TextView priceTextView = cardView.findViewById(R.id.card_price);
        priceTextView.setText("₹" + model.getPrice());

        // Set OnClickListener for cardView to open Model1Activity with the selected data
        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(Main2Activity.this, Model1Activity.class);
            // Pass the model data to Model1Activity
            intent.putExtra("model_name", model.getName());
            intent.putExtra("model_price", model.getPrice());
            intent.putExtra("model_image", model.getImageResource());
            intent.putExtra("model_description", model.getDescription());
            intent.putExtra("shop_url", model.getShopUrl());
            intent.putExtra("ar_url", model.getArUrl());
            startActivity(intent);
        });

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;
        params.height = GridLayout.LayoutParams.WRAP_CONTENT;
        params.setMargins(8, 8, 8, 8);
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        cardView.setLayoutParams(params);

        gridLayout.addView(cardView);
    }

    public static class FurnitureModel {
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
