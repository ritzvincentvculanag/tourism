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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        userRepository = new UserRepository(getApplicationContext());
    }
    private void initializeWidgets(){

        // TextFields
        tilRegisterFirstName = findViewById(R.id.til_reg_first_name);
        tilRegisterLastName = findViewById(R.id.til_reg_last_name);
        tilRegisterMiddleName = findViewById(R.id.til_reg_middle_name);
        tilRegisterBirtDate = findViewById(R.id.til_reg_birthdate);
        tilRegisterEmail = findViewById(R.id.til_reg_email);
        tilRegisterPassword = findViewById(R.id.til_reg_password);
        tilRegisterPassword = findViewById(R.id.til_reg_password_retype);

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
                tilRegisterLastName ,
                tilRegisterBirtDate,
                tilRegisterEmail,
                tilRegisterPassword,
                tilRegisterPassword
        };

        if(Validator.fieldsAreEmpty(fields)){
            Messenger.showAlertDialog(getApplicationContext(), "User Registration",
                    "Please provide information needed!").show();

            return;
        }

        if(!tilRegisterPassword.getEditText().getText().toString().equals(tilRegisterRetypePassword.getEditText().getText().toString())){
            Messenger.showAlertDialog(this, "User Registration",
                    "Password does not match!").show();
            return;
        }

        LocalDate birthDate = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            birthDate = LocalDate.parse(tilRegisterBirtDate.getEditText().getText().toString());
        }

        UserInformation user = new UserInformation(
                "",
                tilRegisterFirstName.getEditText().getText().toString(),
                tilRegisterLastName.getEditText().getText().toString(),
                tilRegisterMiddleName.getEditText().getText().toString(),
                birthDate,
                actvGender.getText().toString(),
                tilRegisterEmail.getEditText().getText().toString(),
                tilRegisterPassword.getEditText().getText().toString()
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