package io.github.rmmc.rmmctourism.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

/**
 * Model class representing a destination.
 * Implements Parcelable to allow passing instances between activities.
 */
public class Destination implements Parcelable {

    // Constants for Firestore collection and field names
    public static final String collectioName = "Destinations";
    public static final String userIdField = "userId";
    public static final String nameField = "name";
    public static final String descriptionField = "description";
    public static final String addressField = "address";
    public static final String contactNumberField = "contactNumber";
    public static final String websiteUrlField = "websiteUrl";
    public static final String facebookPageField = "facebookPage";
    public static final String instagramPageField = "instagramPage";
    public static final String emailAddressField = "emailAddress";
    public static final String datePublishedField = "datePublished";
    public static final String lastUpdateField = "lastUpdate";

    private String destinationId;
    private String userId;
    private String name;
    private String description;
    private String address;
    private String contactNumber;
    private String websiteUrl;
    private String facebookPage;
    private String instagramPage;
    private String emailAddress;
    private Timestamp datePublished;
    private Timestamp lastUpdate;

    // Empty constructor required for Firestore
    public Destination() {
    }

    // Constructor for creating a new destination
    public Destination(String userId, String name, String description, String address,
                       String contactNumber, String websiteUrl, String facebookPage,
                       String instagramPage, String emailAddress, Timestamp datePublished,
                       Timestamp lastUpdate) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.contactNumber = contactNumber;
        this.websiteUrl = websiteUrl;
        this.facebookPage = facebookPage;
        this.instagramPage = instagramPage;
        this.emailAddress = emailAddress;
        this.datePublished = datePublished;
        this.lastUpdate = lastUpdate;
    }

    // Constructor for updating an existing destination
    public Destination(String destinationId, String userId, String name, String description,
                       String address, String contactNumber, String websiteUrl,
                       String facebookPage, String instagramPage, String emailAddress,
                       Timestamp datePublished, Timestamp lastUpdate) {
        this.destinationId = destinationId;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.contactNumber = contactNumber;
        this.websiteUrl = websiteUrl;
        this.facebookPage = facebookPage;
        this.instagramPage = instagramPage;
        this.emailAddress = emailAddress;
        this.datePublished = datePublished;
        this.lastUpdate = lastUpdate;
    }

    // Parcelable creator
    public static final Creator<Destination> CREATOR = new Creator<Destination>() {
        @Override
        public Destination createFromParcel(Parcel in) {
            return new Destination();
        }

        @Override
        public Destination[] newArray(int size) {
            return new Destination[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(destinationId);
        parcel.writeString(userId);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(address);
        parcel.writeString(contactNumber);
        parcel.writeString(websiteUrl);
        parcel.writeString(facebookPage);
        parcel.writeString(instagramPage);
        parcel.writeString(emailAddress);
        parcel.writeParcelable(datePublished, i);
        parcel.writeParcelable(lastUpdate, i);
    }
}
