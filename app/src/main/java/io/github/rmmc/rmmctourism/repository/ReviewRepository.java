package io.github.rmmc.rmmctourism.repository;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.model.Favorite;
import io.github.rmmc.rmmctourism.model.Review;
import io.github.rmmc.rmmctourism.model.UserInformation;
import io.github.rmmc.rmmctourism.util.DataCallBack;
import io.github.rmmc.rmmctourism.util.Messenger;
import io.github.rmmc.rmmctourism.util.OnReviewDataCallback;
import io.github.rmmc.rmmctourism.views.DestinationDetail;

public class ReviewRepository {

    private Context context;
    private FirebaseAuth userAuth;
    private FirebaseFirestore instance;
    public ReviewRepository(Context context){
        this.context = context;
        this.instance = FirebaseFirestore.getInstance();
        this.userAuth = FirebaseAuth.getInstance();
    }

    public void addReview(Review review){
        instance.collection(Review.collectionName).add(reviewToMap(review)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Messenger.showAlertDialog(context,
                        "Tourist Destination Review",
                        "Tourist Destination review added successfully!",
                        "Ok").show();

            }
        });
    }

    public void getReview(Destination destination, OnReviewDataCallback reviewDataCallback){
        instance.collection(Review.collectionName)
                .whereEqualTo(Review.destinationIdField, destination.getDestinationId())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Review> reviews = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Review review = document.toObject(Review.class);
                        reviews.add(review);
                    }
                    if (reviewDataCallback != null) {
                        reviewDataCallback.onSuccess(reviews);
                    }
                })
                .addOnFailureListener(e -> {
                    if (reviewDataCallback != null) {
                        reviewDataCallback.onFailure();
                    }
                });
    }

    public void getFullName(OnReviewDataCallback<UserInformation> onReviewDataCallback){
        instance.collection(UserInformation.collectionName)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<UserInformation> userInfos = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        userInfos.add(documentToUserInformation(document));
                    }
                    if (onReviewDataCallback != null) {
                        onReviewDataCallback.onSuccess(userInfos);
                    }
                })
                .addOnFailureListener(e -> {
                    if (onReviewDataCallback != null) {
                        onReviewDataCallback.onFailure();
                    }
                });
    }
    private UserInformation documentToUserInformation(QueryDocumentSnapshot queryDocumentSnapshot){
        UserInformation userInformation = new UserInformation();
        userInformation.setUID(queryDocumentSnapshot.getId());
        userInformation.setFirstName(queryDocumentSnapshot.getString(UserInformation.firstNameField));
        userInformation.setLastName(queryDocumentSnapshot.getString(UserInformation.lastNameField));
        userInformation.setMiddleName(queryDocumentSnapshot.getString(UserInformation.middleNameField));
        return userInformation;
    }

    private Map<String, Object> reviewToMap(Review review) {
        Map<String, Object> reviewMap = new HashMap<>();
        reviewMap.put(Review.userIdField, review.getUserId());
        reviewMap.put(Review.destinationIdField, review.getDestinationId());
        reviewMap.put(Review.contentField, review.getContent());
        reviewMap.put(Review.datePublishedField, review.getDatePublished());
        return reviewMap;
    }
}
