package io.github.rmmc.rmmctourism.views;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.GalleryAdapter;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class AddDestination extends Fragment implements WidgetInitializer, ActionInitializer {

    private static final int REQUEST_PERMISSION_CODE = 4283;

    private View view;

    private TextView tvDestinationgGalleryIndicator;

    private ImageView ivAddDestinationCover;

    private Button btnUploadCover;
    private Button btnGallerySelectPhotos;

    private List<Uri> imgUris;
    private RecyclerView rvDestinationGallery;
    private GalleryAdapter galleryAdapter;
    private SnapHelper gallerySnapHelper;

    private ActivityResultLauncher<String> selectDestinationCover;
    private ActivityResultLauncher<String> selectDestinationImages;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_destination, container, false);

        initializeWidgets();
        initializeActions();

        return view;
    }

    @Override
    public void initializeActions() {
        selectDestinationCover = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> ivAddDestinationCover.setImageURI(uri)
        );

        selectDestinationImages = registerForActivityResult(
                new ActivityResultContracts.GetMultipleContents(),
                uris -> {
                    uris.forEach(uri -> {
                        imgUris.add(uri);
                        Log.d("RITCHIE_RESULT", uri.toString());
                    });
                    galleryAdapter.refreshUris(imgUris);
                    galleryAdapter.notifyDataSetChanged();

                    tvDestinationgGalleryIndicator.setVisibility(View.INVISIBLE);
                }
        );

        btnUploadCover.setOnClickListener(e -> selectDestinationCover.launch("image/*"));
        btnGallerySelectPhotos.setOnClickListener(e -> selectDestinationImages.launch("image/*"));
    }

    @Override
    public void initializeWidgets() {
        tvDestinationgGalleryIndicator = view.findViewById(R.id.tv_destination_gallery_indicator);

        ivAddDestinationCover = view.findViewById(R.id.iv_add_destination_cover);

        btnUploadCover = view.findViewById(R.id.btn_upload_cover);
        btnGallerySelectPhotos = view.findViewById(R.id.btn_gallery_select_photos);

        initializeDestinationGallery();
    }

    private void initializeDestinationGallery() {
        imgUris = new ArrayList<>();
        gallerySnapHelper = new LinearSnapHelper();
        galleryAdapter = new GalleryAdapter(imgUris);
        rvDestinationGallery = view.findViewById(R.id.rv_destination_gallery);
        rvDestinationGallery.setAdapter(galleryAdapter);
        rvDestinationGallery.setLayoutManager(new LinearLayoutManager(getContext()));

        gallerySnapHelper.attachToRecyclerView(rvDestinationGallery);
    }

}