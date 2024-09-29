package com.pulkit.platform.furnitureecommerceappui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.ar.core.ArCoreApk;
import com.google.ar.core.ArCoreApk.Availability;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class ARViewActivity extends AppCompatActivity {

    private ArFragment arFragment;
    private ModelRenderable modelRenderable;
    private boolean installRequested;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_view);

        // Check if ARCore is available
        if (!checkArCoreAvailability()) {
            Toast.makeText(this, "ARCore is not available on this device.", Toast.LENGTH_LONG).show();
            finish(); // Close the activity if ARCore is not available
            return;
        }

        // Initialize AR Fragment
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        // Load the 3D model
        ModelRenderable.builder()
                .setSource(this, R.raw.chair4)  // Replace with your actual model file
                .build()
                .thenAccept(renderable -> modelRenderable = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model", Toast.LENGTH_LONG).show();
                    return null;
                });

        // Set up AR tap listener to place model
        arFragment.setOnTapArPlaneListener((HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
            if (modelRenderable == null) {
                return;
            }

            // Create an anchor at the tapped location
            Anchor anchor = hitResult.createAnchor();
            AnchorNode anchorNode = new AnchorNode(anchor);
            anchorNode.setParent(arFragment.getArSceneView().getScene());

            // Attach the 3D model to the anchor node
            TransformableNode modelNode = new TransformableNode(arFragment.getTransformationSystem());
            modelNode.setParent(anchorNode);
            modelNode.setRenderable(modelRenderable);
            modelNode.select();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check for ARCore installation prompt
        try {
            if (ArCoreApk.getInstance().requestInstall(this, !installRequested) == ArCoreApk.InstallStatus.INSTALL_REQUESTED) {
                installRequested = true;
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (arFragment != null) {
            arFragment.getArSceneView().pause();
        }
    }

    private boolean checkArCoreAvailability() {
        Availability availability = ArCoreApk.getInstance().checkAvailability(this);
        if (availability.isTransient()) {
            // Wait until ARCore finishes checking availability
            availability = ArCoreApk.getInstance().checkAvailability(this);
        }
        return availability.isSupported();
    }
}
