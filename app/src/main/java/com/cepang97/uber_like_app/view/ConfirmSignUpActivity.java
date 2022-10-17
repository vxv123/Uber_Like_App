package com.cepang97.uber_like_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.result.AuthSignInResult;
import com.amplifyframework.auth.result.AuthSignUpResult;
import com.amplifyframework.core.Amplify;
import com.cepang97.uber_like_app.R;

/**
 * Confirm Account Sign Up Successfully first
 * Then sign in to Amplify,
 * Then based on different user type, goes to different main activities.
 */
public class ConfirmSignUpActivity extends AppCompatActivity  implements View.OnClickListener {
    Button submit;
    EditText confirmed_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_sign_up);
        submit = findViewById(R.id.submit_in_confirm_signup);
        confirmed_code = findViewById(R.id.confirmed_code_in_confirm_signup);

        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String code = confirmed_code.getText().toString();
        String email_address = getIntent().getStringExtra("email_address");
        Amplify.Auth.confirmSignUp(
                email_address,
                code,
                this::onSignUpSuccess,
                this::onFail
        );
    }

    private void onFail(AuthException e) {
        Toast.makeText(ConfirmSignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }


    private void onSignUpSuccess(AuthSignUpResult authSignUpResult) {
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Amplify.Auth.signIn(username, password, this::onLoginSuccess, this::onFail);
    }

    //Store data into DataBase and jump to corresponding page.
    private void onLoginSuccess(AuthSignInResult authSignInResult) {

    }
}
