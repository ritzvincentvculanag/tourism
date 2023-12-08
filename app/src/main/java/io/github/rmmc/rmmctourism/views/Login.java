package io.github.rmmc.rmmctourism.views;

import static io.github.rmmc.rmmctourism.util.Messenger.showAlertDialog;
import static io.github.rmmc.rmmctourism.util.Validator.fieldsAreEmpty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.Miner;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class Login extends AppCompatActivity implements WidgetInitializer, ActionInitializer {

    private TextInputLayout tilLoginUsername;
    private TextInputLayout tilLoginPassword;

    private Button btnLogin;
    private Button btnNotRegistered;
    private Button btnForgotPassword;

    private FirebaseAuth userAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeWidgets();
        initializeActions();
        initializeFirebaseAuth();
    }

    @Override
    public void initializeWidgets() {
        tilLoginUsername = findViewById(R.id.til_login_username);
        tilLoginPassword = findViewById(R.id.til_login_password);

        btnLogin = findViewById(R.id.btn_login);
        btnNotRegistered = findViewById(R.id.btn_not_registered);
        btnForgotPassword = findViewById(R.id.btn_forgot_password);
    }

    @Override
    public void initializeActions() {
        btnLogin.setOnClickListener(this::login);
        btnNotRegistered.setOnClickListener(this::register);
    }

    private void initializeFirebaseAuth() {
        userAuth = FirebaseAuth.getInstance();
        if (userAuth.getCurrentUser() == null) {
            return;
        }

        Intent goToHome = new Intent(this, HomeActivity.class);
        startActivity(goToHome);
    }

    private void login(View view) {
        if (fieldsAreEmpty(tilLoginUsername, tilLoginPassword)) {
            showAlertDialog(
                    this,
                    getString(R.string.login_dialog_error_title),
                    getString(R.string.login_dialog_error_message),
                    getString(R.string.login_dialog_error_positive_button_title)
            ).show();

            return;
        }

        String username = Miner.getString(tilLoginUsername);
        String password = Miner.getString(tilLoginPassword);
        userAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, this::loginOnComplete);
    }

    private void loginOnComplete(Task<AuthResult> task) {
        if (!task.isSuccessful()) {
            showAlertDialog(
                    this,
                    getString(R.string.login_dialog_error_title),
                    getString(R.string.login_dialog_error_user_not_found),
                    getString(R.string.login_dialog_error_positive_button_title)
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

    private void forgotPassword(View view) {
        // TODO: Implement forgot password
    }

}