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
import io.github.rmmc.rmmctourism.util.Messenger;
import io.github.rmmc.rmmctourism.util.Miner;
import io.github.rmmc.rmmctourism.util.NetworkUtils;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class Login extends AppCompatActivity implements WidgetInitializer, ActionInitializer {

    // Declare widgets
    private TextInputLayout tilLoginUsername;
    private TextInputLayout tilLoginPassword;
    private Button btnLogin;
    private Button btnNotRegistered;
    private Button btnForgotPassword;

    // Firebase Authentication
    private FirebaseAuth userAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize widgets, actions, and Firebase Authentication
        initializeWidgets();
        initializeActions();
        initializeFirebaseAuth();
    }

    @Override
    public void initializeWidgets() {
        // Initialize UI widgets
        tilLoginUsername = findViewById(R.id.til_login_username);
        tilLoginPassword = findViewById(R.id.til_login_password);
        btnLogin = findViewById(R.id.btn_login);
        btnNotRegistered = findViewById(R.id.btn_not_registered);
        btnForgotPassword = findViewById(R.id.btn_forgot_password);
    }

    @Override
    public void initializeActions() {
        // Set up click listeners for buttons
        btnLogin.setOnClickListener(this::login);
        btnNotRegistered.setOnClickListener(this::register);
        btnForgotPassword.setOnClickListener(this::forgotPassword);
    }

    private void initializeFirebaseAuth() {
        // Initialize Firebase Authentication
        userAuth = FirebaseAuth.getInstance();

        // Check if the user is already authenticated, if yes, redirect to the home activity
        if (userAuth.getCurrentUser() != null) {
            Intent goToHome = new Intent(this, Hero.class);
            startActivity(goToHome);
        }
    }

    private void login(View view) {
        // Check internet connection
        if (!NetworkUtils.isNetworkConnected(this)) {
            Messenger.showAlertDialog(this, "Internet Connection", "Please connect to the internet before using the application", "Ok").show();
            return;
        }

        // Fields validation
        if (fieldsAreEmpty(tilLoginUsername, tilLoginPassword)) {
            showAlertDialog(
                    this,
                    getString(R.string.login_dialog_error_title),
                    getString(R.string.login_dialog_error_message),
                    getString(R.string.login_dialog_error_positive_button_title)
            ).show();
            return;
        }

        // Get username and password
        String username = Miner.getString(tilLoginUsername);
        String password = Miner.getString(tilLoginPassword);

        // Attempt to sign in with Firebase Authentication
        userAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, this::loginOnComplete);
    }

    private void loginOnComplete(Task<AuthResult> task) {
        // Check if the login was successful
        if (!task.isSuccessful()) {
            showAlertDialog(
                    this,
                    getString(R.string.login_dialog_error_title),
                    "Email or password is incorrect!",
                    getString(R.string.login_dialog_error_positive_button_title)
            ).show();
            return;
        }

        // Redirect to the home activity on successful login
        Intent goToHome = new Intent(this, Hero.class);
        startActivity(goToHome);
        finish();
    }

    private void register(View view) {
        // Redirect to the registration activity
        Intent gotToRegister = new Intent(this, Register.class);
        startActivity(gotToRegister);
    }

    private void forgotPassword(View view) {
        startActivity(new Intent(this, ForgotPassword.class));
    }
}
