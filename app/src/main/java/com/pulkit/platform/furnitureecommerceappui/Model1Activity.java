package com.pulkit.platform.furnitureecommerceappui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class Model1Activity extends AppCompatActivity {

    // List to store FurnitureModel objects
    private static List<FurnitureModel> furnitureModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Initialize the list and add your FurnitureModel objects
        initializeFurnitureModels();
    }

    private void initializeFurnitureModels() {
        furnitureModels = new ArrayList<>();
        // Add furniture models (example)
        furnitureModels.add(new FurnitureModel("Chair", "Model 1", "2000", "4.5", "Description of Model 1", R.drawable.model1));
        furnitureModels.add(new FurnitureModel("Bed", "Model 2", "3000", "4.0", "Description of Model 2", R.drawable.model2));
        // Add more models as needed
    }

    // Static method to get a FurnitureModel by index
    public static FurnitureModel getFurnitureModel(int index) {
        if (index >= 0 && index < furnitureModels.size()) {
            return furnitureModels.get(index);
        }
        return null; // Return null if index is out of bounds
    }
}
