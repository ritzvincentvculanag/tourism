package io.github.rmmc.rmmctourism.fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.ExploreSearchAdapter;
import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.repository.DestinationRepository;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.DestinationDataCallback;
import io.github.rmmc.rmmctourism.util.Miner;

public class Explore extends Fragment implements ActionInitializer {

    private RecyclerView rvSearchExplore;
    private DestinationRepository destinationRepository;
    private ExploreSearchAdapter exploreSearchAdapter;
    private TextInputLayout tfSearchDestination;
    private List<Destination> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create an instance of DestinationRepository
        destinationRepository = new DestinationRepository();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        // Find the RecyclerView and TextInputLayout in the layout
        rvSearchExplore = view.findViewById(R.id.rv_explore_search);
        tfSearchDestination = view.findViewById(R.id.lit_search_destination);

        // Populate data and set up the RecyclerView
        populateData();

        return view;
    }

    private void populateData() {
        // Fetch destinations from the repository
        destinationRepository.getDestination(new DestinationDataCallback<Destination>() {
            @Override
            public void onDataLoaded(List<Destination> t) {
                // Save the data in a list and create an adapter
                list = t;
                exploreSearchAdapter = new ExploreSearchAdapter(getContext(), list);

                // Set up the RecyclerView with the adapter
                rvSearchExplore.setLayoutManager(new LinearLayoutManager(getActivity()));
                rvSearchExplore.setAdapter(exploreSearchAdapter);

                // Initialize actions for search functionality
                initializeActions();
            }

            @Override
            public void onDataNotAvailable() {
                // Handle the case where data is not available
            }
        });
    }

    @Override
    public void initializeActions() {
        // Add a TextWatcher to the search TextInputLayout
        tfSearchDestination.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not needed for this implementation
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Get the search query
                String query = Miner.getString(tfSearchDestination);
                List<Destination> filterData = new ArrayList<>();
                List<Destination> oldData = new ArrayList<>();
                oldData.addAll(list);

                // Filter the data based on the search query
                if (query.isEmpty()) {
                    filterData.addAll(list);
                } else {
                    for (Destination data : oldData) {
                        if (data.getName().toLowerCase().contains(query.toLowerCase())) {
                            filterData.add(data);
                            Log.d(TAG, data.getName());
                        }
                    }
                }

                // Update the adapter with the filtered data
                exploreSearchAdapter.searchDestination(filterData);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Not needed for this implementation
            }
        });
    }
}
