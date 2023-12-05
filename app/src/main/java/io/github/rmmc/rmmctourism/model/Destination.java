package io.github.rmmc.rmmctourism.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Destination implements Parcelable {
    // Name for parcelable
    public static final String OBJ_DESTINATION = "OBJ_DESTINATION";

    private final String name;
    private final String description;

    public Destination(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // Parcelable starts here
    protected Destination(Parcel in) {
        name = in.readString();
        description = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
    }
}
