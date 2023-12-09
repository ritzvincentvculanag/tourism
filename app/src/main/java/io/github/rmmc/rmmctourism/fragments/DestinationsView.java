package io.github.rmmc.rmmctourism.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.DestinationAdapter;
import io.github.rmmc.rmmctourism.util.OnDestinationClick;
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
        viewDestination.putExtra(Destination.documentNameField, destination);
        startActivity(viewDestination);
    }

    private void setupDestinations() {
        destinations.add(new Destination(
                "Breads and Blends",
                "The best coffee shop in South Cotabato",
                "user_id_1",
                "category_id_1",
                "Breads and Blends Description",
                "123 Main St, City",
                "123456789",
                "http://www.breadsandblends.com",
                "http://www.facebook.com/breadsandblends",
                "http://www.instagram.com/breadsandblends",
                "info@breadsandblends.com",
                null, null
        ));

        destinations.add(new Destination(
                "Koronadal City",
                "Koronadal City Description.",
                "user_id_2",
                "category_id_2",
                "Koronadal City Address",
                "987 City Ave, Koronadal",
                "987654321",
                "http://www.koronadalcity.com",
                "http://www.facebook.com/koronadalcity",
                "http://www.instagram.com/koronadalcity",
                "info@koronadalcity.com",
                null, null
        ));

        destinations.add(new Destination(
                "Tupi",
                "Tupi South Cotabato Description.",
                "user_id_3",
                "category_id_3",
                "Tupi Address",
                "456 Tupi St, Tupi",
                "456789012",
                "http://www.tupi.com",
                "http://www.facebook.com/tupi",
                "http://www.instagram.com/tupi",
                "info@tupi.com",
                null, null
        ));
    }
}