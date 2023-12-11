package io.github.rmmc.rmmctourism.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.viewpager.DetailAdapter;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class DestinationDetail extends AppCompatActivity implements WidgetInitializer, ActionInitializer {

    private View dialogView;

    private ExtendedFloatingActionButton efabAddReview;

    private BottomSheetDialog addReview;

    private DetailAdapter detailAdapter;
    private ViewPager2 vpDestinationDetails;
    private TabLayout tlDestinationDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_detail);

        initializeWidgets();
        initializeActions();
    }

    @Override
    public void initializeActions() {
        efabAddReview.setOnClickListener(this::btnAddReviewAction);
    }

    @Override
    public void initializeWidgets() {
        addReview = new BottomSheetDialog(DestinationDetail.this);
        efabAddReview = findViewById(R.id.fav_destination_detail_add_review);
        dialogView = getLayoutInflater().inflate(R.layout.layout_add_review, null, false);


        detailAdapter = new DetailAdapter(this);

        tlDestinationDetails = findViewById(R.id.tl_destination_details);
        vpDestinationDetails = findViewById(R.id.vp_destination_details);
        vpDestinationDetails.setAdapter(detailAdapter);

        initializeViewPager();
        initializeDialog();
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

    private void initializeDialog() {
        TextInputLayout tilContent = dialogView.findViewById(R.id.til_add_review_content);
        Button btnSubmit = dialogView.findViewById(R.id.btn_submit_review);
        btnSubmit.setOnClickListener(this::btnSubmitAction);
    }

    private void btnSubmitAction(View view) {
        addReview.dismiss();
    }

    private void btnAddReviewAction(View view) {
        addReview.show();
    }

}

