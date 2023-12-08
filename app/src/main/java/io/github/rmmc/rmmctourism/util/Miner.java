package io.github.rmmc.rmmctourism.util;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class Miner {

    public static String getString(TextInputLayout field) {
        return Objects.requireNonNull(field.getEditText()).getText().toString();
    }

}
