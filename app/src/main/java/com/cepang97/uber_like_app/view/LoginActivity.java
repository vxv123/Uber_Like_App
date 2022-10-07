package com.cepang97.uber_like_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.cepang97.uber_like_app.R;

public class LoginActivity extends AppCompatActivity {
    EditText email_address;
    EditText password;
    CheckBox employee, customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email_address = findViewById(R.id.email_address_in_login);
        password = findViewById(R.id.password_in_login);
        employee = findViewById(R.id.employee_checkbox_in_login);
        customer = findViewById(R.id.customer_checkbox_in_login);

        if(!employee.isChecked() && !customer.isChecked()){
            Toast.makeText(LoginActivity.this, "Are you an employee or a customer?", Toast.LENGTH_SHORT).show();
        }
        else if(employee.isChecked()){

        }
        else if(customer.isChecked()){

        }


    }
}