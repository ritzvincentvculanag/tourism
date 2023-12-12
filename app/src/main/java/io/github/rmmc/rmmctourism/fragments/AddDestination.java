package io.github.rmmc.rmmctourism.fragments;

import android.net.Uri;
import android.os.Bundle;

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

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.GalleryAdapter;
import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.repository.DestinationRepository;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.Messenger;
import io.github.rmmc.rmmctourism.util.Miner;
import io.github.rmmc.rmmctourism.util.Validator;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class AddDestination extends Fragment implements WidgetInitializer, ActionInitializer {

    private static final int REQUEST_PERMISSION_CODE = 4283;

    private View view;

    private TextView tvDestinationGalleryIndicator;
    private ImageView ivAddDestinationCover;

    private Button btnUploadCover;
    private Button btnGallerySelectPhotos;
    private Button btnAddDestination;

    private Uri coverUri;
    private List<Uri> imgUris;
    private RecyclerView rvDestinationGallery;
    private GalleryAdapter galleryAdapter;
    private SnapHelper gallerySnapHelper;

    private ActivityResultLauncher<String> selectDestinationCover;
    private ActivityResultLauncher<String> selectDestinationImages;

    private TextInputLayout tilAddDestinationName;
    private TextInputLayout tilAddDestinationDescription;
    private TextInputLayout tilAddDestinationAddress;
    private TextInputLayout tilAddDestinationEmail;
    private TextInputLayout tilAddDestinationPhone;
    private TextInputLayout tilAddDestinationWebsite;
    private TextInputLayout tilAddDestinationFacebook;
    private TextInputLayout tilAddDestinationInstagram;
    private DestinationRepository repository;
    private FirebaseAuth userAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_destination, container, false);

        initializeWidgets();
        initializeActions();
        repository = new DestinationRepository(getContext());
        userAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void initializeActions() {
        selectDestinationCover = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    ivAddDestinationCover.setImageURI(uri);
                    coverUri = uri;
                }
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

                    tvDestinationGalleryIndicator.setVisibility(View.INVISIBLE);
                }
        );

        btnUploadCover.setOnClickListener(e -> selectDestinationCover.launch("image/*"));
        btnGallerySelectPhotos.setOnClickListener(e -> selectDestinationImages.launch("image/*"));
        btnAddDestination.setOnClickListener(this::addDestination);
    }

    @Override
    public void initializeWidgets() {
        tvDestinationGalleryIndicator = view.findViewById(R.id.tv_destination_gallery_indicator);

        ivAddDestinationCover = view.findViewById(R.id.iv_add_destination_cover);

        btnUploadCover = view.findViewById(R.id.btn_upload_cover);
        btnGallerySelectPhotos = view.findViewById(R.id.btn_add_destination_select_photos);
        btnAddDestination = view.findViewById(R.id.btn_add_destination);

        tilAddDestinationName = view.findViewById(R.id.til_add_destination_name);
        tilAddDestinationDescription = view.findViewById(R.id.til_add_destination_description);
        tilAddDestinationAddress = view.findViewById(R.id.til_add_destination_address);
        tilAddDestinationEmail = view.findViewById(R.id.til_add_destination_email);
        tilAddDestinationPhone = view.findViewById(R.id.til_add_destination_phone);
        tilAddDestinationWebsite = view.findViewById(R.id.til_add_destination_website);
        tilAddDestinationFacebook = view.findViewById(R.id.til_add_destination_facebook);
        tilAddDestinationInstagram = view.findViewById(R.id.til_add_destination_instagram);

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

    private void addDestination(View view) {

        TextInputLayout fields[] = {tilAddDestinationName, tilAddDestinationDescription, tilAddDestinationAddress, tilAddDestinationEmail, tilAddDestinationPhone};

        if(ivAddDestinationCover.getDrawable() == null){
            Messenger.showAlertDialog(getContext(),
                    "Add Destination",
                    "Please select the cover photo of the tourist spot!",
                    "Ok").show();
            return;
        }

        if(Validator.fieldsAreEmpty(fields)){
            Messenger.showAlertDialog(getContext(),
                    "Add Destination",
                    "Please provide the needed information!",
                    "Ok").show();
            return;
        }

        if(!Validator.isValidEmail(tilAddDestinationEmail)){
            Messenger.showAlertDialog(getContext(),
                    "Add Destination",
                    "Please provide a valid email!",
                    "Ok").show();
            return;
        }

        if(!Validator.isPhoneNumberValid(tilAddDestinationPhone)){
            Messenger.showAlertDialog(getContext(),
                    "Add Destination",
                    "Please provide a valid number!",
                    "Ok").show();
            return;
        }

        if(!Validator.areAllUrlsValid(tilAddDestinationWebsite, tilAddDestinationFacebook, tilAddDestinationInstagram)){
            Messenger.showAlertDialog(getContext(),
                    "Add Destination",
                    "Please provide correct url for the social media!",
                    "Ok").show();
            return;
        }

        Destination destination = new Destination(
                userAuth.getCurrentUser().getUid(),
                "Gg7QZQgunepHC2DQaiMQ",
                Miner.getString(tilAddDestinationName),
                Miner.getString(tilAddDestinationDescription),
                Miner.getString(tilAddDestinationAddress),
                Miner.getString(tilAddDestinationPhone),
                Miner.getString(tilAddDestinationWebsite),
                Miner.getString(tilAddDestinationFacebook),
                Miner.getString(tilAddDestinationInstagram),
                Miner.getString(tilAddDestinationEmail),
                Timestamp.now(),
                Timestamp.now()
        );

        repository.addDestination(destination, coverUri, imgUris, ivAddDestinationCover, requireContext().getContentResolver());
    }


}