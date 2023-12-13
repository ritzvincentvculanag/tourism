package io.github.rmmc.rmmctourism.views;

import static io.github.rmmc.rmmctourism.util.Messenger.showAlertDialog;
import static io.github.rmmc.rmmctourism.util.Validator.isValidPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.repository.UpdateUserRepository;
import io.github.rmmc.rmmctourism.util.Messenger;
import io.github.rmmc.rmmctourism.util.Miner;
import io.github.rmmc.rmmctourism.util.Validator;

public class UpdatePassword extends AppCompatActivity {

    // Declare widgets
    private TextInputLayout oldPassword;
    private TextInputLayout newPassword;
    private TextInputLayout confirmPassword;
    private Button updatePassword;
    private UpdateUserRepository updateUserRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        // Initialize widgets
        oldPassword = findViewById(R.id.til_update_password_old);
        newPassword = findViewById(R.id.til_update_password_new);
        confirmPassword = findViewById(R.id.til_update_password_confirm);
        updatePassword = findViewById(R.id.btn_update_password);

        updateUserRepository = new UpdateUserRepository(this);

        updatePassword.setOnClickListener(this::updatePassword);
    }

    private void updatePassword(View view) {
        if(Validator.fieldsAreEmpty(newPassword, confirmPassword ,oldPassword)){
            showAlertDialog(
                    this,
                    "Change Password",
                    "Please enter the new email and password!",
                    "Try Again"
            ).show();
            return;
        }
        if(!isValidPassword(newPassword)){
            showAlertDialog(
                    this,
                    getString(R.string.register_dialog_error_title),
                    "Invalid password format",
                    getString(R.string.register_dialog_error_postive_button)
            ).show();
            return;
        }
        String newPass = Miner.getString(newPassword);
        String confirmPass = Miner.getString(confirmPassword);
        if(!newPass.equals(confirmPass)){
            Messenger.showAlertDialog(this,
                    "Change Password",
                    "Password doest not match!",
                    "Try again").show();
            return;
        }
        updateUserRepository.changePassword(oldPassword, newPassword);
    }
}
