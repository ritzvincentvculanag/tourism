package io.github.rmmc.rmmctourism.views;

import static io.github.rmmc.rmmctourism.util.Messenger.showAlertDialog;
import static io.github.rmmc.rmmctourism.util.Validator.fieldIsEmpty;
import static io.github.rmmc.rmmctourism.util.Validator.fieldsAreEmpty;
import static io.github.rmmc.rmmctourism.util.Validator.isValidName;
import static io.github.rmmc.rmmctourism.util.Validator.isValidPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.SpinnerAdapter;
import io.github.rmmc.rmmctourism.model.UserInformation;
import io.github.rmmc.rmmctourism.repository.UpdateUserRepository;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.Miner;
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
    private UpdateUserRepository updateUserRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        updateUserRepository = new UpdateUserRepository(this);
        // Initialize widgets and set up actions
        initializeWidgets();
        initializeActions();
    }

    @Override
    public void initializeActions() {
        chooseDate.setOnClickListener(this::datePicker);
        update.setOnClickListener(this::updateUser);
    }

    private void updateUser(View view) {
        // Fields validation
        if (fieldsAreEmpty(
                firstName, lastName, birthday
        )) {
            // Show error dialog for empty fields
            showAlertDialog(
                    this,
                    getString(R.string.register_dialog_error_title),
                    getString(R.string.register_dialog_error_message),
                    getString(R.string.register_dialog_error_postive_button)
            ).show();
            return;
        }

        if(!isValidName(firstName)){
            showAlertDialog(this,
                    "Registration Error",
                    "Invalid format for first name!", "Ok").show();
            return;
        }
        if(!isValidName(middleName) && !fieldIsEmpty(middleName)){
            showAlertDialog(this,
                    "Registration Error",
                    "Invalid format for middle name!", "Ok").show();
            return;
        }
        if(!isValidName(lastName)){
            showAlertDialog(this,
                    "Registration Error",
                    "Invalid format for last name!", "Ok").show();
            return;
        }

        // Birthdate validation
        Timestamp birthDate = null;
        String birthDateStr = Miner.getString(birthday);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            Date parsedDate = dateFormat.parse(birthDateStr);

            if (parsedDate != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(parsedDate);
                calendar.add(Calendar.YEAR, 18);

                Date eighteenYearsAgo = calendar.getTime();

                if (eighteenYearsAgo.before(new Date())) {
                    // The birthdate is valid, user is at least 18 years old
                    birthDate = new Timestamp(parsedDate);
                } else {

                    showAlertDialog(this, "Validation Error", "User must be at least 18 years old", "Ok").show();
                    return;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();

            showAlertDialog(this, "Validation Error", "Invalid date format", "Ok").show();
            return;
        }

        UserInformation userInformation = new UserInformation();

        userInformation.setFirstName(Miner.getString(firstName));
        userInformation.setMiddleName(Miner.getString(middleName));
        userInformation.setLastName(Miner.getString(lastName));
        userInformation.setBirthDate(birthDate);
        userInformation.setGender(actvGender.getText().toString());

        updateUserRepository.updateUser(userInformation);

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
            setData(birthday, formatTimestampToString(userInformation.getBirthDate()));
            actvGender.setText(userInformation.getGender(), false);
        }
    }

    private String formatTimestampToString(Timestamp timestamp) {
        if (timestamp != null) {
            Date date = timestamp.toDate();

            if (date != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                return dateFormat.format(date);
            }
        }
        return ""; // Return an empty string or handle the case when timestamp or date is null
    }

    private void datePicker(View view) {
        // Show material date picker and set selected date to the birthdate field
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String formattedDate = sdf.format(selection);
                birthday.getEditText().setText(formattedDate);
            }
        });

        datePicker.show(getSupportFragmentManager(), datePicker.toString());
    }

    // Helper method to set data to TextInputLayout
    private void setData(TextInputLayout textInputLayout, String data) {
        textInputLayout.getEditText().setText(data);
    }
}
