package io.github.rmmc.rmmctourism.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.model.UserInformation;
import io.github.rmmc.rmmctourism.util.Messenger;

public class DestinationRepository {

    private Context context;
    private FirebaseAuth userAuth;
    private FirebaseFirestore instance;

    public DestinationRepository(Context context){
        this.context = context;
        this.userAuth = FirebaseAuth.getInstance();
        this.instance = FirebaseFirestore.getInstance();
    }

    public void addDestination(Destination destination){

        Map<String, Object> data = destinationToMap(destination);
        instance.collection(Destination.collectioName)
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Messenger.showAlertDialog(context,
                                "Tourist Destination",
                                "Tourist Destination added successfully!",
                                "Ok").show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Messenger.showAlertDialog(context,
                                "Tourist Destination",
                                "Tourist Destination added fail!",
                                "Ok").show();
                    }
                });
    }

    public List<Destination> getDestination(){
        List<Destination> list = new ArrayList<>();

        instance.collection(Destination.collectioName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Destination destination = documentToDestination(document);
                                list.add(destination);
                            }
                        } else {

                        }
                    }
                });

        return list;
    }

    private Map<String, Object> destinationToMap(Destination destination) {
        Map<String, Object> destinationMap = new HashMap<>();
        destinationMap.put(Destination.userIdField, destination.getUserId());
        destinationMap.put(Destination.descriptionField, destination.getDestinationCategoryId());
        destinationMap.put(Destination.nameField, destination.getName());
        destinationMap.put(Destination.descriptionField, destination.getDescription());
        destinationMap.put(Destination.addressField, destination.getAddress());
        destinationMap.put(Destination.contactNumberField, destination.getContactNumber());
        destinationMap.put(Destination.websiteUrlField, destination.getWebsiteUrl());
        destinationMap.put(Destination.facebookPageField, destination.getFacebookPage());
        destinationMap.put(Destination.instagramPageField, destination.getInstagramPage());
        destinationMap.put(Destination.emailAddressField, destination.getEmailAddress());
        destinationMap.put(Destination.datePublishedField, destination.getDatePublished());
        destinationMap.put(Destination.lastUpdateField, destination.getLastUpdate());

        destinationMap.values().removeAll(Collections.singleton(null));

        return destinationMap;
    }

    private Destination documentToDestination(QueryDocumentSnapshot document) {
        String destinationId = document.getString(Destination.descriptionField);
        String userId = document.getString(Destination.userIdField);
        String destinationCategoryId = document.getString(Destination.destinationCategoryIdField);
        String name = document.getString(Destination.nameField);
        String description = document.getString(Destination.descriptionField);
        String address = document.getString(Destination.addressField);
        String contactNumber = document.getString(Destination.contactNumberField);
        String websiteUrl = document.getString(Destination.websiteUrlField);
        String facebookPage = document.getString(Destination.facebookPageField);
        String instagramPage = document.getString(Destination.instagramPageField);
        String emailAddress = document.getString(Destination.emailAddressField);
        Timestamp datePublished = document.getTimestamp(Destination.datePublishedField);
        Timestamp lastUpdate = document.getTimestamp(Destination.lastUpdateField);

        return new Destination(destinationId, userId, destinationCategoryId, name, description, address, contactNumber,
                websiteUrl, facebookPage, instagramPage, emailAddress, datePublished, lastUpdate);
    }
}
