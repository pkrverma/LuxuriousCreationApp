package com.pulkit.platform.furnitureecommerceappui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        // Apply only slide-in transition for Main4Activity (entry transition only)
        overridePendingTransition(R.anim.slide_in, 0); // No exit transition for the previous activity

        LinearLayout homeLinearLayout = findViewById(R.id.home_button);
        LinearLayout profileLinearLayout = findViewById(R.id.profile_button);

        homeLinearLayout.setOnClickListener(v -> {
            Intent intent = new Intent(Main4Activity.this, Main2Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Clear Main4Activity from the back stack
            // Apply only slide-in transition for Main2Activity
            overridePendingTransition(R.anim.slide_in, 0); // No exit transition for Main4Activity
        });

        profileLinearLayout.setOnClickListener(v -> {
            Intent intent = new Intent(Main4Activity.this, Main3Activity.class);
            startActivity(intent);
            // Apply only slide-in transition for Main3Activity
            overridePendingTransition(R.anim.slide_in, 0); // No exit transition for Main4Activity
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Main4Activity.this, Main2Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Finish Main4Activity to remove it from the back stack
        // Apply only slide-in transition for Main2Activity
        overridePendingTransition(R.anim.fade_in, 0); // No exit transition for Main4Activity
    }
}
