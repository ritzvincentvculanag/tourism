package io.github.rmmc.rmmctourism.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import io.github.rmmc.rmmctourism.R;

public class UpdatePassword extends AppCompatActivity {

    private TextInputLayout oldPassword;
    private TextInputLayout newPassword;
    private TextInputLayout confirmPassword;

    private Button updatePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        oldPassword = findViewById(R.id.til_update_password_old);
        newPassword = findViewById(R.id.til_update_password_new);
        confirmPassword = findViewById(R.id.til_update_password_confirm);

        updatePassword = findViewById(R.id.btn_update_password);
    }
}