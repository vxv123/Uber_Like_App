package com.cepang97.uber_like_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.cepang97.uber_like_app.view.customer.CustomerLoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        AuthUser currentUser = Amplify.Auth.getCurrentUser();
        Intent intent;
        //If current user is null, jump to the sign in page
        if(currentUser == null){
            intent = new Intent(MainActivity.this, CustomerLoginActivity.class);

        }
        else{
            //If not null, jump to the map page.
            //Need further implement.
            String user_id = currentUser.getUserId();
            //Get user type by its id, then go to employee_map page or customer_map page.
        }
        //startActivity(intent);
        finish();
    }
}