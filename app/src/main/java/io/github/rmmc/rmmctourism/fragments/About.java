package io.github.rmmc.rmmctourism.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

/**
 * Fragment representing information about a destination.
 */
public class About extends Fragment implements WidgetInitializer, ActionInitializer {

    private View view;

    private TextView tvDescription;
    private TextView tvPhone;
    private TextView tvEmail;
    private TextView tvFacebook;
    private TextView tvInstagram;

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
        view = inflater.inflate(R.layout.fragment_about, container, false);

        initializeWidgets();
        initializeActions();

        return view;
    }

    /**
     * Initializes actions for the fragment. This method should be overridden to set up event listeners or other actions.
     */
    @Override
    public void initializeActions() {
        // Add actions here if needed
    }

    /**
     * Initializes widgets in the fragment. This method should be overridden to find and set up UI elements.
     */
    @Override
    public void initializeWidgets() {
        tvDescription = view.findViewById(R.id.tv_destination_detail_description);
        tvPhone = view.findViewById(R.id.tv_destination_detail_phone);
        tvEmail = view.findViewById(R.id.tv_destination_detail_email);
        tvFacebook = view.findViewById(R.id.tv_destination_detail_facebook);
        tvInstagram = view.findViewById(R.id.tv_destination_detail_instagram);
        populateData();
    }

    /**
     * Populates the UI elements with data from the destination object.
     */
    private void populateData() {
        if (getArguments() != null) {
            Destination destination = getArguments().getParcelable(Destination.collectioName);
            tvDescription.setText(destination.getDescription());
            tvPhone.setText(getValue(destination.getContactNumber()));
            tvEmail.setText(getValue(destination.getEmailAddress()));
            tvFacebook.setText(getValue(destination.getFacebookPage()));
            tvInstagram.setText(getValue(destination.getInstagramPage()));
        }
    }

    /**
     * Returns the provided value if not empty, otherwise returns "Na".
     *
     * @param value The value to be checked.
     * @return The original value if not empty, otherwise "Na".
     */
    private String getValue(String value) {
        return value.isEmpty() ? "Na" : value;
    }
}
