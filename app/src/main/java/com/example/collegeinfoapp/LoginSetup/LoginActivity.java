package com.example.collegeinfoapp.LoginSetup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeinfoapp.MainActivity;
import com.example.collegeinfoapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextInputLayout email, password;
    TextView forget;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progress);

        //to handle visibility of errors in edit texts
        handlingEditTexts();

    }

    //handling visibillity of errors
    void handlingEditTexts(){
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

    }


    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()!=null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }




    public void login(View view) {

        //again set email error to false
        email.setErrorEnabled(false);
        password.setErrorEnabled(false);

        //accessing values of email and password
        String auemail, aupassword;
        auemail = email.getEditText().getText().toString().trim();
        aupassword = password.getEditText().getText().toString().trim();

        //calling validation method to validate email and password
        if(!validateEverything(auemail,aupassword))
            return;
        //showing progress bar and telling user to wait
        progressBar.setVisibility(View.VISIBLE);
        Toast.makeText(LoginActivity.this, "Please wait...", Toast.LENGTH_LONG).show();

        //Log in with user email and password
        mAuth.signInWithEmailAndPassword(auemail, aupassword)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //method for validation
    public boolean validateEverything(String auemail,String aupassword){

        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (email == null || auemail.length()==0) {
            email.setError("This field cannot be empty.");
            return false;
        }
        if(password==null || aupassword.length()==0){
            password.setError("This field cannot be empty.");
            return false;
        }
        if (!(auemail.matches(EMAIL_PATTERN))) {
            email.setError("Please give correct email.");
            return false;
        }
        return true;
    }

    //method to call SignUpActivity
    public void MoveToSignUpScreen(View view) {
        Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
        startActivity(intent);
    }

    //method to call ForgotPasswordActivity
    public void MoveToForgetPassword(View view) {
        Intent intent=new Intent(LoginActivity.this,ForgotPassword.class);
        startActivity(intent);
    }
}