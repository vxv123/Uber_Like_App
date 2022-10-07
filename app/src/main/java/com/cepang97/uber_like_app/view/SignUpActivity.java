package com.cepang97.uber_like_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.cepang97.uber_like_app.R;

public class SignUpActivity extends AppCompatActivity {
    EditText email_address, username, password, confirm_password;
    Button submit;
    CheckBox employee, customer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email_address = findViewById(R.id.email_address_in_signup);
        username = findViewById(R.id.username_in_signup);
        password = findViewById(R.id.password_in_signup);
        confirm_password = findViewById(R.id.confirm_password_in_signup);
        submit = findViewById(R.id.submit_in_signup);
        employee = findViewById(R.id.employee_checkbox_in_signup);
        customer = findViewById(R.id.customer_checkbox_in_signup);

        if(!employee.isChecked() && !customer.isChecked()){
            Toast.makeText(SignUpActivity.this, "Please check yourself in as an employee or a customer", Toast.LENGTH_SHORT).show();
        }
        else if(employee.isChecked() && customer.isChecked()){
            Toast.makeText(SignUpActivity.this, "Please only check in as an employee or a customer", Toast.LENGTH_SHORT).show();
        }
        else if(employee.isChecked()){

        }
        else if(customer.isChecked()){

        }

    }
}