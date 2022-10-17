package com.cepang97.uber_like_app.view;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.auth.result.AuthSignUpResult;
import com.amplifyframework.core.Amplify;
import com.cepang97.uber_like_app.R;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    EditText email_address, username, password, confirm_password;
    Button submit;
    CheckBox employee_cb, customer_cb;

    static boolean verification_result = false;
    String email_address_str;
    String username_str;
    String pwd_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email_address = findViewById(R.id.email_address_in_signup);
        username = findViewById(R.id.username_in_signup);
        password = findViewById(R.id.password_in_signup);
        confirm_password = findViewById(R.id.confirm_password_in_signup);
        submit = findViewById(R.id.submit_in_signup);
        employee_cb = findViewById(R.id.employee_checkbox_in_signup);
        customer_cb = findViewById(R.id.customer_checkbox_in_signup);


        //Only make user click employee's click box or customer's click box
        only_one_checkbox_checked(customer_cb, employee_cb);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(blank_content_checking(email_address, username, password, confirm_password, customer_cb, employee_cb)){
                    email_address_str = email_address.getText().toString();
                    username_str = username.getText().toString();
                    pwd_str = password.getText().toString();


                    //Create An account by Using AWS
                    create_account(email_address_str, username_str, pwd_str);

                }
            }
        });
    }

    /**
     * Check if there is a field or more blank, or if checkboxes are both checked or none of them are checked.
     * @param email_address email address of user as EditText
     * @param username user customized name as EditText
     * @param password password as EditText
     * @param confirm_password confirmed password as EditText
     * @param customer customer as Checkbox
     * @param employee employee as Checkbox
     * @return true if all the fields are filled and passwords are matched and only one checkbox is checked, otherwise return false.
     */
    public boolean blank_content_checking(EditText email_address, EditText username, EditText password, EditText confirm_password, CheckBox customer, CheckBox employee){
        if(TextUtils.isEmpty(email_address.getText())){
            Toast.makeText(SignUpActivity.this, "Email Address Cannot Be Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(username.getText())){
            Toast.makeText(SignUpActivity.this, "Username Cannot Be Empty!",  Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(password.getText())){
            Toast.makeText(SignUpActivity.this, "Password Cannot Be Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(confirm_password.getText())){
            Toast.makeText(SignUpActivity.this, "Confirm Password Cannot Be Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        String confirm_pwd_str = confirm_password.getText().toString();
        String pwd_str = password.getText().toString();
        if(!confirm_pwd_str.equals(pwd_str)){
            Toast.makeText(SignUpActivity.this, "The Passwords Do Not Match!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!employee.isChecked() && !customer.isChecked()){
            Toast.makeText(SignUpActivity.this, "Please check yourself in as an employee or a customer", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(employee.isChecked() && customer.isChecked()){
            Toast.makeText(SignUpActivity.this, "Please only check in as an employee or a customer", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Set Two Checkboxes that can only be checked one each time.
     * @param customer customer as CheckBox
     * @param employee employee as CheckBox
     */
    public void only_one_checkbox_checked(CheckBox customer, CheckBox employee){
        employee.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    if(isChecked){
                        customer.setChecked(false);
                    }
                }
        );

        customer.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    if(isChecked){
                        employee.setChecked(false);
                    }
                }
        );
    }

    public String get_checkbox_result(CheckBox customer, CheckBox employee){
        if(customer.isChecked()){
            return "customer";
        }

        if(employee.isChecked()){
            return "employee";
        }

        return "customer";
    }

    /**
     * Using google firebase auth to sign up a new account
     * @param email_address email address as string
     * @param password password as string
     */
    public void create_account(String email_address, String username, String password){
        AuthSignUpOptions options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), email_address)
                .build();
        Amplify.Auth.signUp(username, password, options,
                this::onSuccess,
                this::onFail
        );
    }

    private void onFail(AuthException e) {
        Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

    }

    private void onSuccess(AuthSignUpResult authSignUpResult) {
        Intent intent = new Intent(SignUpActivity.this, ConfirmSignUpActivity.class);
        String user_type = get_checkbox_result(customer_cb, employee_cb);
        String email_address_str = email_address.getText().toString();
        String username_str = username.getText().toString();
        String password_str =  password.getText().toString();
        intent.putExtra("email_address", email_address_str);
        intent.putExtra("user_type", user_type);
        intent.putExtra("password", password_str);
        intent.putExtra("username", username_str);
        startActivity(intent);
        finish();
    }
}