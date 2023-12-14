package io.github.rmmc.rmmctourism.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Favorite implements Parcelable {

    public static final String collectionName = "Favorite";
    public static final String userIdField = "userId";
    public static final String destinationField = "destinationId";
    private String favoriteId;
    private String userId;
    private String destinationId;

    public Favorite() {

    }

    public Favorite(String userId, String destinationId) {
        this.userId = userId;
        this.destinationId = destinationId;
    }

    public Favorite(String favoriteId, String userId, String destinationId) {
        this.favoriteId = favoriteId;
        this.userId = userId;
        this.destinationId = destinationId;
    }

    protected Favorite(Parcel in) {
        favoriteId = in.readString();
        userId = in.readString();
        destinationId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(favoriteId);
        dest.writeString(userId);
        dest.writeString(destinationId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    public String getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(String favoriteId) {
        this.favoriteId = favoriteId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }
}
