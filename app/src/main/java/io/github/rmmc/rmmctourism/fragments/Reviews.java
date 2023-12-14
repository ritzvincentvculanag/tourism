package io.github.rmmc.rmmctourism.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reviews, container, false);

        // Create an instance of ReviewRepository
        reviewRepository = new ReviewRepository(getContext());

        // Initialize widgets and actions
        initializeWidgets();
        initializeActions();

        return view;
    }

    @Override
    public void initializeActions() {
        // Actions initialization, if any, can be added here
    }

    @Override
    public void initializeWidgets() {
        // Find the RecyclerView in the layout
        rvReviews = view.findViewById(R.id.rv_destination_detail_reviews);

        // Set up the reviews in the RecyclerView
        setupReviews();
    }

    private void setupReviews() {
        // Check if arguments are not null
        if (getArguments() != null) {
            // Retrieve the Destination object from arguments
            Destination destination = getArguments().getParcelable(Destination.collectioName);

            // Fetch reviews for the given destination
            reviewRepository.getReview(destination, new OnReviewDataCallback<Review>() {
                @Override
                public void onSuccess(List<Review> reviews) {
                    // Once reviews are retrieved, fetch user information for each review
                    reviewRepository.getFullName(new OnReviewDataCallback<UserInformation>() {
                        @Override
                        public void onSuccess(List<UserInformation> userInformations) {
                            // Create a ReviewAdapter with reviews and user information
                            reviewAdapter = new ReviewAdapter(reviews, userInformations);

                            // Set the adapter and layout manager for the RecyclerView
                            rvReviews.setAdapter(reviewAdapter);
                            rvReviews.setLayoutManager(new LinearLayoutManager(getContext()));
                        }

                        @Override
                        public void onFailure() {
                            // Handle failure to fetch user information
                        }
                    });
                }

                @Override
                public void onFailure() {
                    // Handle failure to fetch reviews
                }
            });
        }
    }
}
