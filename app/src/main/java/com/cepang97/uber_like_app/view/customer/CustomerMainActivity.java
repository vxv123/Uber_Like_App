package com.cepang97.uber_like_app.view.customer;

import androidx.appcompat.app.AppCompatActivity;


import androidx.fragment.app.Fragment;
import android.os.Bundle;

import com.cepang97.uber_like_app.R;


public class CustomerMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

        Fragment fragment = new CustomerMapFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_in_customer, fragment)
                .commit();

    }
}