package com.pulkit.platform.furnitureecommerceappui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;

public class ProfilePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        // Apply only slide-in transition for ProfilePage (no exit transition)
        overridePendingTransition(R.anim.slide_in, 0);

        LinearLayout homeLinearLayout = findViewById(R.id.home_button);

        homeLinearLayout.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilePage.this, HomePage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Finish ProfilePage to clear it from the back stack
            // Apply only slide-in transition for HomePage
            overridePendingTransition(R.anim.slide_in, 0); // No exit transition for ProfilePage
        });

        FloatingActionButton favoriteButton = findViewById(R.id.favorite_button);
        favoriteButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilePage.this, FavoritePage.class);
            startActivity(intent);
            // Apply only slide-in transition for FavoritePage
            overridePendingTransition(R.anim.slide_in, 0); // No exit transition for ProfilePage
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfilePage.this, HomePage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Finish ProfilePage to clear it from the back stack
        // Apply only slide-in transition for HomePage
        overridePendingTransition(R.anim.fade_in, 0); // No exit transition for ProfilePage
    }
}
