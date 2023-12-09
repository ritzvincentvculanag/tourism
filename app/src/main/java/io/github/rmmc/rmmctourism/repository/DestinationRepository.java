package io.github.rmmc.rmmctourism.repository;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class DestinationRepository {

    private Context context;
    private FirebaseAuth userAuth;
    private FirebaseFirestore instance;

    public DestinationRepository(Context context){
        this.context = context;
        this.userAuth = FirebaseAuth.getInstance();
        this.instance = FirebaseFirestore.getInstance();
    }
}
