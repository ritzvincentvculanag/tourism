package io.github.rmmc.rmmctourism.util;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class FieldsValidetor {

    public static boolean isFieldEmpty(TextInputLayout ... fields){

        for(TextInputLayout field: fields){

            if(field.getEditText().getText().toString().isEmpty()){
                field.setError("Fields is empty!");
                field.setErrorEnabled(true);
                return true;
            }
            field.setError("");
            field.setErrorEnabled(false);
        }

        return false;
    }

}
