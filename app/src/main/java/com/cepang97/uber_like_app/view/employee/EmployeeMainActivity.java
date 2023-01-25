package com.cepang97.uber_like_app.view.employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.cepang97.uber_like_app.R;
import com.cepang97.uber_like_app.view.customer.CustomerMapFragment;

public class EmployeeMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_main);

        Fragment fragment = new CustomerMapFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_in_employee, fragment)
                .commit();
    }
}