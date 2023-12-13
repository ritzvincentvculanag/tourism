package io.github.rmmc.rmmctourism.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class ForgotPassword extends AppCompatActivity implements WidgetInitializer, ActionInitializer {

    // Declare widgets
    private TextInputLayout email;
    private Button forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Initialize widgets and set up actions
        initializeWidgets();
        initializeActions();
    }

    @Override
    public void initializeActions() {
        // Set up click listener for the "Forgot Password" button
        forgotPassword.setOnClickListener(this::forgotPasswordAction);
    }

    @Override
    public void initializeWidgets() {
        // Initialize UI widgets
        email = findViewById(R.id.til_forgot_password_email);
        forgotPassword = findViewById(R.id.btn_forgot_password);
    }

    private void forgotPasswordAction(View view) {
        // TODO: Implement the logic for handling the "Forgot Password" action
        // (e.g., send a reset password email or navigate to a password reset screen)
    }
}
