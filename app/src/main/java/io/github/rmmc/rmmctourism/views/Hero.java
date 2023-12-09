package io.github.rmmc.rmmctourism.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class Hero extends AppCompatActivity implements WidgetInitializer, ActionInitializer {

    private BottomNavigationView bnvHero;
    private NavHostFragment nvfHero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);

        initializeWidgets();
        initializeActions();
    }

    @Override
    public void initializeActions() {

    }

    @Override
    public void initializeWidgets() {


        initializeBottomNavigation();
    }

    private void initializeBottomNavigation() {
        bnvHero = findViewById(R.id.bnvHero);
        nvfHero = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nvfHero);

        if (nvfHero != null) {
            NavigationUI.setupWithNavController(bnvHero, nvfHero.getNavController());
        }
    }
}