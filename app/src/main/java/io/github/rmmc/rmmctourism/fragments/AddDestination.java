package io.github.rmmc.rmmctourism.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.GalleryAdapter;
import io.github.rmmc.rmmctourism.adapter.SpinnerAdapter;
import io.github.rmmc.rmmctourism.model.CItyBarangayData;
import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.repository.DestinationRepository;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.Messenger;
import io.github.rmmc.rmmctourism.util.Miner;
import io.github.rmmc.rmmctourism.util.Validator;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

/**
 * Fragment for adding a new destination.
 */
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
    private AutoCompleteTextView actvCity, actvBarangay;
    private DestinationRepository repository;
    private FirebaseAuth userAuth;

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_destination, container, false);

        initializeWidgets();
        initializeActions();
        initializeSpinner();
        repository = new DestinationRepository(getContext());
        userAuth = FirebaseAuth.getInstance();
        return view;
    }

    /**
     * Initializes actions for the fragment. This method should be overridden to set up event listeners or other actions.
     */
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
                    });
                    galleryAdapter.refreshUris(imgUris);
                    galleryAdapter.notifyDataSetChanged();
                }
        );

        btnUploadCover.setOnClickListener(e -> selectDestinationCover.launch("image/*"));
        btnGallerySelectPhotos.setOnClickListener(e -> selectDestinationImages.launch("image/*"));

        btnAddDestination.setOnClickListener(this::addDestination);
    }

    /**
     * Initializes widgets in the fragment. This method should be overridden to find and set up UI elements.
     */
    @Override
    public void initializeWidgets() {

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
        actvCity = view.findViewById(R.id.actv_city);
        actvBarangay = view.findViewById(R.id.actv_brgy);

        initializeDestinationGallery();
    }

    /**
     * Initializes the destination gallery RecyclerView and its associated components.
     */
    private void initializeDestinationGallery() {
        imgUris = new ArrayList<>();
        gallerySnapHelper = new LinearSnapHelper();
        galleryAdapter = new GalleryAdapter(imgUris, getContext());
        rvDestinationGallery = view.findViewById(R.id.rv_destination_gallery);
        rvDestinationGallery.setAdapter(galleryAdapter);
        rvDestinationGallery.setLayoutManager(new LinearLayoutManager(getContext()));

        gallerySnapHelper.attachToRecyclerView(rvDestinationGallery);
    }

    private void initializeSpinner() {

        // Set up the city spinner
        ArrayAdapter<String> cityAdapter = new SpinnerAdapter<String>().GetArrayAdapter(
                getContext(),
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                Arrays.asList(CItyBarangayData.cityList)
        );
        actvCity.setAdapter(cityAdapter);

        actvCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateBarangaySpinner(actvCity.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void updateBarangaySpinner(String selectedCity) {

        // Get the barangays for the selected city
        String[] barangays = CItyBarangayData.brgyList().get(selectedCity);

        if(barangays == null){
            return;
        }

        actvBarangay.setText("");
        // Set up the barangay spinner
        ArrayAdapter<String> brgyAdapter = new SpinnerAdapter<String>().GetArrayAdapter(
                getContext(),
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                Arrays.asList(barangays)
        );
        actvBarangay.setAdapter(brgyAdapter);

    }

    private void addDestination(View view) {
        TextInputLayout fields[] = {tilAddDestinationName, tilAddDestinationDescription, tilAddDestinationAddress, tilAddDestinationEmail, tilAddDestinationPhone};

        if (ivAddDestinationCover.getDrawable() == null) {
            Messenger.showAlertDialog(getContext(),
                    "Add Destination",
                    "Please select the cover photo of the tourist spot!",
                    "Ok").show();
            return;
        }

        if (Validator.fieldsAreEmpty(fields)) {
            Messenger.showAlertDialog(getContext(),
                    "Add Destination",
                    "Please provide all required details (Name, Description, Address, Email, Phone) to add a destination. This information is necessary for processing.",
                    "Ok").show();
            return;
        }

        if(actvCity.getText().toString().isEmpty()){
            Messenger.showAlertDialog(getContext(),
                    "Add Destination",
                    "Please select the city.",
                    "Ok").show();
            return;
        }

        if(actvBarangay.getText().toString().isEmpty()){
            Messenger.showAlertDialog(getContext(),
                    "Add Destination",
                    "Please select the barangay.",
                    "Ok").show();
            return;
        }

        if (!Validator.isValidEmail(tilAddDestinationEmail)) {
            Messenger.showAlertDialog(getContext(),
                    "Add Destination",
                    "Please provide a valid email!",
                    "Ok").show();
            return;
        }

        if (!Validator.isPhoneNumberValid(tilAddDestinationPhone)) {
            Messenger.showAlertDialog(getContext(),
                    "Add Destination",
                    "Please provide a valid number!",
                    "Ok").show();
            return;
        }

        if (!Validator.areAllUrlsValid(tilAddDestinationWebsite, tilAddDestinationFacebook, tilAddDestinationInstagram)) {
            Messenger.showAlertDialog(getContext(),
                    "Add Destination",
                    "Please provide correct URLs for the social media!",
                    "Ok").show();
            return;
        }
        if (coverUri == null) {
            Messenger.showAlertDialog(getContext(),
                    "Add Destination",
                    "Please upload a cover photo!",
                    "Ok").show();
            return;
        }

        if (coverUri == null) {
            Messenger.showAlertDialog(getContext(),
                    "Add Destination",
                    "Please upload a image for gallery!",
                    "Ok").show();
            return;
        }

        Destination destination = new Destination(
                userAuth.getCurrentUser().getUid(),
                Miner.getString(tilAddDestinationName),
                Miner.getString(tilAddDestinationDescription),
                Miner.getString(tilAddDestinationAddress),
                actvCity.getText().toString(),
                actvBarangay.getText().toString(),
                Miner.getString(tilAddDestinationPhone),
                Miner.getString(tilAddDestinationWebsite),
                Miner.getString(tilAddDestinationFacebook),
                Miner.getString(tilAddDestinationInstagram),
                Miner.getString(tilAddDestinationEmail),
                Timestamp.now(),
                Timestamp.now()
        );

        repository.addDestination(destination, coverUri, imgUris, ivAddDestinationCover, requireContext().getContentResolver(), btnAddDestination);
    }
}
