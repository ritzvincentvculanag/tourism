package io.github.rmmc.rmmctourism.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * Model class representing a user's favorite destination.
 * Implements Parcelable to allow passing instances between activities.
 */
public class Favorite implements Parcelable {

    // Constants for Firestore collection and field names
    public static final String collectionName = "Favorite";
    public static final String userIdField = "userId";
    public static final String destinationField = "destinationId";

    private String favoriteId;
    private String userId;
    private String destinationId;

    // Empty constructor required for Firestore
    public Favorite() {
    }

    // Constructor for creating a new favorite
    public Favorite(String userId, String destinationId) {
        this.userId = userId;
        this.destinationId = destinationId;
    }

    // Constructor for updating an existing favorite
    public Favorite(String favoriteId, String userId, String destinationId) {
        this.favoriteId = favoriteId;
        this.userId = userId;
        this.destinationId = destinationId;
    }

    // Parcelable creator
    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite();
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    // Getter and setter methods for each field
    // ...

    // Parcelable implementation

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(favoriteId);
        parcel.writeString(userId);
        parcel.writeString(destinationId);
    }
}
