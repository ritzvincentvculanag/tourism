package io.github.rmmc.rmmctourism.util;

import android.util.Patterns;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class Validator {

    public static boolean isValidName(TextInputLayout tilName) {
        String name = tilName.getEditText().getText().toString().trim();
        if (name.matches("^[a-zA-Z ]+$")) {
            return true;
        }
        return false;
    }

    public static boolean isValidEmail(TextInputLayout tilEmail) {
        String email = tilEmail.getEditText().getText().toString().trim();
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }
        return false;
    }

    public static boolean isValidPassword(TextInputLayout tilPassword) {
        String password = tilPassword.getEditText().getText().toString().trim();

        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

        if (password.matches(passwordPattern)) {
            return true;
        }
        return false;
    }

    public static boolean fieldIsEmpty(TextInputLayout field) {
        return Objects.requireNonNull(field.getEditText()).getText().toString().isEmpty();
    }

    public static boolean fieldsAreEmpty(TextInputLayout ... fields){
        for(TextInputLayout field: fields){
            if (fieldIsEmpty(field)) {
                field.setErrorEnabled(true);
                return true;
            }
            field.setErrorEnabled(false);
        }

        return false;
    }

    public static boolean isPhoneNumberValid(TextInputLayout field){
        String philippinesPhoneNumberPattern = "^09\\d{9}$";
        if (field.getEditText().getText().toString().matches(philippinesPhoneNumberPattern)) {
            field.setError(null);
            return true;
        }
        return false;
    }

    public static boolean areAllUrlsValid(TextInputLayout... fields) {
        boolean allValid = true;

        for (TextInputLayout field : fields) {
            String url = field.getEditText().getText().toString().trim();
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
