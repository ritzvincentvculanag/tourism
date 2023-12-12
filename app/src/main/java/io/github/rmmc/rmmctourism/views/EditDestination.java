package io.github.rmmc.rmmctourism.views;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
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
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class EditDestination extends AppCompatActivity implements WidgetInitializer, ActionInitializer {

    private ImageView cover;

    private Uri coverUri;

    private List<Uri> uris;
    private GalleryAdapter adapter;
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
                uris -> {
                    uris.forEach(uri -> {
                        uris.add(uri);
                        Log.d("RITCHIE_RESULT", uri.toString());
                    });
                    adapter.refreshUris(uris);
                    adapter.notifyDataSetChanged();
                }
        );
    }

    @Override
    public void initializeWidgets() {
        cover = findViewById(R.id.iv_edit_destination_cover);

        uris = new ArrayList<>();
        adapter = new GalleryAdapter(uris);
        snapHelper = new LinearSnapHelper();
        gallery = findViewById(R.id.rv_destination_gallery);

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
    }
}