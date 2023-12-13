package io.github.rmmc.rmmctourism.views;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Context;
import android.content.Intent;
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
import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.model.ImageGallery;
import io.github.rmmc.rmmctourism.repository.ImageRepository;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.OnImageLoadListener;
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
    private ImageRepository imageRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_destination);
        imageRepository = new ImageRepository();
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
                        List<Uri> newUris = new ArrayList<>(uris);
                        newUris.addAll(result);

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
        populateData();
    }

    private void populateData(){
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Destination.collectioName)){
            Destination destination = intent.getParcelableExtra(Destination.collectioName);
            setData(name, destination.getName());
            setData(description, destination.getDescription());
            setData(address, destination.getAddress());
            setData(email, destination.getEmailAddress());
            setData(phone, destination.getContactNumber());
            setData(website, destination.getWebsiteUrl());
            setData(facebook, destination.getFacebookPage());
            setData(instagram, destination.getInstagramPage());
            imageRepository.loadUploadedImage(destination.getDestinationId(), cover);

            imageRepository.loadGalleryImage(destination.getDestinationId(), new OnImageLoadListener<ImageGallery>() {
                @Override
                public void onImageLoadSuccess(List<ImageGallery> imageUris) {
                    Context context = getBaseContext();
                    for (ImageGallery image: imageUris){
                        uris.add(Uri.parse(image.getUrl()));
                        Log.d(TAG, "url"+ image.getUrl());
                    }
                    UpdateGalleryAdapter galleryAdapter;
                    galleryAdapter = new UpdateGalleryAdapter(uris, context);
                    galleryAdapter.refreshUris(uris);
                    galleryAdapter.notifyDataSetChanged();
                }

                @Override
                public void onImageLoadFailure(Exception e) {

                }
            });
        }
    }
    private void setData(TextInputLayout tf, String data){
        if(data != null){
            tf.getEditText().setText(data);
        }else{
            tf.getEditText().setText("");
        }
    }
}