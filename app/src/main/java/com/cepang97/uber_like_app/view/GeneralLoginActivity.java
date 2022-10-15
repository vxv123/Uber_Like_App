package com.cepang97.uber_like_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cepang97.uber_like_app.R;
import com.cepang97.uber_like_app.view.customer.CustomerLoginActivity;
import com.cepang97.uber_like_app.view.employee.EmployeeLoginActivity;

public class GeneralLoginActivity extends AppCompatActivity {

    Button customer, employee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_login);
        customer = findViewById(R.id.customer_in_general_login);
        employee = findViewById(R.id.employee_in_general_login);

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GeneralLoginActivity.this, CustomerLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GeneralLoginActivity.this, EmployeeLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}