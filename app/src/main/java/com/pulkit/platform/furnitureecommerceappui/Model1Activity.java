package com.pulkit.platform.furnitureecommerceappui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Model1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model1);

        // Retrieve the data passed from Main2Activity
        Intent intent = getIntent();
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
    }
}
