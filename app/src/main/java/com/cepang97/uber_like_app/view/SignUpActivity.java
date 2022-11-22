package com.cepang97.uber_like_app.view;


import androidx.annotation.NonNull;
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


import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.cepang97.uber_like_app.R;
import com.cepang97.uber_like_app.model.OnEventListener;
import com.cepang97.uber_like_app.model.User;
import com.cepang97.uber_like_app.model.UserPostTaskAsync;

import com.cepang97.uber_like_app.view.customer.CustomerMainActivity;
import com.cepang97.uber_like_app.view.employee.EmployeeMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class SignUpActivity extends AppCompatActivity {
    EditText email_address, username, password, confirm_password;
    Button submit;
    CheckBox employee_cb, customer_cb;

    String email_address_str;
    String username_str;
    String pwd_str;
    String user_type;
    public static final String TAG = "SignUpActivity";
    public static final String SUCCESS = "user is successfully stored in database. Message send back from AWS Lambda.";
    public static final String FAIL = "current email address is already been used. Message send back from AWS Lambda.";
    private FirebaseAuth mAuth;



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
        //Initialize FirebaseAuth.
        mAuth = FirebaseAuth.getInstance();

        //Only make user click employee's click box or customer's click box
        only_one_checkbox_checked(customer_cb, employee_cb);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //blank_content_checking(email_address, username, password, confirm_password, customer_cb, employee_cb)
                if(true){
                    email_address_str = email_address.getText().toString();
                    username_str = username.getText().toString();
                    pwd_str = password.getText().toString();

                    email_address_str = "pcdota123@gmail.com";
                    username_str = "pcdota123";
                    pwd_str = "bewilder12345";
                    user_type = get_checkbox_result(customer_cb, employee_cb);
                    //Create An account by Using AWS
                    create_account(email_address_str, username_str, pwd_str, user_type);

                    //create_account_in_aws(email_address_str, username_str, pwd_str);
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
     * @param email email address as string
     * @param password password as string
     */
    public void create_account(String email, String username, String password, String user_type){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignUpActivity.this, "createUserWithEmail:success", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userId = user.getUid();

                            //If the account is created successfully, store user data into AWS dynamoDB
                            User newUser = new User(userId, email, password, username, user_type);
                            UserPostTaskAsync userPostTaskAsync = new UserPostTaskAsync(getApplicationContext(), new OnEventListener<String>() {

                                @Override
                                public void onSuccess(String s) {
                                    if(s.equals(SUCCESS)){
                                        //Goes to different pages based on user selected.
                                        if(get_checkbox_result(employee_cb, customer_cb).equals("customer")){
                                            Intent intent = new Intent(SignUpActivity.this, CustomerMainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else {
                                            Intent intent = new Intent(SignUpActivity.this, EmployeeMainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                    else if(s.equals(FAIL)){
                                        Toast.makeText(SignUpActivity.this, "Email Address is been used!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Exception e) {
                                    e.printStackTrace();
                                }
                            });
                            userPostTaskAsync.execute(newUser);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**

    public void create_account_in_aws(String email, String username, String password){
        Amplify.Auth.signUp(
                "pcdota123@gmail.com",
                "bewilder12345!@#",
                AuthSignUpOptions.builder().userAttribute(
                        AuthUserAttributeKey.email(),
                        "pcdota123@gmail.com"
                ).build(),
                this::signupsuccess,
                this::signupfail
        );
    }

    private void signupsuccess(AuthSignUpResult result){
        Log.d(TAG, "Sign Up Successful");
        String username = result.getUser().getUsername();
        String userId = result.getUser().getUserId();
        User newUser = new User("pcdota123", "pcdota123", "pcdota123", "pcdota123");
        UserPostTaskAsync userPostTaskAsync = new UserPostTaskAsync(getApplicationContext(), new OnEventListener<String>() {

            @Override
            public void onSuccess(String s) {
                Log.d(TAG, "Print Out: " + s);
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });
        userPostTaskAsync.execute(newUser);
    }

    private void signupfail(Exception e){
        Log.d(TAG, e.getMessage());
    }

     **/

}

