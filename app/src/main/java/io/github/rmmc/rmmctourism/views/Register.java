package io.github.rmmc.rmmctourism.views;

import static io.github.rmmc.rmmctourism.util.Messenger.showAlertDialog;
import static io.github.rmmc.rmmctourism.util.Validator.fieldIsEmpty;
import static io.github.rmmc.rmmctourism.util.Validator.fieldsAreEmpty;
import static io.github.rmmc.rmmctourism.util.Validator.isValidEmail;
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
import io.github.rmmc.rmmctourism.repository.UserRepository;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.Messenger;
import io.github.rmmc.rmmctourism.util.Miner;
import io.github.rmmc.rmmctourism.util.NetworkUtils;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class Register extends AppCompatActivity implements WidgetInitializer, ActionInitializer {

    // Declare widgets
    private TextInputLayout tilRegisterFirstName;
    private TextInputLayout tilRegisterLastName;
    private TextInputLayout tilRegisterMiddleName;
    private TextInputLayout tilRegisterBirtDate;
    private TextInputLayout tilRegisterEmail;
    private TextInputLayout tilRegisterPassword;
    private TextInputLayout tilRegisterRetypePassword;
    private Button btnRegister;
    private Button btnLogin;
    private Button btnDatePicker;
    private AutoCompleteTextView actvGender;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UserRepository for user data operations
        userRepository = new UserRepository(this);

        // Initialize widgets and set up actions
        initializeWidgets();
        initializeActions();
    }

    @Override
    public void initializeWidgets() {
        // Initialize UI widgets
        tilRegisterFirstName = findViewById(R.id.til_reg_first_name);
        tilRegisterLastName = findViewById(R.id.til_reg_last_name);
        tilRegisterMiddleName = findViewById(R.id.til_reg_middle_name);
        tilRegisterBirtDate = findViewById(R.id.til_reg_birthdate);
        tilRegisterEmail = findViewById(R.id.til_reg_email);
        tilRegisterPassword = findViewById(R.id.til_reg_password);
        tilRegisterRetypePassword = findViewById(R.id.til_reg_password_retype);
        btnLogin = findViewById(R.id.btn_reg_login);
        btnRegister = findViewById(R.id.btn_reg_register);
        btnDatePicker = findViewById(R.id.btn_select_birthdate);
        actvGender = findViewById(R.id.actv_reg_gender);

        // Initialize gender spinner
        initializeSpinner();
    }

    @Override
    public void initializeActions() {
        // Set up click listeners for buttons
        btnRegister.setOnClickListener(this::register);
        btnLogin.setOnClickListener(this::login);
        btnDatePicker.setOnClickListener(this::datePicker);
    }

    private void initializeSpinner() {
        // Initialize gender spinner with ArrayAdapter
        ArrayAdapter<String> genderAdapter = new SpinnerAdapter<String>().GetArrayAdapter(
                getApplicationContext(),
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                Arrays.asList(getResources().getStringArray(R.array.register_til_gender_options))
        );
        actvGender.setAdapter(genderAdapter);
    }

    private void register(View view) {
        // Check internet connection
        if (!NetworkUtils.isNetworkConnected(this)) {
            Messenger.showAlertDialog(this, "Internet Connection","Please connect to the internet before using the application", "Ok").show();
            return;
        }

        // Fields validation
        if (fieldsAreEmpty(
                tilRegisterFirstName,
                tilRegisterLastName,
                tilRegisterBirtDate,
                tilRegisterEmail,
                tilRegisterPassword,
                tilRegisterPassword
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

        if(!isValidName(tilRegisterFirstName)){
            showAlertDialog(this,
                    "Registration Error",
                    "Invalid format for first name!", "Ok").show();
            return;
        }
        if(!isValidName(tilRegisterMiddleName) && !fieldIsEmpty(tilRegisterMiddleName)){
            showAlertDialog(this,
                    "Registration Error",
                    "Invalid format for middle name!", "Ok").show();
            return;
        }
        if(!isValidName(tilRegisterLastName)){
            showAlertDialog(this,
                    "Registration Error",
                    "Invalid format for last name!", "Ok").show();
            return;
        }

        // Birthdate validation
        Timestamp birthDate = null;
        String birthDateStr = Miner.getString(tilRegisterBirtDate);
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

        if(!isValidEmail(tilRegisterEmail)){
            showAlertDialog(
                    this,
                    getString(R.string.register_dialog_error_title),
                    "Invalid email format",
                    getString(R.string.register_dialog_error_postive_button)
            ).show();
            return;
        }

        if(!isValidPassword(tilRegisterPassword)){
            showAlertDialog(
                    this,
                    getString(R.string.register_dialog_error_title),
                    "Invalid password format",
                    getString(R.string.register_dialog_error_postive_button)
            ).show();
            return;
        }

        // Password validation
        String password = Miner.getString(tilRegisterPassword);
        String passwordConfirm = Miner.getString(tilRegisterRetypePassword);
        if (!password.equals(passwordConfirm)) {
            // Show error dialog for mismatched passwords
            showAlertDialog(
                    this,
                    getString(R.string.register_dialog_error_title),
                    getString(R.string.register_dialog_error_password_do_not_match),
                    getString(R.string.register_dialog_error_postive_button)
            ).show();
            return;
        }



        // Create UserInformation object
        UserInformation user = new UserInformation(
                tilRegisterFirstName.getEditText().getText().toString(),
                tilRegisterLastName.getEditText().getText().toString(),
                tilRegisterMiddleName.getEditText().getText().toString(),
                birthDate,
                actvGender.getText().toString(),
                tilRegisterEmail.getEditText().getText().toString(),
                tilRegisterPassword.getEditText().getText().toString()
        );

        // Add user to the repository
        userRepository.addUser(user);
    }

    private void login(View view) {
        // Start the login activity
        startActivity(new Intent(this, Login.class));
        finish();
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
                tilRegisterBirtDate.getEditText().setText(formattedDate);
            }
        });

        datePicker.show(getSupportFragmentManager(), datePicker.toString());
    }
}
