package io.github.rmmc.rmmctourism.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
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
        String oldPassword = password.getEditText().getText().toString().trim();

        FirebaseUser currentUser = userAuth.getCurrentUser();
        if (currentUser != null) {
            String currentEmail = currentUser.getEmail();

            AuthCredential authCredential = EmailAuthProvider.getCredential(currentEmail, oldPassword);

            currentUser.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    user.verifyBeforeUpdateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Messenger.showAlertDialog(context,
                                    "Change Email",
                                    "User email address updated",
                                    "Ok").show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Messenger.showAlertDialog(context,
                                    "Change Email",
                                    e.getMessage(),
                                    "Ok").show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Messenger.showAlertDialog(context,
                            "Changed Email",
                            e.getMessage(),
                            "Ok").show();
                }
            });
        }
    }

    public void changePassword(TextInputLayout oldPassword, TextInputLayout newPassword) {
        String currentPassword = oldPassword.getEditText().getText().toString().trim();
        String newPasswordValue = newPassword.getEditText().getText().toString().trim();

        FirebaseUser currentUser = userAuth.getCurrentUser();
        if (currentUser != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(currentUser.getEmail(), currentPassword);

            currentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> reauthTask) {
                    if (reauthTask.isSuccessful()) {
                        currentUser.updatePassword(newPasswordValue).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> passwordUpdateTask) {
                                if (passwordUpdateTask.isSuccessful()) {

                                    Messenger.showAlertDialog(context, "Change Password", "Password updated successfully", "Ok").show();
                                } else {
                                    Messenger.showAlertDialog(context, "Change Password", "Failed to update password", "Ok").show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle specific password update failure
                                Messenger.showAlertDialog(context, "Change Password", e.getMessage(), "Ok").show();
                            }
                        });
                    } else {

                        Messenger.showAlertDialog(context, "Change Password", "Reauthentication failed", "Ok").show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Messenger.showAlertDialog(context, "Change Password", e.getMessage(), "Ok").show();
                }
            });
        }
    }

    public void resetPassword(String userEmail) {
        userAuth.sendPasswordResetEmail(userEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Password reset email sent successfully
                            Messenger.showAlertDialog(context, "Forgot Password", "Password reset email sent. Check your inbox.", "Ok").show();
                        } else {
                            // Handle password reset email sending failure
                            Messenger.showAlertDialog(context, "Forgot Password", "Failed to send password reset email.", "Ok").show();
                        }
                    }
                });
    }

}
