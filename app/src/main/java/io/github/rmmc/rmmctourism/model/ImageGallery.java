package io.github.rmmc.rmmctourism.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ImageGallery implements Parcelable {

    public final static String collectionName = "galleryUrl";
    public final static String destinationIdField = "destinationId";
    public final static String urlField = "url";
    private String uid;
    private String destinationId;
    private String url;

    public ImageGallery() {
    }

    public ImageGallery(String destinationId, String url) {
        this.destinationId = destinationId;
        this.url = url;
    }

    public ImageGallery(String uid, String destinationId, String url) {
        this.uid = uid;
        this.destinationId = destinationId;
        this.url = url;
    }

    protected ImageGallery(Parcel in) {
        uid = in.readString();
        destinationId = in.readString();
        url = in.readString();
    }

    public static final Creator<ImageGallery> CREATOR = new Creator<ImageGallery>() {
        @Override
        public ImageGallery createFromParcel(Parcel in) {
            return new ImageGallery(in);
        }

        @Override
        public ImageGallery[] newArray(int size) {
            return new ImageGallery[size];
        }
    };

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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
