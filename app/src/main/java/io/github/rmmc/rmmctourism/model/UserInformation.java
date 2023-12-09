package io.github.rmmc.rmmctourism.model;

import com.google.firebase.Timestamp;

import java.time.LocalDate;

public class UserInformation {

    public static final String collectionName = "userInformation";
    public static final String accountTypeField = "accountType";
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
}
