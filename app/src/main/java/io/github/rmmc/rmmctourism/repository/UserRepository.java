package io.github.rmmc.rmmctourism.repository;

import android.app.Activity;
import android.content.Context;

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

public class UserRepository {

    private Context context;
    private FirebaseAuth userAuth;
    private FirebaseFirestore instance;

    public UserRepository(Context context) {
        this.context = context;
        this.instance = FirebaseFirestore.getInstance();
        this.userAuth = FirebaseAuth.getInstance();
    }

    public void addUser(UserInformation user) {
        Map<String, Object> newUser = new HashMap<>();

        newUser.put(UserInformation.firstNameField, user.getFirstName());
        newUser.put(UserInformation.lastNameField, user.getLastName());
        newUser.put(UserInformation.middleNameField, user.getMiddleName());
        newUser.put(UserInformation.birthDateField, user.getBirthDate());
        newUser.put(UserInformation.genderField, user.getGender());
        newUser.put(UserInformation.emailField, user.getEmail());
        newUser.put(UserInformation.passwordField, user.getPassword());
        newUser.put(UserInformation.dateRegisteredField, user.getDateRegistered());
        newUser.put(UserInformation.lastUpdatedField, user.getLastUpdated());

        userAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String uid = userAuth.getCurrentUser().getUid();
                    instance.collection(UserInformation.collectionName).document(uid).set(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Messenger.showAlertDialog(context, "User Register", "You successfully register!", "Ok").show();
                            userAuth.signOut();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Messenger.showAlertDialog(context, "User Register", "You unsuccessfully register!", "Ok").show();
                        }
                    });
                } else {
                    Messenger.showAlertDialog(context, "User Register", task.getException().getMessage(), "Ok").show();
                }
            }

        });
    }

    public void getUserInformation(DataCallBack<UserInformation> dataCallBack) {
        String uid = userAuth.getCurrentUser().getUid();
        instance.collection(UserInformation.collectionName).document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        UserInformation userInformation = document.toObject(UserInformation.class);
                        dataCallBack.onDataLoaded(userInformation);
                    } else {
                        dataCallBack.onDataNotAvailable("User not found");
                    }
                } else {
                    dataCallBack.onDataNotAvailable(task.getException().getMessage());
                }
            }
        });
    }

    public void deleteUserAccount() {
        FirebaseUser currentUser = userAuth.getCurrentUser();

        if (currentUser != null) {
            currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Messenger.showAlertDialog(context, "User Account Deletion", "You successfully delete your account!", "Ok").show();
                }
            });
        } else {
            Messenger.showAlertDialog(context, "User Account Deletion", "No user is currently logged in!", "Ok").show();
        }
    }

}
