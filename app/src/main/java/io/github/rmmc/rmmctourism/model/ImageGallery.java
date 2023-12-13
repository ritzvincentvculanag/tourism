package io.github.rmmc.rmmctourism.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * Model class representing an image in the gallery associated with a destination.
 * Implements Parcelable to allow passing instances between activities.
 */
public class ImageGallery implements Parcelable {

    // Constants for Firestore collection and field names
    public final static String collectionName = "galleryUrl";
    public final static String destinationIdField = "destinationId";
    public final static String urlField = "url";

    private String uid;
    private String destinationId;
    private String url;

    // Empty constructor required for Firestore
    public ImageGallery() {
    }

    // Constructor for creating a new image in the gallery
    public ImageGallery(String destinationId, String url) {
        this.destinationId = destinationId;
        this.url = url;
    }

    // Constructor for updating an existing image in the gallery
    public ImageGallery(String uid, String destinationId, String url) {
        this.uid = uid;
        this.destinationId = destinationId;
        this.url = url;
    }

    // Parcelable creator
    public static final Creator<ImageGallery> CREATOR = new Creator<ImageGallery>() {
        @Override
        public ImageGallery createFromParcel(Parcel in) {
            return new ImageGallery();
        }

        @Override
        public ImageGallery[] newArray(int size) {
            return new ImageGallery[size];
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
        parcel.writeString(uid);
        parcel.writeString(destinationId);
        parcel.writeString(url);
    }
}
