 package io.github.rmmc.rmmctourism.views;

import static io.github.rmmc.rmmctourism.util.Messenger.showAlertDialog;
import static io.github.rmmc.rmmctourism.util.Validator.fieldIsEmpty;
import static io.github.rmmc.rmmctourism.util.Validator.fieldsAreEmpty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.util.Messenger;
import io.github.rmmc.rmmctourism.util.Validator;

 public class Login extends AppCompatActivity {
     private TextInputLayout tilLoginUsername;
     private TextInputLayout tilLoginPassword;

     private Button btnLogin;
     private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeWidgets();
        initializeListeners();
    }

    private void initializeWidgets() {
        tilLoginUsername = findViewById(R.id.til_login_username);
        tilLoginPassword = findViewById(R.id.til_login_password);

        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
    }

    private void initializeListeners() {
        btnLogin.setOnClickListener(this::login);
        btnRegister.setOnClickListener(this::register);
    }

    private void login(View view) {
        if (fieldsAreEmpty(tilLoginUsername, tilLoginPassword)) {
            showAlertDialog(
                    this,
                    "Login Error",
                    "Username and password is required. Try again."
            ).show();

            return;
        }

        Intent goToHome = new Intent(this, HomeActivity.class);
        startActivity(goToHome);
    }

    private void register(View view) {
        Intent gotToRegister = new Intent(this, Register.class);
        startActivity(gotToRegister);
    }
}