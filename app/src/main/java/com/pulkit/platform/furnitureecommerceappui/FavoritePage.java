package com.pulkit.platform.furnitureecommerceappui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FavoritePage extends AppCompatActivity {

    private GridView favoriteGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        // Initialize the GridView for favorite items
        favoriteGridView = findViewById(R.id.favorite_grid_view);

        // Set the adapter for the GridView
        favoriteGridView.setAdapter(new FavoriteAdapter());
    }

    private class FavoriteAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return FavoritesManager.getInstance().getFavoriteModels().size();
        }

        @Override
        public Object getItem(int position) {
            return FavoritesManager.getInstance().getFavoriteModels().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View cardView;
            if (convertView == null) {
                cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_model, parent, false);
            } else {
                cardView = convertView;
            }

            // Get the model for the current position
            BedsCategoryPage.BedsModel model = (BedsCategoryPage.BedsModel) getItem(position);

            // Set image resource for the model
            ImageView imageView = cardView.findViewById(R.id.card_image);
            imageView.setImageResource(model.getImageResource());

            // Set title for the model
            TextView titleTextView = cardView.findViewById(R.id.card_title);
            titleTextView.setText(getString(model.getName())); // Use getString to retrieve the name from resources

            // Set price for the model
            TextView priceTextView = cardView.findViewById(R.id.card_price);
            priceTextView.setText("₹" + model.getPrice());

            // Set onClick listener to open the detail page
            cardView.setOnClickListener(v -> {
                Intent intent = new Intent(FavoritePage.this, ModelDetailPage.class);
                intent.putExtra("furnitureModel", (CharSequence) model);  // Pass the selected model to the detail page
                startActivity(intent);
            });

            return cardView;
        }
    }
}
