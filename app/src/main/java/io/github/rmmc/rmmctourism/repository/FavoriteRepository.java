package io.github.rmmc.rmmctourism.repository;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.rmmc.rmmctourism.model.Favorite;
import io.github.rmmc.rmmctourism.util.Messenger;
import io.github.rmmc.rmmctourism.util.OnFavoriteDataCallback;
import io.github.rmmc.rmmctourism.util.OnViewFavoriteCallback;

public class FavoriteRepository {

    private Context context;
    private FirebaseFirestore instance;
    private FirebaseAuth userAuth;

    // Constructor
    public FavoriteRepository(Context context) {
        this.context = context;
        this.instance = FirebaseFirestore.getInstance();
        this.userAuth = FirebaseAuth.getInstance();
    }

    // Method to add a new favorite
    public void addFavorite(Favorite favorite) {
        // Create a query to check if the favorite already exists
        Query query = instance.collection(Favorite.collectionName)
                .whereEqualTo("userId", favorite.getUserId())
                .whereEqualTo("destinationId", favorite.getDestinationId());

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    // Document with the same userId and destinationId already exists
                    Messenger.showAlertDialog(context,
                            "Tourist Destination",
                            "Tourist Destination is already in favorite!",
                            "Ok").show();
                } else {
                    // No existing document found, add the new favorite
                    instance.collection(Favorite.collectionName)
                            .add(reviewToMap(favorite))
                            .addOnSuccessListener(documentReference -> {
                                Messenger.showAlertDialog(context,
                                        "Tourist Destination",
                                        "Tourist Destination added to favorite!",
                                        "Ok").show();
                            })
                            .addOnFailureListener(e -> {
                                Messenger.showAlertDialog(context,
                                        "Tourist Destination",
                                        "Tourist Destination failed to add in favorite!",
                                        "Ok").show();
                            });
                }
            } else {
                Messenger.showAlertDialog(context,
                        "Tourist Destination",
                        "Error checking favorite existence!",
                        "Ok").show();
            }
        });
    }

    // Method to remove a favorite
    public void removeFavorite(Favorite favorite) {
        instance.collection(Favorite.collectionName)
                .whereEqualTo(Favorite.userIdField, favorite.getUserId())
                .whereEqualTo(Favorite.destinationField, favorite.getDestinationId())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                            document.getReference().delete()
                                    .addOnSuccessListener(aVoid -> {
                                        Messenger.showAlertDialog(context,
                                                "Tourist Destination",
                                                "Tourist Destination removed from favorite!",
                                                "Ok").show();
                                    })
                                    .addOnFailureListener(e -> {
                                        Messenger.showAlertDialog(context,
                                                "Tourist Destination",
                                                "Failed to remove Tourist Destination from favorite!",
                                                "Ok").show();
                                    });
                        } else {
                            // No matching document found
                            Messenger.showAlertDialog(context,
                                    "Tourist Destination",
                                    "Favorite not found!",
                                    "Ok").show();
                        }
                    } else {
                        // Handle query failure
                        Messenger.showAlertDialog(context,
                                "Tourist Destination",
                                "Failed to query favorites!",
                                "Ok").show();
                    }
                });
    }

    // Method to get all user favorites
    public void getFavorite(OnFavoriteDataCallback dataCallBack) {
        instance.collection(Favorite.collectionName)
                .whereEqualTo(Favorite.userIdField, userAuth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Favorite> favorites = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Favorite favorite = document.toObject(Favorite.class);
                        favorites.add(favorite);
                    }

                    if (dataCallBack != null) {
                        dataCallBack.onSuccess(favorites);
                    }
                })
                .addOnFailureListener(e -> {
                    if (dataCallBack != null) {
                        dataCallBack.onFailure(e);
                    }
                });
    }

    // Method to check if a destination is a favorite for the current user
    public void getFavorite(String destinationId, OnViewFavoriteCallback dataCallBack) {
        instance.collection(Favorite.collectionName)
                .whereEqualTo(Favorite.userIdField, userAuth.getCurrentUser().getUid())
                .whereEqualTo(Favorite.destinationField, destinationId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                    if (!documents.isEmpty()) {
                        Favorite favorite = documentToFavorite(documents.get(0));
                        if (dataCallBack != null) {
                            dataCallBack.onSuccess(favorite);
                        }
                    } else {
                        if (dataCallBack != null) {
                            dataCallBack.onFailure();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    if (dataCallBack != null) {
                        dataCallBack.onFailure();
                    }
                });
    }

    // Helper method to convert Favorite object to a map
    private Map<String, Object> reviewToMap(Favorite favorite) {
        Map<String, Object> map = new HashMap<>();
        map.put(Favorite.userIdField, favorite.getUserId());
        map.put(Favorite.destinationField, favorite.getDestinationId());
        return map;
    }

    // Helper method to convert Firestore document to Favorite object
    private Favorite documentToFavorite(DocumentSnapshot document) {
        String uid = document.getId();
        String userId = document.getString(Favorite.userIdField);
        String destinationId = document.getString(Favorite.destinationField);
        return new Favorite(uid, userId, destinationId);
    }
}
