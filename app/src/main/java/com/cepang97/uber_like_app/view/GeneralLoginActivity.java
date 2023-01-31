package com.cepang97.uber_like_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.cepang97.uber_like_app.R;
import com.cepang97.uber_like_app.model.OnEventListener;
import com.cepang97.uber_like_app.model.User;
import com.cepang97.uber_like_app.model.UserGetTaskAsync;
import com.cepang97.uber_like_app.view.customer.CustomerLoginActivity;
import com.cepang97.uber_like_app.view.customer.CustomerMainActivity;
import com.cepang97.uber_like_app.view.employee.EmployeeLoginActivity;
import com.cepang97.uber_like_app.view.employee.EmployeeMainActivity;

public class GeneralLoginActivity extends AppCompatActivity {

    Button submit;
    CheckBox employee_cb, customer_cb;
    EditText email_address_et, password_et;
    String email_address_str, pwd_str, user_type;
    public static final String TAG = "GeneralLoginActivity";
    public static final String USER_TYPE_ERROR = "User's type is wrong!";
    public static final String USER_PASSWORD_ERROR = "User's password is wrong!";
    public static final String USER_LOGIN_SUCCESS = "Authentication success!";
    public static final String USER_LOGIN_FAIL = "Authentication fail!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_login);

        submit = findViewById(R.id.submit_in_login);
        employee_cb = findViewById(R.id.employee_checkbox_in_longin);
        customer_cb = findViewById(R.id.customer_checkbox_in_longin);
        email_address_et = findViewById(R.id.email_address_in_login);
        password_et = findViewById(R.id.password_in_login);

        only_one_checkbox_checked(customer_cb, employee_cb);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(blank_content_checking(email_address_et, password_et)){
                    email_address_str = email_address_et.getText().toString();
                    pwd_str = password_et.getText().toString();

                    user_type = get_checkbox_result(customer_cb, employee_cb);

                    login_account(email_address_str, pwd_str, user_type);

                }
            }
        });

    }

    public void login_account(String email, String password, String user_type){
        User user = new User(email, password, user_type);
        UserGetTaskAsync userGetTaskAsync = new UserGetTaskAsync(getApplicationContext(), new OnEventListener<String>() {

            @Override
            public void onSuccess(String s) {
                if(s.equals(USER_LOGIN_SUCCESS)){
                    Intent intent;
                    if(user_type.equals("customer")){
                        intent = new Intent(GeneralLoginActivity.this, CustomerMainActivity.class);
                    }
                    else {
                        intent = new Intent(GeneralLoginActivity.this, EmployeeMainActivity.class);
                    }
                    startActivity(intent);
                    finish();
                }
                else if(s.equals(USER_LOGIN_FAIL)){
                    Toast.makeText(GeneralLoginActivity.this, "User Login Fails!",
                            Toast.LENGTH_SHORT).show();
                }
                else if(s.equals(USER_PASSWORD_ERROR)){
                    Toast.makeText(GeneralLoginActivity.this, "Password is wrong!",
                            Toast.LENGTH_SHORT).show();
                }
                else if(s.equals(USER_TYPE_ERROR)){
                    Toast.makeText(GeneralLoginActivity.this, "User's Type is wrong!",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(GeneralLoginActivity.this, "Something Wrong!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });
        userGetTaskAsync.execute(user);

    }


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


    public boolean blank_content_checking(EditText email_address, EditText password){
        if(TextUtils.isEmpty(email_address.getText())){
            Toast.makeText(GeneralLoginActivity.this, "Email Address Cannot Be Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(password.getText())){
            Toast.makeText(GeneralLoginActivity.this, "Password Cannot Be Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
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
}