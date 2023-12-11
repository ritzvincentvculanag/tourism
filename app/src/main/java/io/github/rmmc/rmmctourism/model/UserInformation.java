package io.github.rmmc.rmmctourism.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

import java.time.LocalDate;

public class UserInformation implements Parcelable {

    public static final String collectionName = "userInformation";
    public static final String firstNameField = "firstName";
    public static final String lastNameField = "lastName";
    public static final String middleNameField = "middleName";
    public static final String birthDateField = "birthDate";
    public static final  String genderField = "gender";
    public static final String emailField = "email";
    public static final String passwordField = "password";
    public static final String dateRegisteredField = "dataRegistered";
    public static final String lastUpdatedField = "lastUpdated";
    private String UID;
    private int accountType;
    private String firstName;
    private String lastName;
    private String middleName;
    private Timestamp birthDate;
    private String gender;
    private String email;
    private String password;
    private Timestamp dateRegistered;
    private Timestamp lastUpdated;

    public UserInformation(){}

    public UserInformation(
                           int accountType,
                           String firstName,
                           String lastName,
                           String middleName,
                           Timestamp birthDate,
                           String gender,
                           String email,
                           String password,
                           Timestamp dateRegistered,
                           Timestamp lastUpdated) {
        this.accountType = accountType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.dateRegistered = dateRegistered;
        this.lastUpdated = lastUpdated;
    }

    protected UserInformation(Parcel in) {
        UID = in.readString();
        accountType = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        middleName = in.readString();
        birthDate = in.readParcelable(Timestamp.class.getClassLoader());
        gender = in.readString();
        email = in.readString();
        password = in.readString();
        dateRegistered = in.readParcelable(Timestamp.class.getClassLoader());
        lastUpdated = in.readParcelable(Timestamp.class.getClassLoader());
    }

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

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
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

    public Timestamp getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Timestamp dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(UID);
        parcel.writeInt(accountType);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(middleName);
        parcel.writeParcelable(birthDate, i);
        parcel.writeString(gender);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeParcelable(dateRegistered, i);
        parcel.writeParcelable(lastUpdated, i);
    }
}
