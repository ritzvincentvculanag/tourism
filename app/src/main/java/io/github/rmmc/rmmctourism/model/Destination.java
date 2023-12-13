package io.github.rmmc.rmmctourism.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

public class Destination implements Parcelable {

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

    public Destination() {
    }

    public Destination(String userId, String name, String description, String address, String contactNumber, String websiteUrl, String facebookPage, String instagramPage, String emailAddress, Timestamp datePublished, Timestamp lastUpdate) {
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

    public Destination(String destinationId, String userId, String name, String description, String address, String contactNumber, String websiteUrl, String facebookPage, String instagramPage, String emailAddress, Timestamp datePublished, Timestamp lastUpdate) {
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

    protected Destination(Parcel in) {
        destinationId = in.readString();
        userId = in.readString();
        name = in.readString();
        description = in.readString();
        address = in.readString();
        contactNumber = in.readString();
        websiteUrl = in.readString();
        facebookPage = in.readString();
        instagramPage = in.readString();
        emailAddress = in.readString();
        datePublished = in.readParcelable(Timestamp.class.getClassLoader());
        lastUpdate = in.readParcelable(Timestamp.class.getClassLoader());
    }

    public static final Creator<Destination> CREATOR = new Creator<Destination>() {
        @Override
        public Destination createFromParcel(Parcel in) {
            return new Destination(in);
        }

        @Override
        public Destination[] newArray(int size) {
            return new Destination[size];
        }
    };

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getFacebookPage() {
        return facebookPage;
    }

    public void setFacebookPage(String facebookPage) {
        this.facebookPage = facebookPage;
    }

    public String getInstagramPage() {
        return instagramPage;
    }

    public void setInstagramPage(String instagramPage) {
        this.instagramPage = instagramPage;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Timestamp getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Timestamp datePublished) {
        this.datePublished = datePublished;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

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
