package com.cepang97.uber_like_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cepang97.uber_like_app.R;


public class ConfirmSignUpActivity extends AppCompatActivity {
    Button submit;
    EditText confirmed_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_sign_up);
        submit = findViewById(R.id.submit_in_confirm_signup);
        confirmed_code = findViewById(R.id.confirmed_code_in_confirm_signup);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = confirmed_code.getText().toString();
                Intent intent = new Intent(ConfirmSignUpActivity.this, SignUpActivity.class);
                intent.putExtra("Verification_Code", code);
                startActivity(intent);
                finish();
            }
        });
    }
}