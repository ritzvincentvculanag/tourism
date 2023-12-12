package io.github.rmmc.rmmctourism.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.github.rmmc.rmmctourism.MainActivity;
import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.model.UserInformation;
import io.github.rmmc.rmmctourism.repository.UserRepository;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.DataCallBack;
import io.github.rmmc.rmmctourism.util.Messenger;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class Profile extends Fragment implements WidgetInitializer, ActionInitializer {

    private View view;

    private TextView tvFullName;
    private TextView tvGender;
    private TextView tvBirthdate;
    private TextView tvEmail;

    private Button btnLogout;
    private Button btnDeleteAccount;
    private Button btnMyDestinations;

    private UserRepository userRepository;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userRepository = new UserRepository(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        initializeWidgets();
        populateData();
        initializeActions();

        return view;
    }

    @Override
    public void initializeActions() {
        btnLogout.setOnClickListener(this::logout);
        btnDeleteAccount.setOnClickListener(this::deleteAccount);
    }

    @Override
    public void initializeWidgets() {
        btnLogout = view.findViewById(R.id.btn_logout);
        btnDeleteAccount = view.findViewById(R.id.btn_delete_account);
        tvFullName = view.findViewById(R.id.tv_profile_fullname);
        tvGender = view.findViewById(R.id.tv_profile_gender);
        tvBirthdate = view.findViewById(R.id.tv_profile_birthdate);
        tvEmail = view.findViewById(R.id.tv_profile_email);
    }

    public void populateData(){
        userRepository.getUserInformation(new DataCallBack<UserInformation>() {
            @Override
            public void onDataLoaded(UserInformation userInformation) {
                String FullName = userInformation.getFirstName() + " " + userInformation.getMiddleName().charAt(0) + ". " + userInformation.getLastName();
                tvFullName.setText(FullName);
                tvGender.setText(userInformation.getGender());
                tvBirthdate.setText(formatBirthDate(userInformation.getBirthDate()));
                tvEmail.setText(userInformation.getEmail());

            }

            @Override
            public void onDataNotAvailable(String error) {

            }
        });
    }

    private void deleteAccount(View view) {
        Messenger.showAlertDialog(getContext(), "Logout", "Do you want to delete this account?", "Yes", "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                userRepository.deleteUserAccount();
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }

    private void logout(View view) {

        Messenger.showAlertDialog(getContext(), "Logout", "Do you want to logout?", "Yes", "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth userAuth = FirebaseAuth.getInstance();
                startActivity(new Intent(getContext(), MainActivity.class));
                userAuth.signOut();
            }
        }, (dialogInterface, i) -> {}).show();
    }

    public static String formatBirthDate(Timestamp timestamp) {
        Date birthDate = new Date(timestamp.toDate().getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
        String formattedBirthDate = sdf.format(birthDate);
        int age = calculateAge(birthDate);
        String ageString = "("+age + " years old)";
        return String.format("%s, %s", ageString, formattedBirthDate);
    }

    private static int calculateAge(Date birthDate) {
        Date currentDate = new Date();
        long diffInMillis = currentDate.getTime() - birthDate.getTime();
        long ageInMillis = diffInMillis;
        return (int) (ageInMillis / (1000 * 60 * 60 * 24 * 365.25));
    }

}