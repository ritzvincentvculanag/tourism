package io.github.rmmc.rmmctourism.views;

import static io.github.rmmc.rmmctourism.util.Messenger.showAlertDialog;
import static io.github.rmmc.rmmctourism.util.Validator.fieldsAreEmpty;

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

        userRepository = new UserRepository(this);

        initializeWidgets();
        initializeActions();
    }

    @Override
    public void initializeWidgets(){
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

        initializeSpinner();
    }

    @Override
    public void initializeActions() {
        btnRegister.setOnClickListener(this::register);
        btnLogin.setOnClickListener(this::login);
        btnDatePicker.setOnClickListener(this::datePicker);
    }

    private void initializeSpinner(){
        ArrayAdapter<String> genderAdapter = new SpinnerAdapter<String>().GetArrayAdapter(
                getApplicationContext(),
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                Arrays.asList(getResources().getStringArray(R.array.register_til_gender_options))
        );

        actvGender.setAdapter(genderAdapter);
    }

    private void register(View view) {

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
            showAlertDialog(
                    this,
                    getString(R.string.register_dialog_error_title),
                    getString(R.string.register_dialog_error_message),
                    getString(R.string.register_dialog_error_postive_button)
            ).show();

            return;
        }

        // Password validation
        String password = Miner.getString(tilRegisterPassword);
        String passwordConfirm = Miner.getString(tilRegisterRetypePassword);
        if (!password.equals(passwordConfirm)) {
            showAlertDialog(
                    this,
                    getString(R.string.register_dialog_error_title),
                    getString(R.string.register_dialog_error_password_do_not_match),
                    getString(R.string.register_dialog_error_postive_button)
            ).show();

            return;
        }

        // Birthdate validation
        Timestamp birthDate = null;
        String birthDateStr = Miner.getString(tilRegisterBirtDate);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            Date parsedDate = dateFormat.parse(birthDateStr);

            if (parsedDate != null) {
                birthDate = new Timestamp(parsedDate);
            }
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