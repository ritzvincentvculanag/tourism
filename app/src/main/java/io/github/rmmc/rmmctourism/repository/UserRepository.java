package io.github.rmmc.rmmctourism.repository;

import android.content.Context;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.github.rmmc.rmmctourism.model.UserInformation;
import io.github.rmmc.rmmctourism.util.Messenger;

public class UserRepository {


    private Context context;
    private FirebaseFirestore instance;
    public UserRepository(Context context){
        this.instance = FirebaseFirestore.getInstance();
    }

    public void addUser(UserInformation user){

        Map<String, Object> newUser = new HashMap<>();

        newUser.put(UserInformation.firstNameField, user.getFirstName());
        newUser.put(UserInformation.lastNameField, user.getLastName());
        newUser.put(UserInformation.middleNameField, user.getMiddleName());
        newUser.put(UserInformation.birthDateField, user.getBirthDate());
        newUser.put(UserInformation.genderField, user.getGender());
        newUser.put(UserInformation.emailField, user.getEmail());
        newUser.put(UserInformation.passwordField, user.getPassword());

        this.instance.collection(UserInformation.collectionName)
                .add(newUser)
                .addOnSuccessListener(task ->{
                    Messenger.showAlertDialog(context, "User Register", "User register successfully!");
                })
                .addOnFailureListener(task ->{
                    Messenger.showAlertDialog(context, "User Register", "User register unsuccessful!");
                });
    }


}
