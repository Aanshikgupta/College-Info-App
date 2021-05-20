package com.example.collegeinfoapp.LoginSetup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.collegeinfoapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextInputLayout email, password, repeatpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        repeatpassword = findViewById(R.id.passwordrepeat);


        email.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setErrorEnabled(false);

            }
        });

        password.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.setErrorEnabled(false);
            }
        });

        repeatpassword.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatpassword.setErrorEnabled(false);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth != null) {
            //handling already login user
        }
    }

    //Loginbackbutton
    public void MoveToLoginScreen(View view) {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }




    //back
    public void goback(View view) {
        finish();
    }

    public void SignUpClicked(View view) {
        String emailget, passwordget, repeatpasswordget;
        emailget = email.getEditText().getText().toString();
        passwordget = password.getEditText().getText().toString();
        repeatpasswordget = repeatpassword.getEditText().getText().toString();
        email.setErrorEnabled(false);
        if(!validateEmailPassword(emailget,passwordget,repeatpasswordget))
            return;
        Toast.makeText(SignUpActivity.this, "Please wait..", Toast.LENGTH_SHORT).show();
        mAuth.createUserWithEmailAndPassword(emailget, passwordget).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);


                } else {
                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    boolean validateEmailPassword(String emailget,String passwordget,String repeatpasswordget){
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if(emailget==null || passwordget==null || repeatpasswordget==null ){
            if(emailget==null)
                email.setError("This field cannot be empty.");
            if(passwordget==null)
                password.setError("This field cannot be empty.");
            if(repeatpasswordget==null)
                repeatpassword.setError("This field cannot be empty.");

            return false;
        }
        if(!(emailget.matches(EMAIL_PATTERN)))
        {
            email.setError("Please give correct email.");
            return false;
        }
        if(passwordget.length()<8){
            password.setError("Passwords must be of min 8 characters.");
            return false;
        }
        if(!passwordget.equals(repeatpasswordget)){
            Toast.makeText(SignUpActivity.this,"Passwords must be the same.",Toast.LENGTH_LONG).show();
            repeatpassword.setError("Passwords must be the same.");
            return false;
        }
        return true;
    }
}