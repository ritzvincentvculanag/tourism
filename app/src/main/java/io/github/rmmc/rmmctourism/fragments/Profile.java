package io.github.rmmc.rmmctourism.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
import io.github.rmmc.rmmctourism.views.MyDestinations;
import io.github.rmmc.rmmctourism.views.UpdateEmail;
import io.github.rmmc.rmmctourism.views.UpdatePassword;
import io.github.rmmc.rmmctourism.views.UpdateUser;

public class Profile extends Fragment implements WidgetInitializer, ActionInitializer {

    private Context context;
    private View view;

    private TextView tvFullName;
    private TextView tvGender;
    private TextView tvBirthdate;
    private TextView tvEmail;

    private Button btnLogout;
    private Button btnDeleteAccount;
    private Button btnMyDestinations;
    private Button btnEditProfile;
    private Button btnEditEmail;
    private Button btnEditPassword;

    private UserRepository userRepository;
    private FirebaseAuth user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase Authentication and User Repository
        user = FirebaseAuth.getInstance();
        userRepository = new UserRepository(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize widgets, populate data, and set up actions
        initializeWidgets();
        populateData();
        initializeActions();

        return view;
    }

    @Override
    public void initializeActions() {
        // Set click listeners for various buttons
        btnLogout.setOnClickListener(this::logout);
        btnDeleteAccount.setOnClickListener(this::deleteAccount);
        btnMyDestinations.setOnClickListener(this::myDestination);
        btnEditEmail.setOnClickListener(e -> startActivity(new Intent(getContext(), UpdateEmail.class)));
        btnEditPassword.setOnClickListener(e -> startActivity(new Intent(getContext(), UpdatePassword.class)));
    }

    @Override
    public void initializeWidgets() {
        // Find and initialize UI elements
        btnLogout = view.findViewById(R.id.btn_logout);
        btnDeleteAccount = view.findViewById(R.id.btn_delete_account);
        btnMyDestinations = view.findViewById(R.id.btn_my_destinations);
        btnEditEmail = view.findViewById(R.id.btn_edit_email);
        btnEditPassword = view.findViewById(R.id.btn_edit_password);
        tvFullName = view.findViewById(R.id.tv_proflle_username);
        tvGender = view.findViewById(R.id.tv_profile_gender);
        tvBirthdate = view.findViewById(R.id.tv_profile_birthdate);
        tvEmail = view.findViewById(R.id.tv_profile_email);
        btnEditProfile = view.findViewById(R.id.btn_edit_profile);
    }

    public void populateData() {
        // Fetch user information and populate UI elements
        userRepository.getUserInformation(new DataCallBack<UserInformation>() {
            @Override
            public void onDataLoaded(UserInformation userInformation) {
                // Construct the full name
                String middleNameInitial = userInformation.getMiddleName().toString().isEmpty() ? "" : userInformation.getMiddleName().charAt(0) + ".";
                String fullName = userInformation.getFirstName() + " " + middleNameInitial + " " + userInformation.getLastName();

                // Set data to UI elements
                tvFullName.setText(fullName);
                tvGender.setText(userInformation.getGender());
                tvBirthdate.setText(formatBirthDate(userInformation.getBirthDate()));
                tvEmail.setText(userInformation.getEmail());

                // Set up click listener for editing profile
                btnEditProfile.setOnClickListener(e -> {
                    Intent intent = new Intent(getContext(), UpdateUser.class);
                    intent.putExtra(UserInformation.collectionName, userInformation);
                    startActivity(intent);
                });

                // Display current user's email
                tvEmail.setText(user.getCurrentUser().getEmail());
            }

            @Override
            public void onDataNotAvailable(String error) {
                // Handle the case where data is not available
            }
        });
    }

    private void deleteAccount(View view) {
        // Show an alert dialog to confirm account deletion
        Messenger.showAlertDialog(getContext(), "Delete Account", "Do you want to delete this account?", "Yes", "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Delete the user account and navigate to the main activity
                userRepository.deleteUserAccount();
                Intent intent = new Intent(getContext(), MainActivity.class);
                user.signOut();
                getParentFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                startActivity(intent);

            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Handle the case where the user decides not to delete the account
            }
        }).show();

    }

    private void logout(View view) {
        // Show an alert dialog to confirm logout
        Messenger.showAlertDialog(getContext(), "Logout", "Do you want to logout?", "Yes", "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Sign out the user and navigate to the main activity
                FirebaseAuth userAuth = FirebaseAuth.getInstance();
                getParentFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                startActivity(new Intent(getContext(), MainActivity.class));
                userAuth.signOut();
            }
        }, (dialogInterface, i) -> {
        }).show();
    }

    private void myDestination(View view) {
        // Navigate to the MyDestinations activity
        startActivity(new Intent(getContext(), MyDestinations.class));
    }

    public static String formatBirthDate(Timestamp timestamp) {
        // Format the birthdate to a readable string
        Date birthDate = new Date(timestamp.toDate().getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
        String formattedBirthDate = sdf.format(birthDate);
        int age = calculateAge(birthDate);
        String ageString = "(" + age + " years old)";
        return String.format("%s, %s", ageString, formattedBirthDate);
    }

    private static int calculateAge(Date birthDate) {
        // Calculate age based on the birthdate
        Date currentDate = new Date();
        long diffInMillis = currentDate.getTime() - birthDate.getTime();
        long ageInMillis = diffInMillis;
        return (int) (ageInMillis / (1000 * 60 * 60 * 24 * 365.25));
    }

}
