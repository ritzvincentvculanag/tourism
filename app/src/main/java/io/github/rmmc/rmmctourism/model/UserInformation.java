package io.github.rmmc.rmmctourism.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

/**
 * Model class representing user information.
 */
public class UserInformation implements Parcelable {

    // Constants for Firestore collection and field names
    public static final String collectionName = "userInformation";
    public static final String firstNameField = "firstName";
    public static final String lastNameField = "lastName";
    public static final String middleNameField = "middleName";
    public static final String birthDateField = "birthDate";
    public static final String genderField = "gender";
    public static final String emailField = "email";
    public static final String passwordField = "password";

    private String UID;  // User ID associated with this information

    private String firstName;
    private String lastName;
    private String middleName;
    private Timestamp birthDate;  // Timestamp indicating the birth date
    private String gender;
    private String email;
    private String password;

    // Default constructor required for Firestore
    public UserInformation() {

    }

    /**
     * Constructor for creating user information.
     *
     * @param firstName First name of the user.
     * @param lastName  Last name of the user.
     * @param middleName Middle name of the user.
     * @param birthDate Timestamp indicating the birth date of the user.
     * @param gender    Gender of the user.
     * @param email     Email address of the user.
     * @param password  Password of the user.
     */
    public UserInformation(
            String firstName,
            String lastName,
            String middleName,
            Timestamp birthDate,
            String gender,
            String email,
            String password
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }

    /**
     * Parcelable constructor for reading from a Parcel.
     */
    protected UserInformation(Parcel in) {
        UID = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        middleName = in.readString();
        birthDate = in.readParcelable(Timestamp.class.getClassLoader());
        gender = in.readString();
        email = in.readString();
        password = in.readString();
    }

    // Parcelable creator
    public static final Creator<UserInformation> CREATOR = new Creator<UserInformation>() {
        @Override
        public UserInformation createFromParcel(Parcel in) {
            return new UserInformation(in);
        }

        @Override
        public UserInformation[] newArray(int size) {
            return new UserInformation[size];
        }
    };

    // Getter and setter methods for each field

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Timestamp getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Parcelable methods

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(UID);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(middleName);
        parcel.writeParcelable(birthDate, i);
        parcel.writeString(gender);
        parcel.writeString(email);
        parcel.writeString(password);
    }
}
