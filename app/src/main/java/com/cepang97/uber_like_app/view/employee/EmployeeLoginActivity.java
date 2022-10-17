package com.cepang97.uber_like_app.view.employee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.cepang97.uber_like_app.view.customer.CustomerLoginActivity;
import com.cepang97.uber_like_app.view.customer.CustomerMainActivity;

public class EmployeeLoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);
        username = findViewById(R.id.username_in_employee_login);
        password = findViewById(R.id.password_in_employee_login);
        submit = findViewById(R.id.btn_in_employee_login);

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
                Toast.makeText(EmployeeLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            private void onLoginSuccess(AuthSignInResult authSignInResult) {
                Intent intent = new Intent(EmployeeLoginActivity.this, EmployeeMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public boolean input_content_checking(EditText username, EditText password){
        if(TextUtils.isEmpty(username.getText())){
            Toast.makeText(EmployeeLoginActivity.this, "Username is empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(password.getText())){
            Toast.makeText(EmployeeLoginActivity.this, "Password is empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}