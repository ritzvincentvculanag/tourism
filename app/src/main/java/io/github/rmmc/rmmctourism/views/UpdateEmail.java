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

    }

    @Override
    public void initializeActions() {
        updateEmail.setOnClickListener(e -> {
            updateUserRepository.updateEmail(email, password);
        });
    }

}