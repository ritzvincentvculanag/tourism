package io.github.rmmc.rmmctourism.model;

import java.time.LocalDate;

public class UserInformation {

    public static final String collectionName = "userInformation";

    public static final String firstNameField = "firstName";
    public static final String lastNameField = "lastName";
    public static final String middleNameField = "middleName";
    public static final String birthDateField = "birthDate";
    public static final  String genderField = "gender";
    public static final String emailField = "email";
    public static final String passwordField = "password";
    private String UID;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate birthDate;
    private String gender;
    private String email;
    private String password;

    public UserInformation(){}

    public UserInformation(String UID, String firstName, String lastName, String middleName, LocalDate birthDate, String gender, String email, String password) {
        this.UID = UID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }

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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
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
}
