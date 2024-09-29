package com.pulkit.platform.furnitureecommerceappui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent; // Import Intent
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout; // Import LinearLayout

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        // Initialize the home and profile layouts
        LinearLayout homeLinearLayout = findViewById(R.id.home_linear_layout);
        LinearLayout profileLinearLayout = findViewById(R.id.profile_linear_layout); // Add ID for profile layout

        // Set OnClickListener for the home button
        homeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main4Activity.this, Main2Activity.class);
                startActivity(intent);
                finish(); // Optional: Call finish() if you want to remove Main4Activity from the back stack
            }
        });

        // Set OnClickListener for the profile button
        profileLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main4Activity.this, Main3Activity.class);
                startActivity(intent);
                finish(); // Optional: Call finish() if you want to remove Main4Activity from the back stack
            }
        });
    }
}
