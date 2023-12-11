package io.github.rmmc.rmmctourism.fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.ExploreAdapter;
import io.github.rmmc.rmmctourism.adapter.ExploreSearchAdapter;
import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.repository.DestinationRepository;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.DestinationDataCallback;
import io.github.rmmc.rmmctourism.util.Miner;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class Explore extends Fragment implements ActionInitializer{

    private RecyclerView rvSearchExplore;
    private DestinationRepository destinationRepository;
    private ExploreSearchAdapter exploreSearchAdapter;
    private TextInputLayout tfSearchDestination;
    private List<Destination> list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        destinationRepository = new DestinationRepository();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        rvSearchExplore = view.findViewById(R.id.rv_explore_search);
        tfSearchDestination = view.findViewById(R.id.lit_search_destination);
        populateData();

        return view;
    }
    private void populateData(){
        destinationRepository.getDestination(new DestinationDataCallback<Destination>() {
            @Override
            public void onDataLoaded(List<Destination> t) {
                list = t;
                exploreSearchAdapter = new ExploreSearchAdapter(getContext(), list);
                rvSearchExplore.setLayoutManager(new LinearLayoutManager(getActivity()));
                rvSearchExplore.setAdapter(exploreSearchAdapter);
                initializeActions();
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void initializeActions() {
        tfSearchDestination.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = Miner.getString(tfSearchDestination);
                List<Destination> filterData = new ArrayList<>();
                List<Destination> oldData = new ArrayList<>();
                oldData.addAll(list);
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
                exploreSearchAdapter.searchDestination(filterData);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}