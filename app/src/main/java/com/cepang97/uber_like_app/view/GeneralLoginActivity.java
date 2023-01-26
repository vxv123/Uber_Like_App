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
import com.cepang97.uber_like_app.view.customer.CustomerLoginActivity;
import com.cepang97.uber_like_app.view.employee.EmployeeLoginActivity;

public class GeneralLoginActivity extends AppCompatActivity {

    Button submit;
    CheckBox employee_cb, customer_cb;
    EditText email_address_et, password_et;
    String email_address_str, pwd_str, user_type;

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

                    check_account(email_address_str, pwd_str, user_type);

                }
            }
        });

    }

    public boolean check_account(String email, String password, String user_type){
        return true;
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