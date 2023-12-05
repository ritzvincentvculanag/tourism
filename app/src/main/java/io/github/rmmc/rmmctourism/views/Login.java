 package io.github.rmmc.rmmctourism.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.util.Validator;

 public class Login extends AppCompatActivity {


    private TextInputLayout tfUsername;
    private TextInputLayout tfPassword;
    private Button btnLogin;
    private Button btnRegister;

    private FirebaseAuth userAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tfUsername = findViewById(R.id.tf_username);
        tfPassword = findViewById(R.id.tf_password);

        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        btnLogin.setOnClickListener(this::loginListener);

        userAuth = FirebaseAuth.getInstance();

        if(userAuth.getCurrentUser() != null){
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }
    private void loginListener(View view){

        TextInputLayout[] fields = {tfUsername, tfPassword};

        if(!Validator.fieldsAreEmpty(fields)){

            String email = tfUsername.getEditText().getText().toString();
            String password = tfPassword.getEditText().getText().toString();

            userAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task ->{
                        if(task.isSuccessful()){
                            startActivity(new Intent(this, HomeActivity.class));
                            finish();
                        }else{

                        }
                    });
        }
    }
}