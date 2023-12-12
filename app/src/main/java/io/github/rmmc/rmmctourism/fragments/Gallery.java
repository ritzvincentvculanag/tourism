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
import io.github.rmmc.rmmctourism.repository.ImageRepository;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.OnImageLoadListener;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class Gallery extends Fragment implements WidgetInitializer, ActionInitializer {

    private View view;

    private GalleryLoadAdapter galleryAdapter;
    private RecyclerView rvGallery;
    private List<String> imgUris;
    private ImageRepository imageRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gallery, container, false);
        imageRepository = new ImageRepository();
        initializeWidgets();
        initializeActions();

        return view;
    }

    @Override
    public void initializeActions() {

    }

    @Override
    public void initializeWidgets() {
        Log.d(TAG, "Model transfer " + (getArguments() != null));
        if (getArguments() != null) {
            Destination destination = getArguments().getParcelable(Destination.collectioName);
            Log.d(TAG, "Model transfer " + destination.getDestinationId());
            imageRepository.loadGalleryImage(destination.getDestinationId(), new OnImageLoadListener<String>() {
                @Override
                public void onImageLoadSuccess(List<String> imageUris) {
                    Log.d(TAG, "Uri loaded" + imageUris.size());
                    galleryAdapter = new GalleryLoadAdapter(imageUris);
                    rvGallery = view.findViewById(R.id.rv_destination_detail_gallery);
                    rvGallery.setAdapter(galleryAdapter);
                    if (imageUris.size() == 3) {
                        rvGallery.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    } else {
                        rvGallery.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                }

                @Override
                public void onImageLoadFailure(Exception e) {

                }
            });

        }

    }

}