package io.github.rmmc.rmmctourism.repository;

import static android.content.ContentValues.TAG;

import android.content.ContentResolver;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.model.Favorite;
import io.github.rmmc.rmmctourism.model.ImageGallery;
import io.github.rmmc.rmmctourism.util.BatchUploadCallback;
import io.github.rmmc.rmmctourism.util.DestinationDataCallback;
import io.github.rmmc.rmmctourism.util.ImageDataCallback;
import io.github.rmmc.rmmctourism.util.Messenger;

public class DestinationRepository {

    private Context context;

    private FirebaseFirestore instance;
    private ImageRepository imageRepository;
    private FirebaseAuth userAuth;

    public DestinationRepository(){
        this.instance = FirebaseFirestore.getInstance();
        this.imageRepository = new ImageRepository();
        this.userAuth = FirebaseAuth.getInstance();
    }

    public DestinationRepository(Context context){
        this.context = context;
        this.instance = FirebaseFirestore.getInstance();
        this.imageRepository = new ImageRepository();
        this.userAuth = FirebaseAuth.getInstance();
    }

    public void addDestination(Destination destination, Uri uri, List<Uri> listUri,ImageView imageView, ContentResolver contentResolver){

        Map<String, Object> data = destinationToMap(destination);
        instance.collection(Destination.collectioName)
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        imageRepository.uploadImageCover(uri, documentReference.getId(), imageView, contentResolver, new ImageDataCallback(){
                            @Override
                            public void onSuccess() {
                                imageRepository.batchUploadImages(listUri, documentReference.getId(), contentResolver, new BatchUploadCallback() {
                                    @Override
                                    public void onSuccess(List<ImageGallery> downloadUrls) {

                                        imageRepository.uploadBatch(downloadUrls);
                                        Messenger.showAlertDialog(context,
                                                "Tourist Destination",
                                                "Tourist Destination added successfully!",
                                                "Ok").show();
                                    }

                                    @Override
                                    public void onFailure(Exception exception) {
                                        Messenger.showAlertDialog(context,
                                                "Image Upload",
                                                "Failed to upload the image gallery: " + exception.getMessage(),
                                                "Ok").show();
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Exception exception) {
                                Messenger.showAlertDialog(context,
                                        "Image Upload",
                                        "Failed to upload the image cover: " + exception.getMessage(),
                                        "Ok").show();
                            }
                        });
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

    public void getDestination(final DestinationDataCallback<Destination> callback){
        List<Destination> list = new ArrayList<>();

        instance.collection(Destination.collectioName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getString(Destination.nameField));
                                Destination destination = documentToDestination(document);
                                list.add(destination);
                            }

                            if (callback != null) {
                                callback.onDataLoaded(list);
                                Log.d(TAG, "success: ", task.getException());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            if (callback != null) {
                                callback.onDataNotAvailable();
                            }
                        }
                    }
                });
    }

    public void getMyDestination(final DestinationDataCallback<Destination> callback){
        List<Destination> list = new ArrayList<>();

        instance.collection(Destination.collectioName)
                .whereEqualTo(Destination.userIdField, userAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getString(Destination.nameField));
                                Destination destination = documentToDestination(document);
                                list.add(destination);
                            }

                            if (callback != null) {
                                callback.onDataLoaded(list);
                                Log.d(TAG, "success: ", task.getException());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            if (callback != null) {
                                callback.onDataNotAvailable();
                            }
                        }
                    }
                });
    }

    public void getDestination(List<Favorite> favorites, final DestinationDataCallback<Destination> callback) {
        List<Destination> list = new ArrayList<>();
        List<Task<DocumentSnapshot>> tasks = new ArrayList<>();

        for (Favorite favorite : favorites) {
            Task<DocumentSnapshot> task = instance.collection(Destination.collectioName)
                    .document(favorite.getDestinationId())
                    .get();

            tasks.add(task);
        }

        Tasks.whenAllComplete(tasks)
                .addOnCompleteListener(overallTask -> {
                    for (Task<DocumentSnapshot> individualTask : tasks) {
                        if (individualTask.isSuccessful()) {
                            DocumentSnapshot document = individualTask.getResult();
                            if (document.exists()) {
                                Log.d(TAG, document.getString(Destination.nameField));
                                Destination destination = documentToDestination(document);
                                list.add(destination);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", individualTask.getException());
                        }
                    }

                    if (callback != null) {
                        callback.onDataLoaded(list);
                        Log.d(TAG, "success");
                    }
                });
    }

    public void deleteDestination(String destinationId)
    {
        instance.collection(Destination.collectioName).document(destinationId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Messenger.showAlertDialog(context,
                                "Delete Destination",
                                "Destination delete successfully!",
                                "Ok").show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Messenger.showAlertDialog(context,
                                "Delete Destination",
                                "Destination delete unsuccessfully!",
                                "Ok").show();
                    }
                });
    }




    private Map<String, Object> destinationToMap(Destination destination) {
        Map<String, Object> destinationMap = new HashMap<>();
        destinationMap.put(Destination.userIdField, destination.getUserId());

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
        String destinationId = document.getId();
        String userId = document.getString(Destination.userIdField);

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

        return new Destination(destinationId, userId, name, description, address, contactNumber,
                websiteUrl, facebookPage, instagramPage, emailAddress, datePublished, lastUpdate);
    }
    private Destination documentToDestination(DocumentSnapshot document) {
        String destinationId = document.getId();
        String userId = document.getString(Destination.userIdField);
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

        return new Destination(destinationId, userId, name, description, address, contactNumber,
                websiteUrl, facebookPage, instagramPage, emailAddress, datePublished, lastUpdate);
    }

}
