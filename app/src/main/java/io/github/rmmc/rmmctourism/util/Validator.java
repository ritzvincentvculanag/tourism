package io.github.rmmc.rmmctourism.util;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class Validator {

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
}
