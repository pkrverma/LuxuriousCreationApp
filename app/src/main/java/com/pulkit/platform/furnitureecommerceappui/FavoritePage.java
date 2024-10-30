package com.pulkit.platform.furnitureecommerceappui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class FavoritePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        // Apply only slide-in transition for FavoritePage (entry transition only)
        overridePendingTransition(R.anim.slide_in, 0); // No exit transition for the previous activity

        LinearLayout homeLinearLayout = findViewById(R.id.home_button);
        LinearLayout profileLinearLayout = findViewById(R.id.profile_button);

        homeLinearLayout.setOnClickListener(v -> {
            Intent intent = new Intent(FavoritePage.this, HomePage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Clear FavoritePage from the back stack
            // Apply only slide-in transition for HomePage
            overridePendingTransition(R.anim.slide_in, 0); // No exit transition for FavoritePage
        });

        profileLinearLayout.setOnClickListener(v -> {
            Intent intent = new Intent(FavoritePage.this, ProfilePage.class);
            startActivity(intent);
            // Apply only slide-in transition for ProfilePage
            overridePendingTransition(R.anim.slide_in, 0); // No exit transition for FavoritePage
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FavoritePage.this, HomePage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Finish FavoritePage to remove it from the back stack
        // Apply only slide-in transition for HomePage
        overridePendingTransition(R.anim.fade_in, 0); // No exit transition for FavoritePage
    }
}
