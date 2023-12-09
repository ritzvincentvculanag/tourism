package io.github.rmmc.rmmctourism;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.github.rmmc.rmmctourism.util.ActionInitializer;
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
        startActivity(new Intent(this, Register.class));
    }

    private void login(View view) {
        startActivity(new Intent(this, Login.class));
    }

}