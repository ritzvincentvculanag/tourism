package io.github.rmmc.rmmctourism;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.Timestamp;

import java.util.Date;

import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.repository.DestinationRepository;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.Messenger;
import io.github.rmmc.rmmctourism.util.NetworkUtils;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;
import io.github.rmmc.rmmctourism.views.Login;
import io.github.rmmc.rmmctourism.views.Register;

public class MainActivity extends AppCompatActivity implements ActionInitializer, WidgetInitializer {

    private Button btnTraveler;
    private Button btnManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initializeWidgets();
        initializeActions();
    }

    @Override
    public void initializeWidgets() {
        btnManager = findViewById(R.id.btnManager);
        btnTraveler = findViewById(R.id.btnTraveler);
    }

    @Override
    public void initializeActions() {
        btnTraveler.setOnClickListener(this::login);
        btnManager.setOnClickListener(this::register);
    }

    private void register(View view) {
        if (!NetworkUtils.isNetworkConnected(this)) {
            Messenger.showAlertDialog(this, "Internet Connection","Please connect to the internet before using the application", "Ok").show();
            return;
        }
        startActivity(new Intent(this, Register.class));
    }

    private void login(View view) {
        if (!NetworkUtils.isNetworkConnected(this)) {
            Messenger.showAlertDialog(this, "Internet Connection","Please connect to the internet before using the application", "Ok").show();
            return;
        }
        startActivity(new Intent(this, Login.class));
    }




}