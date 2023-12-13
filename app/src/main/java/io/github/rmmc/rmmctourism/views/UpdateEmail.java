package io.github.rmmc.rmmctourism.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.repository.UpdateUserRepository;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class UpdateEmail extends AppCompatActivity implements ActionInitializer {

    // Declare widgets
    private TextInputLayout email;
    private TextInputLayout password;
    private Button updateEmail;
    private UpdateUserRepository updateUserRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);

        // Initialize repository for updating user information
        updateUserRepository = new UpdateUserRepository(this);

        // Initialize widgets
        email = findViewById(R.id.til_update_email);
        password = findViewById(R.id.til_update_email_password);
        updateEmail = findViewById(R.id.btn_update_email);

        // Set up actions
        initializeActions();
    }

    @Override
    public void initializeActions() {
        // Set a click listener for the updateEmail button
        updateEmail.setOnClickListener(e -> {
            // Call the repository method to update the email
            updateUserRepository.updateEmail(email, password);
        });
    }
}
