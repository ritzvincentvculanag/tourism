package io.github.rmmc.rmmctourism.repository;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import io.github.rmmc.rmmctourism.model.UserInformation;
import io.github.rmmc.rmmctourism.util.DataCallBack;
import io.github.rmmc.rmmctourism.util.Messenger;
import io.github.rmmc.rmmctourism.views.Login;

public class UserRepository {

    private Context context;
    private FirebaseAuth userAuth;
    private FirebaseFirestore instance;

    public UserRepository(Context context) {
        this.context = context;
        this.instance = FirebaseFirestore.getInstance();
        this.userAuth = FirebaseAuth.getInstance();
    }

    // Method to add a new user
    public void addUser(UserInformation user) {
        Map<String, Object> newUser = new HashMap<>();

        newUser.put(UserInformation.firstNameField, user.getFirstName());
        newUser.put(UserInformation.lastNameField, user.getLastName());
        newUser.put(UserInformation.middleNameField, user.getMiddleName());
        newUser.put(UserInformation.birthDateField, user.getBirthDate());
        newUser.put(UserInformation.genderField, user.getGender());

        // Create a new user using email and password
        userAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // If user creation is successful, add user information to Firestore
                            String uid = userAuth.getCurrentUser().getUid();
                            instance.collection(UserInformation.collectionName).document(uid).set(newUser)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            // Show success message, start login activity, and sign out
                                            Messenger.showAlertDialog(context, "User Register",
                                                    "You successfully registered!", "Login Now", "Back", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            context.startActivity(new Intent(context, Login.class));
                                                            userAuth.signOut();
                                                        }
                                                    }, new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {

                                                        }
                                                    }).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Show failure message
                                            Messenger.showAlertDialog(context, "User Register",
                                                    "Registration unsuccessful!", "Ok").show();
                                        }
                                    });
                        } else {
                            // Show failure message with the exception details
                            Messenger.showAlertDialog(context, "User Register",
                                    task.getException().getMessage(), "Ok").show();
                        }
                    }
                });
    }

    // Method to get user information from Firestore
    public void getUserInformation(DataCallBack<UserInformation> dataCallBack) {
        String uid = userAuth.getCurrentUser().getUid();
        instance.collection(UserInformation.collectionName).document(uid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // If user document exists, load user information
                                UserInformation userInformation = document.toObject(UserInformation.class);
                                dataCallBack.onDataLoaded(userInformation);
                            } else {
                                // If user document does not exist, provide a message
                                dataCallBack.onDataNotAvailable("User not found");
                            }
                        } else {
                            // If an error occurs during the query, provide an error message
                            dataCallBack.onDataNotAvailable(task.getException().getMessage());
                        }
                    }
                });
    }

    // Method to delete the user account
    public void deleteUserAccount() {
        FirebaseUser currentUser = userAuth.getCurrentUser();

        if (currentUser != null) {
            // If a user is logged in, delete the user account
            currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    // Show success message
                    Messenger.showAlertDialog(context, "User Account Deletion",
                            "You successfully deleted your account!", "Ok").show();
                }
            });
        } else {
            // If no user is currently logged in, provide a message
            Messenger.showAlertDialog(context, "User Account Deletion",
                    "No user is currently logged in!", "Ok").show();
        }
    }

}
