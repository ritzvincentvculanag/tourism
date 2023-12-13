package io.github.rmmc.rmmctourism.views;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.GalleryAdapter;
import io.github.rmmc.rmmctourism.adapter.UpdateGalleryAdapter;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class EditDestination extends AppCompatActivity implements WidgetInitializer, ActionInitializer {

    private ImageView cover;

    private Uri coverUri;

    private List<Uri> uris;
    private UpdateGalleryAdapter adapter;
    private SnapHelper snapHelper;
    private RecyclerView gallery;

    private TextInputLayout name;
    private TextInputLayout description;
    private TextInputLayout address;
    private TextInputLayout email;
    private TextInputLayout phone;
    private TextInputLayout website;
    private TextInputLayout facebook;
    private TextInputLayout instagram;

    private Button uploadCover;
    private Button updateDestination;
    private Button updateGallery;

    private ActivityResultLauncher<String> selectDestinationCover;
    private ActivityResultLauncher<String> selectDestinationImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_destination);

        initializeWidgets();
        initializeActions();
    }

    @Override
    public void initializeActions() {
        selectDestinationCover = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    cover.setImageURI(uri);
                    coverUri = uri;
                }
        );

        selectDestinationImages = registerForActivityResult(
                new ActivityResultContracts.GetMultipleContents(),
                new ActivityResultCallback<List<Uri>>() {
                    @Override
                    public void onActivityResult(List<Uri> result) {
                        List<Uri> newUris = new ArrayList<>(uris); // Create a new list to store added elements
                        newUris.addAll(result); // Add new elements to the new list

                        // Update the original list with the new elements
                        uris.clear();
                        uris.addAll(newUris);

                        adapter.refreshUris(uris);
                        adapter.notifyDataSetChanged();
                    }
                }
        );
        uploadCover.setOnClickListener(e -> selectDestinationCover.launch("image/*"));
        updateGallery.setOnClickListener(e -> selectDestinationImages.launch("image/*"));
    }

    @Override
    public void initializeWidgets() {
        cover = findViewById(R.id.iv_edit_destination_cover);

        uris = new ArrayList<>();
        adapter = new UpdateGalleryAdapter(uris, this);
        snapHelper = new LinearSnapHelper();
        gallery = findViewById(R.id.rv_edit_destination_galler);
        gallery.setAdapter(adapter);
        gallery.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        snapHelper.attachToRecyclerView(gallery);

        name = findViewById(R.id.til_edit_destination_name);
        description = findViewById(R.id.til_edit_destination_description);
        address = findViewById(R.id.til_edit_destination_address);
        email = findViewById(R.id.til_edit_destination_email);
        phone = findViewById(R.id.til_edit_destination_phone);
        website = findViewById(R.id.til_edit_destination_website);
        facebook = findViewById(R.id.til_edit_destination_facebook);
        instagram = findViewById(R.id.til_edit_destination_instagram);

        uploadCover = findViewById(R.id.btn_edit_destination_cover);
        updateDestination = findViewById(R.id.btn_update_destination);
        updateGallery = findViewById(R.id.btn_edit_destination_photos);
    }
}