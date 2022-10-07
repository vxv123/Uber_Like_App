package com.cepang97.uber_like_app.controller;


import androidx.annotation.NonNull;

import com.cepang97.uber_like_app.view.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;

public class SignupHandler {
    public FirebaseAuth mAuth;

    //create firebase account for new user
    public void create_account(String email_address, String password){
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email_address, password).addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

            }
        });
    }

}
