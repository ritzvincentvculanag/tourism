package io.github.rmmc.rmmctourism.util;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

/**
 * Utility class for extracting information from UI components.
 */
public class Miner {

    /**
     * Extracts the text from a TextInputLayout.
     *
     * @param field The TextInputLayout from which to extract text
     * @return The text extracted from the TextInputLayout
     */
    public static String getString(TextInputLayout field) {
        // Use Objects.requireNonNull to get the non-null EditText from the TextInputLayout
        return Objects.requireNonNull(field.getEditText()).getText().toString();
    }
}
