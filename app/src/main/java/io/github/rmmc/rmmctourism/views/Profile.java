package io.github.rmmc.rmmctourism.views;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import io.github.rmmc.rmmctourism.MainActivity;
import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.Messenger;

public class Profile extends Fragment implements ActionInitializer {

    private Button btnLogout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnLogout = view.findViewById(R.id.btn_logout);
        initializeActions();
        return view;
    }

    @Override
    public void initializeActions() {
        btnLogout.setOnClickListener(this::logout);
    }

    private void logout(View view) {


        Messenger.showAlertDialog(getContext(), "Logout", "Do you want to logout?", "Yes", "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth userAuth = FirebaseAuth.getInstance();
                startActivity(new Intent(getContext(), MainActivity.class));
                userAuth.signOut();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();

    }
}