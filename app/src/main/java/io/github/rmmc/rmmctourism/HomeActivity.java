package io.github.rmmc.rmmctourism;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.github.rmmc.rmmctourism.fragments.SpotViewerFragment;

public class HomeActivity extends AppCompatActivity {


    private BottomNavigationView userNavView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userNavView = findViewById(R.id.user_navigation);
        ChangeFragment(new SpotViewerFragment());

        userNavView.setOnItemSelectedListener(item ->{
            if(item.getItemId() == R.id.user_home){
                ChangeFragment(new SpotViewerFragment());
            }
            return true;
        });

    }

    public void ChangeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}