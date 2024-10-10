package com.pulkit.platform.furnitureecommerceappui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        // Apply only slide-in transition for Main3Activity (no exit transition)
        overridePendingTransition(R.anim.slide_in, 0);

        LinearLayout homeLinearLayout = findViewById(R.id.home_button);

        homeLinearLayout.setOnClickListener(v -> {
            Intent intent = new Intent(Main3Activity.this, Main2Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Finish Main3Activity to clear it from the back stack
            // Apply only slide-in transition for Main2Activity
            overridePendingTransition(R.anim.slide_in, 0); // No exit transition for Main3Activity
        });

        FloatingActionButton favoriteButton = findViewById(R.id.favorite_button);
        favoriteButton.setOnClickListener(v -> {
            Intent intent = new Intent(Main3Activity.this, Main4Activity.class);
            startActivity(intent);
            // Apply only slide-in transition for Main4Activity
            overridePendingTransition(R.anim.slide_in, 0); // No exit transition for Main3Activity
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Main3Activity.this, Main2Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Finish Main3Activity to clear it from the back stack
        // Apply only slide-in transition for Main2Activity
        overridePendingTransition(R.anim.fade_in, 0); // No exit transition for Main3Activity
    }
}
