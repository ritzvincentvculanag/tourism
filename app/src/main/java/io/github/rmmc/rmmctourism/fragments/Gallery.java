package io.github.rmmc.rmmctourism.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.GalleryAdapter;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class Gallery extends Fragment implements WidgetInitializer, ActionInitializer {

    private View view;

    private GalleryAdapter galleryAdapter;
    private RecyclerView rvGallery;
    private List<Uri> imgUris;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gallery, container, false);

        initializeWidgets();
        initializeActions();

        return view;
    }

    @Override
    public void initializeActions() {

    }

    @Override
    public void initializeWidgets() {
        imgUris = new ArrayList<>();
        galleryAdapter = new GalleryAdapter(imgUris);
        rvGallery = view.findViewById(R.id.rv_destination_detail_gallery);
        rvGallery.setAdapter(galleryAdapter);
        rvGallery.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }
}