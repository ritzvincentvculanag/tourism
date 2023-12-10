package io.github.rmmc.rmmctourism.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.DestinationAdapter;
import io.github.rmmc.rmmctourism.repository.DestinationRepository;
import io.github.rmmc.rmmctourism.util.DestinationDataCallback;
import io.github.rmmc.rmmctourism.util.OnDestinationClick;
import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.views.DestinationInformation;
public class DestinationsView extends Fragment implements OnDestinationClick {
    private RecyclerView rvSpotViewer;
    private List<Destination> destinations;
    private DestinationAdapter destinationAdapter;
    private DestinationRepository destinationRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spot_viewer, container, false);

        destinations = new ArrayList<>();
        destinationAdapter= new DestinationAdapter(getContext(), this, destinations);
        rvSpotViewer = view.findViewById(R.id.rv_spot_viewer);
        rvSpotViewer.setAdapter(destinationAdapter);
        rvSpotViewer.setLayoutManager(new LinearLayoutManager(getContext()));

        setupDestinations();

        return view;
    }

    @Override
    public void onDestinationClick(int position) {
        Destination destination = destinations.get(position);
        Intent viewDestination = new Intent(getContext(), DestinationInformation.class);
        viewDestination.putExtra(Destination.collectioName, destination);
        startActivity(viewDestination);
    }

    private void setupDestinations() {
        destinationRepository = new DestinationRepository(getContext().getApplicationContext());
        destinationRepository.getDestination(new DestinationDataCallback<Destination>() {
            @Override
            public void onDataLoaded(List<Destination> t) {
                destinations.clear();
                for (Destination data: t){
                    destinations.add(data);
                }
                destinationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onDataNotAvailable() {
            }
        });
    }
}