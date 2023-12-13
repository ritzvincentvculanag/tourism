package io.github.rmmc.rmmctourism.repository;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import io.github.rmmc.rmmctourism.util.Messenger;

public class UpdateUserRepository {

    private Context context;
    private FirebaseFirestore instance;
    private FirebaseAuth userAuth;

    public UpdateUserRepository(Context context) {
        this.context = context;
        this.instance = FirebaseFirestore.getInstance();
        this.userAuth = FirebaseAuth.getInstance();
    }


    public void updateEmail(TextInputLayout email, TextInputLayout password) {
        String newEmail = email.getEditText().getText().toString().trim();
        String newPassword = password.getEditText().getText().toString().trim();

        FirebaseUser currentUser = userAuth.getCurrentUser();
        if (currentUser != null) {
            String currentEmail = currentUser.getEmail();

            userAuth.signInWithEmailAndPassword(currentEmail, newPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            currentUser.updateEmail(newEmail)
                                    .addOnCompleteListener(emailUpdateTask -> {
                                        if (emailUpdateTask.isSuccessful()) {
                                            Messenger.showAlertDialog(context,
                                                    "Change Email",
                                                    "Email change successfully",
                                                    "Ok").show();
                                        } else {
                                            Messenger.showAlertDialog(context,
                                                    "Change Email",
                                                    "There is an error while changing the email",
                                                    "Ok").show();
                                        }
                                    });
                        } else {
                            Messenger.showAlertDialog(context,
                                    "Change Email",
                                    "Incorrect Password!",
                                    "Ok").show();

                        }
                    });
        }
    }

}
