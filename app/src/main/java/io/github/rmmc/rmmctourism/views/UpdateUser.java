package io.github.rmmc.rmmctourism.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class UpdateUser extends AppCompatActivity implements WidgetInitializer, ActionInitializer {

    private TextInputLayout firstName;
    private TextInputLayout lastName;
    private TextInputLayout middleName;
    private TextInputLayout birthday;
    private TextInputLayout gender;
    private TextInputLayout email;
    private TextInputLayout password;
    private TextInputLayout passwordRetype;

    private Button chooseDate;
    private Button delete;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        initializeWidgets();
        initializeActions();
    }

    @Override
    public void initializeActions() {
        // TODO: intiialize actions
    }

    @Override
    public void initializeWidgets() {
        firstName = findViewById(R.id.til_update_first_name);
        lastName = findViewById(R.id.til_update_last_name);
        middleName = findViewById(R.id.til_update_middle_name);
        birthday = findViewById(R.id.til_update_birthdate);
        gender = findViewById(R.id.til_update_gender);
        email = findViewById(R.id.til_update_email);
        password = findViewById(R.id.til_update_password);
        passwordRetype = findViewById(R.id.til_update_password_retype);

        chooseDate = findViewById(R.id.btn_update_birthdate);
        delete = findViewById(R.id.btn_update_delete);
        update = findViewById(R.id.btn_update);
    }
}