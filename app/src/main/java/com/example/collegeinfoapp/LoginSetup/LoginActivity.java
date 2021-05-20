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
        progressBar=findViewById(R.id.progress);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if(mAuth.getCurrentUser()!=null) {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        }
//    }




    public void login(View view) {
        progressBar.setVisibility(View.VISIBLE);
        String auemail, aupassword;
        auemail = email.getEditText().getText().toString().trim();
        aupassword = password.getEditText().getText().toString().trim();
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (email == null) {
            email.setError("This field cannot be empty.");
            return;
        }
        if(password==null){
            password.setError("This field cannot be empty.");
            return;
        }
        if (!(auemail.matches(EMAIL_PATTERN))) {
            email.setError("Please give correct email.");
            return;
        }
        Toast.makeText(LoginActivity.this, "Please wait...", Toast.LENGTH_LONG).show();
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

    public void MoveToSignUpScreen(View view) {
        Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
        startActivity(intent);
    }

    public void MoveToForgetPassword(View view) {
        Intent intent=new Intent(LoginActivity.this,ForgotPassword.class);
        startActivity(intent);
    }
}