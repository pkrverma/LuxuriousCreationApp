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

    // Sample furniture models
    private FurnitureModel[] furnitureModels = {
            new FurnitureModel("Chair", 500.00, R.drawable.model1, "Great for comfort and style"),
            new FurnitureModel("Bed", 150.00, R.drawable.model2, "Perfect for dining"),
            new FurnitureModel("Sofa Set", 300.00, R.drawable.model3, "Cozy and modern"),
            new FurnitureModel("Sofa Set", 75.00, R.drawable.model4, "Comfortable seating"),
            new FurnitureModel("Dining Set", 450.00, R.drawable.model5, "Gather around this stylish set")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Find the GridLayout instead of LinearLayout
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        // Dynamically populate card views
        for (FurnitureModel model : furnitureModels) {
            addCardView(model, gridLayout); // Pass the gridLayout to the method
        }

//        // Find the buttons by their IDs
        LinearLayout profileButton = findViewById(R.id.profile_button);

        // Set onClickListener for the profile button to navigate to Main3Activity
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton favoriteButton = findViewById(R.id.favorite_button);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to navigate to Main4Activity
                Intent intent = new Intent(Main2Activity.this, Main4Activity.class);
                startActivity(intent);
            }
        });
    }


    private void addCardView(FurnitureModel model, GridLayout gridLayout) {
        // Inflate the card item layout
        View cardView = getLayoutInflater().inflate(R.layout.card_item, null);

        // Set up the views using their IDs
        ImageView imageView = cardView.findViewById(R.id.card_image);
        imageView.setImageResource(model.getImageResource());

        TextView titleTextView = cardView.findViewById(R.id.card_title);
        titleTextView.setText(model.getName());

        TextView priceTextView = cardView.findViewById(R.id.card_price);
        priceTextView.setText("₹" + model.getPrice());

        // Set layout parameters for GridLayout
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0; // Use 0dp for width to allow for weighting
        params.height = GridLayout.LayoutParams.WRAP_CONTENT;
        params.setMargins(8, 8, 8, 8); // Set margins for the grid items
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // Add this line to define the weight
        cardView.setLayoutParams(params);


        // Add the card view to the grid layout
        gridLayout.addView(cardView);
    }

    // FurnitureModel class to represent furniture data
    public static class FurnitureModel {
        private String name;
        private double price;
        private int imageResource;
        private String description;

        public FurnitureModel(String name, double price, int imageResource, String description) {
            this.name = name;
            this.price = price;
            this.imageResource = imageResource;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public int getImageResource() {
            return imageResource;
        }

        public String getDescription() {
            return description;
        }
    }
}