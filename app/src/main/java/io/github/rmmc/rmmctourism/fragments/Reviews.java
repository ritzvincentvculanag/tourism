package io.github.rmmc.rmmctourism.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.ReviewAdapter;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class Reviews extends Fragment implements WidgetInitializer, ActionInitializer {

    private View view;
    private View dialogView;

    private ExtendedFloatingActionButton efabAddReview;

    private ReviewAdapter reviewAdapter;
    private RecyclerView rvReviews;

    private BottomSheetDialog addReview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reviews, container, false);
        dialogView = getLayoutInflater().inflate(R.layout.layout_add_review, null, false);

        initializeWidgets();
        initializeActions();
        
        return view;
    }

    @Override
    public void initializeActions() {
        efabAddReview.setOnClickListener(this::btnAddReviewAction);
    }

    @Override
    public void initializeWidgets() {
        efabAddReview = view.findViewById(R.id.fav_destination_detail_add_review);
        rvReviews = view.findViewById(R.id.rv_destination_detail_reviews);
        addReview = new BottomSheetDialog(getContext());

        setupReviews();
        initializeDialog();
    }

    private void initializeDialog() {
        TextInputLayout tilContent = dialogView.findViewById(R.id.til_add_review_content);
        Button btnSubmit = dialogView.findViewById(R.id.btn_submit_review);
        btnSubmit.setOnClickListener(this::btnSubmitAction);
    }

    private void setupReviews() {
        reviewAdapter = new ReviewAdapter();
        rvReviews.setAdapter(reviewAdapter);
        rvReviews.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void btnSubmitAction(View view) {
        addReview.dismiss();
    }

    private void btnAddReviewAction(View view) {
        addReview.show();
    }

}