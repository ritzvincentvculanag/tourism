package io.github.rmmc.rmmctourism.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.viewpager.DetailAdapter;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class DestinationDetail extends AppCompatActivity implements WidgetInitializer, ActionInitializer {

    private DetailAdapter detailAdapter;
    private ViewPager2 vpDestinationDetails;
    private TabLayout tlDestinationDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_detail);

        initializeActions();
        initializeWidgets();
    }

    @Override
    public void initializeActions() {

    }

    @Override
    public void initializeWidgets() {
        detailAdapter = new DetailAdapter(this);

        tlDestinationDetails = findViewById(R.id.tl_destination_details);
        vpDestinationDetails = findViewById(R.id.vp_destination_details);
        vpDestinationDetails.setAdapter(detailAdapter);

        initializeViewPager();
    }

    private void initializeViewPager() {
        tlDestinationDetails.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpDestinationDetails.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        vpDestinationDetails.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                tlDestinationDetails.getTabAt(position).select();
            }
        });
    }
}

