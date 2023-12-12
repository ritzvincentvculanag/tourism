package io.github.rmmc.rmmctourism.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.viewpager.DetailAdapter;
import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.repository.ImageRepository;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class DestinationDetail extends AppCompatActivity implements WidgetInitializer, ActionInitializer {

    private TextView tvTitle;
    private TextView tvAddress;
    private ImageView ivDestinationCoverPhoto;
    private View dialogView;

    private ExtendedFloatingActionButton efabAddReview;

    private DetailAdapter detailAdapter;
    private ViewPager2 vpDestinationDetails;
    private TabLayout tlDestinationDetails;
    private ImageRepository imageRepository;
    private Destination destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_detail);
        imageRepository = new ImageRepository();
        initializeWidgets();
        initializeActions();
    }

    @Override
    public void initializeActions() {
        efabAddReview.setOnClickListener(e -> {
            Intent goToAddReview = new Intent(this, AddReview.class);
            startActivity(goToAddReview);
        });
    }

    @Override
    public void initializeWidgets() {
        tvTitle = findViewById(R.id.tv_destination_detail_title);
        tvAddress = findViewById(R.id.tv_destination_detail_address);
        ivDestinationCoverPhoto = findViewById(R.id.iv_destination_details_cover);
        populateData();

        efabAddReview = findViewById(R.id.fav_destination_detail_add_review);

        detailAdapter = new DetailAdapter(this, destination);

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

    private void populateData(){
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Destination.collectioName)) {
            destination = intent.getParcelableExtra(Destination.collectioName);
            tvTitle.setText(destination.getName());
            tvAddress.setText(destination.getAddress());

            imageRepository.loadUploadedImage(destination.getDestinationId(), ivDestinationCoverPhoto);
        }
    }

}

