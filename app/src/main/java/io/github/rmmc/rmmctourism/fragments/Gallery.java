package io.github.rmmc.rmmctourism.fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.GalleryAdapter;
import io.github.rmmc.rmmctourism.adapter.viewpager.GalleryLoadAdapter;
import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.model.ImageGallery;
import io.github.rmmc.rmmctourism.repository.ImageRepository;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.OnImageLoadListener;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class Gallery extends Fragment implements WidgetInitializer, ActionInitializer {

    private View view;

    private GalleryLoadAdapter galleryAdapter;
    private RecyclerView rvGallery;
    private ImageRepository imageRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_gallery, container, false);

        // Create an instance of ImageRepository
        imageRepository = new ImageRepository();

        // Initialize widgets and actions
        initializeWidgets();
        initializeActions();

        return view;
    }

    @Override
    public void initializeActions() {
        // No actions to initialize in this fragment
    }

    @Override
    public void initializeWidgets() {
        Log.d(TAG, "Model transfer " + (getArguments() != null));

        // Check if arguments are not null
        if (getArguments() != null) {
            // Retrieve the Destination object from arguments
            Destination destination = getArguments().getParcelable(Destination.collectioName);
            Log.d(TAG, "Model transfer " + destination.getDestinationId());

            // Load gallery images for the destination
            imageRepository.loadGalleryImage(destination.getDestinationId(), new OnImageLoadListener<ImageGallery>() {
                @Override
                public void onImageLoadSuccess(List<ImageGallery> imageUris) {
                    Log.d(TAG, "Uri loaded" + imageUris.size());

                    // Create a GalleryLoadAdapter with the loaded image URIs
                    galleryAdapter = new GalleryLoadAdapter(imageUris);

                    // Find the RecyclerView in the layout
                    rvGallery = view.findViewById(R.id.rv_destination_detail_gallery);

                    // Set the adapter and layout manager for the RecyclerView
                    rvGallery.setAdapter(galleryAdapter);
                    if (imageUris.size() == 3) {
                        rvGallery.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    } else {
                        rvGallery.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                }

                @Override
                public void onImageLoadFailure(Exception e) {
                    // Handle failure to load images
                }
            });
        }
    }
}
