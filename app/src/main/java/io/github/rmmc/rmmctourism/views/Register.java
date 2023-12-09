package io.github.rmmc.rmmctourism.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.SpinnerAdapter;
import io.github.rmmc.rmmctourism.model.UserInformation;
import io.github.rmmc.rmmctourism.repository.UserRepository;
import io.github.rmmc.rmmctourism.util.Messenger;
import io.github.rmmc.rmmctourism.util.Validator;

public class Register extends AppCompatActivity {
    private TextInputLayout tilRegisterFirstName;
    private TextInputLayout tilRegisterLastName;
    private TextInputLayout tilRegisterMiddleName;
    private TextInputLayout tilRegisterBirtDate;
    private TextInputLayout tilRegisterEmail;
    private TextInputLayout tilRegisterPassword;
    private TextInputLayout tilRegisterRetypePassword;
    private AutoCompleteTextView actvGender;
    private Button btnRegister;
    private Button btnLogin;
    private Button btnDatePicker;

    private UserRepository userRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeWidgets();
        initializeListeners();
        initialiseSpinner();
        userRepository = new UserRepository(this);
    }
    private void initializeWidgets(){

        // TextFields
        tilRegisterFirstName = findViewById(R.id.til_reg_first_name);
        tilRegisterLastName = findViewById(R.id.til_reg_last_name);
        tilRegisterMiddleName = findViewById(R.id.til_reg_middle_name);
        tilRegisterBirtDate = findViewById(R.id.til_reg_birthdate);
        tilRegisterEmail = findViewById(R.id.til_reg_email);
        tilRegisterPassword = findViewById(R.id.til_reg_password);
        tilRegisterRetypePassword = findViewById(R.id.til_reg_password_retype);

        //Spinner
        actvGender = findViewById(R.id.actv_reg_gender);

        // Button
        btnLogin = findViewById(R.id.btn_reg_login);
        btnRegister = findViewById(R.id.btn_reg_register);
        btnDatePicker = findViewById(R.id.btn_select_birthdate);
    }

    private void initializeListeners() {
        btnRegister.setOnClickListener(this::register);
        btnLogin.setOnClickListener(this::login);
        btnDatePicker.setOnClickListener(this::datePicker);
    }


    private void initialiseSpinner(){   
        List<String> list = new ArrayList<>();
        list.add("Male");
        list.add("Female");
        actvGender.setAdapter(new SpinnerAdapter<String>().GetArrayAdapter(this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                list));
    }

    private void register(View view) {

        TextInputLayout[] fields = {
                tilRegisterFirstName,
                tilRegisterLastName,
                tilRegisterBirtDate,
                tilRegisterEmail,
                tilRegisterPassword,
                tilRegisterPassword
        };

        if (Validator.fieldsAreEmpty(fields)) {
            Messenger.showAlertDialog(this, "User Registration",
                    "Please provide information needed!", "Ok").show();

            return;
        }

        if (!Validator.isValidName(tilRegisterFirstName) || !Validator.isValidName(tilRegisterLastName)) {
            Messenger.showAlertDialog(this, "User Registration",
                    "Invalid name!", "Ok").show();
            return;
        }

        if (!Validator.isValidEmail(tilRegisterEmail)) {
            Messenger.showAlertDialog(this, "User Registration",
                    "Invalid email address!", "Ok").show();
            return;
        }

        if (!Validator.isValidPassword(tilRegisterPassword)) {
            Messenger.showAlertDialog(this, "User Registration",
                    "Invalid password (minimum 8 characters with at least one uppercase, one lowercase, one digit, and one special character)", "Ok").show();
            return;
        }
        if (!tilRegisterPassword.getEditText().getText().toString().equals(tilRegisterRetypePassword.getEditText().getText().toString())) {
            Messenger.showAlertDialog(this, "User Registration",
                    "Password does not match!", "Ok").show();
            return;
        }

        // Parse birthdate and convert it to Timestamp
        String birthDateStr = tilRegisterBirtDate.getEditText().getText().toString();
        Timestamp birthDate = null;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            Date parsedDate = dateFormat.parse(birthDateStr);
            birthDate = new Timestamp(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        UserInformation user = new UserInformation(
                1,
                tilRegisterFirstName.getEditText().getText().toString(),
                tilRegisterLastName.getEditText().getText().toString(),
                tilRegisterMiddleName.getEditText().getText().toString(),
                birthDate,
                actvGender.getText().toString(),
                tilRegisterEmail.getEditText().getText().toString(),
                tilRegisterPassword.getEditText().getText().toString(),
                null, null
        );

        userRepository.addUser(user);
    }

    private void login(View view) {
        startActivity(new Intent(this, Login.class));
        finish();
    }

    private void datePicker(View view) {
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