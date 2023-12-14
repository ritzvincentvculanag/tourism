package io.github.rmmc.rmmctourism.views;// Import statements

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.viewpager.DetailAdapter;
import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.model.Favorite;
import io.github.rmmc.rmmctourism.repository.FavoriteRepository;
import io.github.rmmc.rmmctourism.repository.ImageRepository;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.OnViewFavoriteCallback;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class DestinationDetail extends AppCompatActivity implements WidgetInitializer, ActionInitializer {

    private static boolean isFavorite = true;
    // UI components
    private TextView tvTitle;
    private TextView tvAddress;
    private ImageView ivDestinationCoverPhoto;
    private View dialogView;
    private ExtendedFloatingActionButton efabAddReview;
    private Button btnFavorite;
    private DetailAdapter detailAdapter;
    private ViewPager2 vpDestinationDetails;
    private TabLayout tlDestinationDetails;
    private ImageRepository imageRepository;
    private Destination destination;
    private FavoriteRepository favoriteRepository;
    private FirebaseAuth userAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_detail);
        imageRepository = new ImageRepository();
        userAuth = FirebaseAuth.getInstance();
        favoriteRepository = new FavoriteRepository(this);
        initializeWidgets();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data when the activity resumes
        populateData();
    }

    @Override
    public void initializeActions() {
        // Action: Open AddReview activity when the ExtendedFloatingActionButton is clicked
        efabAddReview.setOnClickListener(e -> {
            Intent goToAddReview = new Intent(this, AddReview.class);
            goToAddReview.putExtra(Destination.collectioName, destination);
            startActivity(goToAddReview);
        });

        // Action: Handle favorite button click

    }

    @Override
    public void initializeWidgets() {
        // Initialize UI components
        tvTitle = findViewById(R.id.tv_destination_detail_title);
        tvAddress = findViewById(R.id.tv_destination_detail_address);
        ivDestinationCoverPhoto = findViewById(R.id.iv_destination_details_cover);
        populateData();

        efabAddReview = findViewById(R.id.fav_destination_detail_add_review);
        btnFavorite = findViewById(R.id.btn_destination_detail_favorite);

        // Initialize ViewPager and TabLayout
        detailAdapter = new DetailAdapter(this, destination);
        tlDestinationDetails = findViewById(R.id.tl_destination_details);
        vpDestinationDetails = findViewById(R.id.vp_destination_details);
        vpDestinationDetails.setAdapter(detailAdapter);

        // Set up the relationship between ViewPager and TabLayout
        initializeViewPager();
        initializeActions();
    }

    private void initializeViewPager() {
        // Set up TabLayout to respond to ViewPager changes
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

    private void populateData() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Destination.collectioName)) {
            destination = intent.getParcelableExtra(Destination.collectioName);
            tvTitle.setText(destination.getName());
            tvAddress.setText(destination.getAddress());

            imageRepository.loadUploadedImage(destination.getDestinationId(), ivDestinationCoverPhoto);

            favoriteRepository.getFavorite(destination.getDestinationId(), new OnViewFavoriteCallback() {
                @Override
                public void onSuccess(Favorite favorite) {
                    favoriteAction(favorite);
                    isFavorite = false;
                    btnFavorite.setBackgroundColor(Color.parseColor("#FF0000"));
                }

                @Override
                public void onFailure() {
                    favoriteAction(new Favorite(userAuth.getCurrentUser().getUid(), destination.getDestinationId()));
                    btnFavorite.setBackgroundColor(Color.parseColor("#6750a4"));
                    isFavorite = true;
                }
            });
        }
    }

    private void favoriteAction(Favorite favorite) {
        Log.d(TAG, "Fav " + isFavorite);


        btnFavorite.setOnClickListener(e -> {
            if (isFavorite != true) {
                favoriteRepository.removeFavorite(favorite);
                btnFavorite.setBackgroundColor(Color.parseColor("#6750a4"));
            } else {
                favoriteRepository.addFavorite(favorite);
                btnFavorite.setBackgroundColor(Color.parseColor("#FF0000"));
            }
            isFavorite = !isFavorite;
        });
    }
}
