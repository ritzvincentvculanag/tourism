package io.github.rmmc.rmmctourism.views;

import static io.github.rmmc.rmmctourism.util.Messenger.showAlertDialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.repository.UpdateUserRepository;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.Validator;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class ForgotPassword extends AppCompatActivity implements WidgetInitializer, ActionInitializer {

    private TextInputLayout email;
    private Button forgotPassword;
    private UpdateUserRepository updateUserRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        updateUserRepository = new UpdateUserRepository(this);
        initializeWidgets();
        initializeActions();
    }

    @Override
    public void initializeActions() {
        forgotPassword.setOnClickListener(this::forgotPasswordAction);
    }

    @Override
    public void initializeWidgets() {
        email = findViewById(R.id.til_forgot_password_email);
        forgotPassword = findViewById(R.id.btn_send_link);
    }

    private void forgotPasswordAction(View view) {
        if(Validator.fieldIsEmpty(email)){
            showAlertDialog(
                    this,
                    "Forget Password",
                    "Please enter your email!",
                    "Try Again"
            ).show();
            return;
        }
        updateUserRepository.resetPassword(email.getEditText().getText().toString());

    }

}