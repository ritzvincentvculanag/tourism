package io.github.rmmc.rmmctourism.views;

import static io.github.rmmc.rmmctourism.util.Messenger.showAlertDialog;
import static io.github.rmmc.rmmctourism.util.Validator.isValidEmail;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.repository.UpdateUserRepository;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.Validator;

public class UpdateEmail extends AppCompatActivity implements ActionInitializer {

    private TextInputLayout email;
    private TextInputLayout password;

    private Button updateEmail;
    private UpdateUserRepository updateUserRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);
        updateUserRepository = new UpdateUserRepository(this);

        email = findViewById(R.id.til_update_email);
        password = findViewById(R.id.til_update_email_password);

        updateEmail = findViewById(R.id.btn_update_email);
        initializeActions();
    }

    @Override
    public void initializeActions() {

        updateEmail.setOnClickListener(e -> {
            if (Validator.fieldsAreEmpty(email, password)) {
                showAlertDialog(
                        this,
                        "Change Email",
                        "Please enter the new email and password!",
                        "Try Again"
                ).show();
                return;
            }
            if (!isValidEmail(email)) {
                showAlertDialog(
                        this,
                        getString(R.string.register_dialog_error_title),
                        "Invalid email format",
                        getString(R.string.register_dialog_error_postive_button)
                ).show();
                return;
            }
            updateUserRepository.updateEmail(email, password);
        });

    }

}