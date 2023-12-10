package io.github.rmmc.rmmctourism.views;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class AddDestination extends Fragment implements WidgetInitializer, ActionInitializer {

    private View view;

    private ImageView ivAddDestinationCover;
    private Button btnUploadCover;

    private ActivityResultLauncher<String> arlTakePhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_destination, container, false);

        initializeWidgets();
        initializeActions();

        return view;
    }

    @Override
    public void initializeActions() {
        arlTakePhoto = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> ivAddDestinationCover.setImageURI(uri)
        );

        btnUploadCover.setOnClickListener(e -> arlTakePhoto.launch("image/*"));
    }

    @Override
    public void initializeWidgets() {
        ivAddDestinationCover = view.findViewById(R.id.iv_add_destination_cover);

        btnUploadCover = view.findViewById(R.id.btn_upload_cover);
    }
}