package io.github.rmmc.rmmctourism.views;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class AddDestination extends Fragment implements WidgetInitializer, ActionInitializer {

    private static final int REQUEST_PERMISSION_CODE = 4283;

    private View view;

    private ImageView ivAddDestinationCover;
    private Button btnUploadCover;

    private List<Uri> urisImg = new ArrayList<>();

    private ActivityResultLauncher<String> selectDestinationCover;
    private ActivityResultLauncher<String> requestPermissionLauncher;

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

        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.GetMultipleContents(),
                uris -> {
                    uris.forEach(uri -> {
                        Log.d("RITCHIE", uri.toString());
                        urisImg.add(uri);
                    });

                    ivAddDestinationCover.setImageURI(urisImg.get(3));
                }
        );

        btnUploadCover.setOnClickListener(e -> {
            requestPermissionLauncher.launch("image/*");
        });
    }

    @Override
    public void initializeWidgets() {
        ivAddDestinationCover = view.findViewById(R.id.iv_add_destination_cover);

        btnUploadCover = view.findViewById(R.id.btn_upload_cover);
    }
}