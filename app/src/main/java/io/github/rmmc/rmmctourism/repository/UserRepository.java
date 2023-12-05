package io.github.rmmc.rmmctourism.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

public class UserRepository {


    private FirebaseFirestore instance;
    public UserRepository(){
        instance = FirebaseFirestore.getInstance();
    }

    public void addUser(){



    }


}
