package com.pulkit.platform.furnitureecommerceappui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main5Activity extends AppCompatActivity {
    private Button viewArButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        viewArButton = findViewById(R.id.viewAr_button);

        viewArButton.setOnClickListener(view -> {
            // Navigate to ARViewActivity
            Intent intent = new Intent(Main5Activity.this, ARViewActivity.class);
            startActivity(intent);
        });
    }
}
