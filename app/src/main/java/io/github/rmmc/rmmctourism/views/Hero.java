package io.github.rmmc.rmmctourism.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class Hero extends AppCompatActivity implements WidgetInitializer, ActionInitializer {

    // Declare widgets
    private BottomNavigationView bnvHero;
    private NavHostFragment nvfHero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);

        // Initialize widgets and set up actions
        initializeWidgets();
        initializeActions();
    }

    @Override
    public void initializeActions() {
        // No specific actions initialized in this activity
        // (You can add actions related to Bottom Navigation item clicks or other interactions)
    }

    @Override
    public void initializeWidgets() {
        // Initialize Bottom Navigation
        initializeBottomNavigation();
    }

    private void initializeBottomNavigation() {
        // Find Bottom Navigation and NavHostFragment
        bnvHero = findViewById(R.id.bnvHero);
        nvfHero = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nvfHero);

        // Set up Bottom Navigation with Navigation Component
        if (nvfHero != null) {
            NavigationUI.setupWithNavController(bnvHero, nvfHero.getNavController());
        }
    }
}
