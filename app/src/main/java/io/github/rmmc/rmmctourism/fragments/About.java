package io.github.rmmc.rmmctourism.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;


public class About extends Fragment implements WidgetInitializer, ActionInitializer {

    private View view;

    private TextView tvDescription;
    private TextView tvPhone;
    private TextView tvEmail;
    private TextView tvFacebook;
    private TextView tvInstagram;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about, container, false);

        initializeWidgets();
        initializeActions();

        return view;
    }

    @Override
    public void initializeActions() {

    }

    @Override
    public void initializeWidgets() {
        tvDescription = view.findViewById(R.id.tv_destination_detail_description);
        tvPhone = view.findViewById(R.id.tv_destination_detail_phone);
        tvEmail = view.findViewById(R.id.tv_destination_detail_email);
        tvFacebook = view.findViewById(R.id.til_add_destination_facebook);
        tvInstagram = view.findViewById(R.id.tv_destination_detail_instagram);
    }
}