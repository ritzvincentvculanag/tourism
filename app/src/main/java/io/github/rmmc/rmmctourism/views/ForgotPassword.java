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

    private TextInputLayout email;
    private Button forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initializeWidgets();
        initializeActions();
    }

    @Override
    public void initializeActions() {
        forgotPassword.setOnClickListener(this::forgotPasswordAction);
    }

    @Override
    public void initializeWidgets() {
        email = findViewById(R.id.til_forgot_password_email);
        forgotPassword = findViewById(R.id.btn_forgot_password);
    }

    private void forgotPasswordAction(View view) {
        // TODO: Handle forgot password click
    }

}