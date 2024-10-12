package com.pulkit.platform.furnitureecommerceappui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordPage extends AppCompatActivity {

    private EditText emailEditText, newPasswordEditText, reenterPasswordEditText;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        emailEditText = findViewById(R.id.emailEditText);
        firebaseAuth = FirebaseAuth.getInstance();

        findViewById(R.id.resetPasswordButton).setOnClickListener(view -> resetPassword());
    }

    private void resetPassword() {
        String email = emailEditText.getText().toString().trim();
        String newPassword = newPasswordEditText.getText().toString().trim();
        String reenterPassword = reenterPasswordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required");
            return;
        }

        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgetPasswordPage.this, "Reset link sent to your email", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ForgetPasswordPage.this, AuthenticationPage.class));
                        finish();
                    } else {
                        Toast.makeText(ForgetPasswordPage.this, "Error occurred: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
