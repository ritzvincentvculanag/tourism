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
import io.github.rmmc.rmmctourism.adapter.ExploreSearchAdapter;
import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.model.Favorite;
import io.github.rmmc.rmmctourism.repository.DestinationRepository;
import io.github.rmmc.rmmctourism.repository.FavoriteRepository;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.DestinationDataCallback;
import io.github.rmmc.rmmctourism.util.OnDestinationClick;
import io.github.rmmc.rmmctourism.util.OnFavoriteDataCallback;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class Favorites extends Fragment implements WidgetInitializer {

    private View view;

    private ExploreSearchAdapter adapter;
    private DestinationRepository destinationRepository;
    private FavoriteRepository favoriteRepository;
    private RecyclerView favorites;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favorites, container, false);

        // Initialize repositories
        destinationRepository = new DestinationRepository();
        favoriteRepository = new FavoriteRepository(getContext());

        // Initialize widgets
        initializeWidgets();

        return view;
    }

    @Override
    public void initializeWidgets() {
        // Fetch favorite destinations from the repository
        favoriteRepository.getFavorite(new OnFavoriteDataCallback() {
            @Override
            public void onSuccess(List<Favorite> list) {
                // Fetch detailed destination information for each favorite
                destinationRepository.getDestination(list, new DestinationDataCallback<Destination>() {
                    @Override
                    public void onDataLoaded(List<Destination> t) {
                        // Create an ExploreSearchAdapter with the fetched destinations
                        adapter = new ExploreSearchAdapter(getContext(), t);

                        // Find the RecyclerView in the layout
                        favorites = view.findViewById(R.id.rv_favorites);

                        // Set the adapter and layout manager for the RecyclerView
                        favorites.setAdapter(adapter);
                        favorites.setLayoutManager(new LinearLayoutManager(getContext()));
                    }

                    @Override
                    public void onDataNotAvailable() {
                        // Handle the case where destination data is not available
                    }
                });
            }

            @Override
            public void onFailure(Exception exception) {
                // Handle the case where there is a failure in fetching favorite data
            }
        });
    }
}
