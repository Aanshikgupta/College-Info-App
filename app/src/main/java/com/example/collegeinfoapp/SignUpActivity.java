package com.example.collegeinfoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextInputLayout email,password,repeatpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        repeatpassword=findViewById(R.id.passwordrepeat);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth!=null){
            //handling already login user
        }
    }

    //Loginbackbutton
    public void MoveToLoginScreen(View view) {
        Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
        startActivity(intent);
    }
    
    
    //back
    public void goback(View view) {
        finish();
    }

    public void SignUpClicked(View view) {
        String emailget,passwordget,repeatpasswordget;
        emailget=email.getEditText().getText().toString();
        passwordget=password.getEditText().getText().toString();
        repeatpasswordget=repeatpassword.getEditText().getText().toString();
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if(emailget==null || passwordget==null || repeatpasswordget==null ){
            if(emailget==null)
                email.setError("This field cannot be empty.");
            if(passwordget==null)
                password.setError("This field cannot be empty.");
            if(repeatpasswordget==null)
                repeatpassword.setError("This field cannot be empty.");

            return;
        }
        if(!(emailget.matches(EMAIL_PATTERN)))
        {
            email.setError("Please give correct email.");
            return;
        }
        mAuth.createUserWithEmailAndPassword(emailget,passwordget).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this,"Success",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
                    startActivity(intent);


                }else{
                    Toast.makeText(SignUpActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}