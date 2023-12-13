package io.github.rmmc.rmmctourism.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import io.github.rmmc.rmmctourism.R;

public class UpdateEmail extends AppCompatActivity {

    private TextInputLayout email;
    private TextInputLayout password;

    private Button updateEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);

        email = findViewById(R.id.til_update_email);
        password = findViewById(R.id.til_update_email_password);

        updateEmail = findViewById(R.id.btn_update_email);
    }
}