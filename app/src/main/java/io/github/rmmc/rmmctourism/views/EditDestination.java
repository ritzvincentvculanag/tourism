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
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.UpdateGalleryAdapter;
import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.model.ImageGallery;
import io.github.rmmc.rmmctourism.repository.DestinationRepository;
import io.github.rmmc.rmmctourism.repository.ImageRepository;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.Messenger;
import io.github.rmmc.rmmctourism.util.Miner;
import io.github.rmmc.rmmctourism.util.OnImageLoadListener;
import io.github.rmmc.rmmctourism.util.OnLoadCover;
import io.github.rmmc.rmmctourism.util.Validator;
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
    private DestinationRepository repository;

    private ActivityResultLauncher<String> selectDestinationCover;
    private ActivityResultLauncher<String> selectDestinationImages;
    private ImageRepository imageRepository;
    private FirebaseAuth userAuth;
    private String destinationId = "";
    private Uri newCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_destination);
        imageRepository = new ImageRepository();
        userAuth = FirebaseAuth.getInstance();
        repository = new DestinationRepository(this);
        initializeWidgets();
        initializeActions();
    }

    @Override
    public void initializeActions() {
        selectDestinationCover = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    cover.setImageURI(uri);
                    newCover = uri;

                    cover.setImageURI(newCover);
                }
        );

        selectDestinationImages = registerForActivityResult(
                new ActivityResultContracts.GetMultipleContents(),
                data -> {
                    data.forEach(uri ->{
                        uris.add(uri);
                    });
                    adapter.refreshUris(uris);

                    adapter.notifyDataSetChanged();
                }
        );

        uploadCover.setOnClickListener(e -> selectDestinationCover.launch("image/*"));
        updateGallery.setOnClickListener(e -> selectDestinationImages.launch("image/*"));
        updateDestination.setOnClickListener(this::editDestination);
    }

    @Override
    public void initializeWidgets() {
        cover = findViewById(R.id.iv_edit_destination_cover);

        uris = new ArrayList<>();
        adapter = new UpdateGalleryAdapter(uris, this);
        snapHelper = new LinearSnapHelper();
        gallery = findViewById(R.id.rv_edit_destination_galler);
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
            destinationId = destination.getDestinationId();
            setData(name, destination.getName());
            setData(description, destination.getDescription());
            setData(address, destination.getAddress());
            setData(email, destination.getEmailAddress());
            setData(phone, destination.getContactNumber());
            setData(website, destination.getWebsiteUrl());
            setData(facebook, destination.getFacebookPage());
            setData(instagram, destination.getInstagramPage());
            imageRepository.loadUploadedImage(destination.getDestinationId(), cover, new OnLoadCover() {
                @Override
                public void OnLoad(Uri uri) {
                    coverUri = uri;
                }
            });

            imageRepository.loadGalleryImage(destination.getDestinationId(), new OnImageLoadListener<ImageGallery>() {
                @Override
                public void onImageLoadSuccess(List<ImageGallery> imageUris) {
                    uris.clear(); // Clear existing URIs
                    for (ImageGallery image : imageUris) {
                        uris.add(Uri.parse(image.getUrl()));
                        Log.d(TAG, "url" + image.getUrl());
                    }
                    adapter.refreshUris(uris);
                    gallery.setAdapter(adapter);
                    gallery.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                    snapHelper.attachToRecyclerView(gallery);
                }

                @Override
                public void onImageLoadFailure(Exception e) {
                    // Handle failure if needed
                }
            });
        }
    }

    private void setData(TextInputLayout tf, String data){
        if(data != null){
            tf.getEditText().setText(data);
        } else {
            tf.getEditText().setText("");
        }
    }

    private void editDestination(View view) {
        TextInputLayout fields[] = {name, description, address, email, phone};

        if(cover.getDrawable() == null){
            Messenger.showAlertDialog(this,
                    "Add Destination",
                    "Please select the cover photo of the tourist spot!",
                    "Ok").show();
            return;
        }

        if(Validator.fieldsAreEmpty(fields)){
            Messenger.showAlertDialog(this,
                    "Add Destination",
                    "Please provide the needed information!",
                    "Ok").show();
            return;
        }

        if(!Validator.isValidEmail(email)){
            Messenger.showAlertDialog(this,
                    "Add Destination",
                    "Please provide a valid email!",
                    "Ok").show();
            return;
        }

        if(!Validator.isPhoneNumberValid(phone)){
            Messenger.showAlertDialog(this,
                    "Add Destination",
                    "Please provide a valid number!",
                    "Ok").show();
            return;
        }

        if(!Validator.areAllUrlsValid(website, facebook, instagram)){
            Messenger.showAlertDialog(this,
                    "Add Destination",
                    "Please provide correct url for the social media!",
                    "Ok").show();
            return;
        }

        Destination destination = new Destination(
                destinationId,
                userAuth.getCurrentUser().getUid(),
                Miner.getString(name),
                Miner.getString(description),
                Miner.getString(address),
                Miner.getString(phone),
                Miner.getString(website),
                Miner.getString(facebook),
                Miner.getString(instagram),
                Miner.getString(email),
                Timestamp.now(),
                Timestamp.now()
        );
        repository.updateDestination(destination, coverUri, newCover,uris, cover, getContentResolver(),updateDestination );
    }
}
