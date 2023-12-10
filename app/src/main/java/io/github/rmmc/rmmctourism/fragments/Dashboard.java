package io.github.rmmc.rmmctourism.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.ExploreAdapter;
import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.repository.DestinationRepository;
import io.github.rmmc.rmmctourism.util.DestinationDataCallback;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;


public class Dashboard extends Fragment{

    private RecyclerView rvDashboard;
    private ExploreAdapter exploreAdapter;
    private DestinationRepository destinationRepository;
    private List<Destination> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        destinationRepository = new DestinationRepository();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        rvDashboard = view.findViewById(R.id.rv_dashboard_destination);
        populateData();
        return view;
    }
    private void populateData(){
        destinationRepository.getDestination(new DestinationDataCallback<Destination>() {
            @Override
            public void onDataLoaded(List<Destination> t) {
                list = t;
                exploreAdapter = new ExploreAdapter(getContext(), list);
                rvDashboard.setLayoutManager(new LinearLayoutManager(getActivity()));
                rvDashboard.setAdapter(exploreAdapter);

            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}