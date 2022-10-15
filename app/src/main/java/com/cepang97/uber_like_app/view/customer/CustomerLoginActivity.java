package com.cepang97.uber_like_app.view.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.result.AuthSignInResult;
import com.amplifyframework.core.Amplify;
import com.cepang97.uber_like_app.R;

public class CustomerLoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);
        username = findViewById(R.id.username_in_login);
        password = findViewById(R.id.password_in_login);
        submit = findViewById(R.id.btn_login);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input_content_checking(username, password)){
                    Amplify.Auth.signIn(
                            username.getText().toString(),
                            password.getText().toString(),
                            this::onLoginSuccess,
                            this::onLoginError

                    );
                }
            }

            private void onLoginError(AuthException e) {
            }

            private void onLoginSuccess(AuthSignInResult authSignInResult) {

            }
        });
    }

    public boolean input_content_checking(EditText username, EditText password){
        if(TextUtils.isEmpty(username.getText())){
            Toast.makeText(CustomerLoginActivity.this, "Username is empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(password.getText())){
            Toast.makeText(CustomerLoginActivity.this, "Password is empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}