package io.github.rmmc.rmmctourism.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.SpinnerAdapter;
import io.github.rmmc.rmmctourism.model.UserInformation;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class UpdateUser extends AppCompatActivity implements WidgetInitializer, ActionInitializer {

    // Declare widgets
    private TextInputLayout firstName;
    private TextInputLayout lastName;
    private TextInputLayout middleName;
    private TextInputLayout birthday;
    private TextInputLayout gender;
    private AutoCompleteTextView actvGender;
    private TextInputLayout email;
    private Button chooseDate;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        // Initialize widgets and set up actions
        initializeWidgets();
        initializeActions();
    }

    @Override
    public void initializeActions() {
        // Actions initialization (if any)
    }

    @Override
    public void initializeWidgets() {
        // Initialize widgets
        firstName = findViewById(R.id.til_update_first_name);
        lastName = findViewById(R.id.til_update_last_name);
        middleName = findViewById(R.id.til_update_middle_name);
        birthday = findViewById(R.id.til_update_birthdate);
        gender = findViewById(R.id.til_update_gender);
        actvGender = findViewById(R.id.actv_update_gender);
        email = findViewById(R.id.til_update_email);
        chooseDate = findViewById(R.id.btn_update_birthdate);
        update = findViewById(R.id.btn_update);

        // Initialize gender spinner
        initializeSpinner();

        // Populate data if available
        populateData();
    }

    private void initializeSpinner() {
        // Set up the gender spinner using a custom adapter
        ArrayAdapter<String> genderAdapter = new SpinnerAdapter<String>().GetArrayAdapter(
                getApplicationContext(),
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                Arrays.asList("Male", "Female")
        );

        actvGender.setAdapter(genderAdapter);
    }

    private void populateData() {
        // Retrieve user information from the intent and populate the UI fields
        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(UserInformation.collectionName)) {
            UserInformation userInformation = intent.getParcelableExtra(UserInformation.collectionName);

            // Set data to UI fields
            setData(firstName, userInformation.getFirstName());
            setData(lastName, userInformation.getLastName());
            setData(middleName, userInformation.getMiddleName() != null ? userInformation.getMiddleName() : "");
            actvGender.setText(userInformation.getGender(), false);
        }
    }

    // Helper method to get the index of a value in an array
    private int getIndexFromArray(String[] array, String value) {
        if (array != null && value != null) {
            for (int i = 0; i < array.length; i++) {
                if (value.equals(array[i])) {
                    return i;
                }
            }
        }
        return -1; // Not found
    }

    // Helper method to set data to TextInputLayout
    private void setData(TextInputLayout textInputLayout, String data) {
        textInputLayout.getEditText().setText(data);
    }
}
