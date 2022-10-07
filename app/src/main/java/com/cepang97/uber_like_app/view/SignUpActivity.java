package com.cepang97.uber_like_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.cepang97.uber_like_app.R;
import com.cepang97.uber_like_app.controller.SignupHandler;

public class SignUpActivity extends AppCompatActivity {
    EditText email_address, username, password, confirm_password;
    Button submit;
    CheckBox employee, customer;
    SignupHandler handler = new SignupHandler();
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


        only_one_checkbox_checked(customer, employee);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(blank_content_checking(email_address, username, password, confirm_password, customer, employee)){
                    String email_addr_str = email_address.getText().toString();
                    String username_str = username.getText().toString();
                    String pwd_str = password.getText().toString();
                    String confirm_pwd_str = confirm_password.getText().toString();
                    handler.create_account(email_addr_str, pwd_str);

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
        else if(TextUtils.isEmpty(username.getText())){
            Toast.makeText(SignUpActivity.this, "Username Cannot Be Empty!",  Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.isEmpty(password.getText())){
            Toast.makeText(SignUpActivity.this, "Password Cannot Be Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.isEmpty(confirm_password.getText())){
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
}