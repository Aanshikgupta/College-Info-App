package com.example.collegeinfoapp.LoginSetup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.collegeinfoapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class ForgotPassword extends AppCompatActivity {

    private View view;
    private TextInputLayout resetemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        resetemail=findViewById(R.id.resetemail);
    }


    //On button click send password reset link ot email
    public void sendVerificationlink(View view) {

        FirebaseAuth mAuth;
        mAuth=FirebaseAuth.getInstance();
        String email=resetemail.getEditText().getText().toString().trim();

        //validation
        if(!validate(email))
            return;



        mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ForgotPassword.this,"Reset link successfully sent",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(view.getContext(),LoginActivity.class);
                startActivity(intent);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(ForgotPassword.this,"Error! Reset link not sent.",Toast.LENGTH_LONG).show();

            }
        });
    }

    public boolean validate(String email){
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if(resetemail==null) {
            resetemail.setError("This field cannot be empty.");
            return false;
        }
        if(!(email.matches(EMAIL_PATTERN)))
        {
            resetemail.setError("Please give correct email.");
            return false;
        }
        return true;
    }

    //back button
    public void goToBack(View view) {
        finish();
    }
}