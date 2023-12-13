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

import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.ReviewAdapter;
import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.model.Review;
import io.github.rmmc.rmmctourism.model.UserInformation;
import io.github.rmmc.rmmctourism.repository.ReviewRepository;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.OnReviewDataCallback;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class Reviews extends Fragment implements WidgetInitializer, ActionInitializer {

    private View view;

    private ReviewAdapter reviewAdapter;
    private RecyclerView rvReviews;
    private ReviewRepository reviewRepository;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reviews, container, false);
        reviewRepository = new ReviewRepository(getContext());
        initializeWidgets();
        initializeActions();
        
        return view;
    }

    @Override
    public void initializeActions() {
    }

    @Override
    public void initializeWidgets() {
        rvReviews = view.findViewById(R.id.rv_destination_detail_reviews);

        setupReviews();
    }

    private void setupReviews() {

        if (getArguments() != null) {
            Destination destination = getArguments().getParcelable(Destination.collectioName);
            reviewRepository.getReview(destination, new OnReviewDataCallback<Review>() {
                @Override
                public void onSuccess(List<Review> reviews) {
                    reviewRepository.getFullName(new OnReviewDataCallback<UserInformation>() {
                        @Override
                        public void onSuccess(List<UserInformation> userInformations) {
                            reviewAdapter = new ReviewAdapter(reviews, userInformations);
                            rvReviews.setAdapter(reviewAdapter);
                            rvReviews.setLayoutManager(new LinearLayoutManager(getContext()));
                        }

                        @Override
                        public void onFailure() {

                        }
                    });

                }
                @Override
                public void onFailure() {

                }
            });

        }

    }

}