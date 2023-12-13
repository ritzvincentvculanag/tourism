package io.github.rmmc.rmmctourism;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.google.firebase.auth.FirebaseAuth;

import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.Messenger;
import io.github.rmmc.rmmctourism.util.NetworkUtils;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;
import io.github.rmmc.rmmctourism.views.Hero;
import io.github.rmmc.rmmctourism.views.Login;
import io.github.rmmc.rmmctourism.views.Register;

public class MainActivity extends AppCompatActivity implements ActionInitializer, WidgetInitializer {

    private Button btnTraveler;
    private FirebaseAuth userAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeWidgets();
        initializeActions();
        initializeFirebaseAuth();
    }

    @Override
    public void initializeWidgets() {
        btnTraveler = findViewById(R.id.btnTraveler);
    }

    @Override
    public void initializeActions() {
        btnTraveler.setOnClickListener(this::login);
    }

    private void initializeFirebaseAuth() {
        userAuth = FirebaseAuth.getInstance();
        if (userAuth.getCurrentUser() == null) {
            return;
        }

        Intent goToHome = new Intent(this, Hero.class);
        startActivity(goToHome);
        finish();

    }

    private void login(View view) {
        if (!NetworkUtils.isNetworkConnected(this)) {
            Messenger.showAlertDialog(this, "Internet Connection","Please connect to the internet before using the application", "Ok").show();
            return;
        }
        startActivity(new Intent(this, Login.class));
    }

}