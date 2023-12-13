package io.github.rmmc.rmmctourism.util;

import android.util.Patterns;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

/**
 * Utility class for input validation.
 */
public class Validator {

    /**
     * Validates if the provided name is valid.
     *
     * @param tilName TextInputLayout containing the name input
     * @return true if the name is valid, false otherwise
     */
    public static boolean isValidName(TextInputLayout tilName) {
        String name = Objects.requireNonNull(tilName.getEditText()).getText().toString().trim();
        if (name.matches("^[a-zA-Z ]+$")) {
            return true;
        }
        return false;
    }

    /**
     * Validates if the provided email is valid.
     *
     * @param tilEmail TextInputLayout containing the email input
     * @return true if the email is valid, false otherwise
     */
    public static boolean isValidEmail(TextInputLayout tilEmail) {
        String email = Objects.requireNonNull(tilEmail.getEditText()).getText().toString().trim();
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }
        return false;
    }

    /**
     * Validates if the provided password is valid.
     *
     * @param tilPassword TextInputLayout containing the password input
     * @return true if the password is valid, false otherwise
     */
    public static boolean isValidPassword(TextInputLayout tilPassword) {
        String password = Objects.requireNonNull(tilPassword.getEditText()).getText().toString().trim();

        // Password pattern with specific requirements
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

        if (password.matches(passwordPattern)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if a TextInputLayout field is empty.
     *
     * @param field TextInputLayout field to check
     * @return true if the field is empty, false otherwise
     */
    public static boolean fieldIsEmpty(TextInputLayout field) {
        return Objects.requireNonNull(field.getEditText()).getText().toString().isEmpty();
    }

    /**
     * Checks if multiple TextInputLayout fields are empty.
     *
     * @param fields TextInputLayout fields to check
     * @return true if any field is empty, false otherwise
     */
    public static boolean fieldsAreEmpty(TextInputLayout... fields) {
        for (TextInputLayout field : fields) {
            if (fieldIsEmpty(field)) {
                field.setErrorEnabled(true);
                return true;
            }
            field.setErrorEnabled(false);
        }
        return false;
    }

    /**
     * Validates if the provided phone number is a valid Philippines phone number.
     *
     * @param field TextInputLayout containing the phone number input
     * @return true if the phone number is valid, false otherwise
     */
    public static boolean isPhoneNumberValid(TextInputLayout field) {
        String philippinesPhoneNumberPattern = "^09\\d{9}$";
        if (Objects.requireNonNull(field.getEditText()).getText().toString().matches(philippinesPhoneNumberPattern)) {
            field.setError(null);
            return true;
        }
        return false;
    }

    /**
     * Validates if multiple TextInputLayout fields contain valid URLs.
     *
     * @param fields TextInputLayout fields containing URLs to check
     * @return true if all URLs are valid, false otherwise
     */
    public static boolean areAllUrlsValid(TextInputLayout... fields) {
        boolean allValid = true;

        for (TextInputLayout field : fields) {
            String url = Objects.requireNonNull(field.getEditText()).getText().toString().trim();
            if (!url.isEmpty()) {
                if (!Patterns.WEB_URL.matcher(url).matches()) {
                    // Invalid URL
                    field.setError("Invalid URL");
                    allValid = false;
                } else {
                    // Valid URL, clear any previous error
                    field.setError(null);
                }
            }
        }
        return allValid;
    }
}
