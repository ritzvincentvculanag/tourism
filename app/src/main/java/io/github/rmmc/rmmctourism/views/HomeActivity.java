package io.github.rmmc.rmmctourism.views;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

import android.view.MenuItem;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.fragments.DestinationsView;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class HomeActivity extends AppCompatActivity implements ActionInitializer, WidgetInitializer {


    private BottomNavigationView userNavView;
    private FirebaseAuth userAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initializeWidgets();
        initializeActions();
        ChangeFragment(new DestinationsView());
        userAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void initializeActions() {
        userNavView.setOnItemSelectedListener(this :: bottomNavBarListener);
    }

    @Override
    public void initializeWidgets() {
        userNavView = findViewById(R.id.user_navigation);
    }

    private boolean bottomNavBarListener(MenuItem item ){
        if(item.getItemId() == R.id.user_home){
            ChangeFragment(new DestinationsView());
        }else if(item.getItemId() == R.id.user_logout){
            userLogout();
        }
        return true;
    }

    private void userLogout(){

        new MaterialAlertDialogBuilder(this)
                .setTitle("Logout")
                .setMessage("Do you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
        }


    private void logout(){
        userAuth.signOut();
        startActivity(new Intent(this, Login.class));
        finish();
    }
    public void ChangeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }


}