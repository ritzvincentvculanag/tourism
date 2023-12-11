package io.github.rmmc.rmmctourism.fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.ExploreAdapter;
import io.github.rmmc.rmmctourism.adapter.ExploreSearchAdapter;
import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.repository.DestinationRepository;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.DestinationDataCallback;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class Explore extends Fragment {

    private RecyclerView rvSearchExplore;
    private DestinationRepository destinationRepository;
    private ExploreSearchAdapter exploreSearchAdapter;
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
                Log.d(TAG, "Loaded");

            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }



}