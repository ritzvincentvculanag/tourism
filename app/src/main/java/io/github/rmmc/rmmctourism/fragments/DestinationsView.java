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
import io.github.rmmc.rmmctourism.adapter.OnDestinationClick;
import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.views.DestinationInformation;


public class DestinationsView extends Fragment implements OnDestinationClick {

    private RecyclerView rvSpotViewer;
    private List<Destination> destinations;
    private DestinationAdapter destinationAdapter;

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
        viewDestination.putExtra(Destination.OBJ_DESTINATION, destination);
        startActivity(viewDestination);
    }

    private void setupDestinations() {
        destinations.add(new Destination("Breads and Blends", "The best coffee shop in South Cotabato"));
        destinations.add(new Destination("Koronadal City", "Koronadal City Description."));
        destinations.add(new Destination("Tupi", "Tupi South Cotabato Description."));
        destinations.add(new Destination("KCC Mall of Marbel", "The closes mall in Tacurong City"));
        destinations.add(new Destination("Gaisano Mall of Marble", "The second closest mall in Tacurong"));
        destinations.add(new Destination("STI College Koronadal", "The best IT school in Koronadal City"));
    }
}