package com.cepang97.uber_like_app.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.result.AuthSignInResult;
import com.amplifyframework.auth.result.AuthSignUpResult;
import com.amplifyframework.core.Amplify;
import com.cepang97.uber_like_app.R;
import com.cepang97.uber_like_app.view.customer.CustomerLoginActivity;
import com.cepang97.uber_like_app.view.customer.CustomerMainActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.WebSocket;

/**
 * Confirm Account Sign Up Successfully first
 * Then sign in to Amplify,
 * Then based on different user type, goes to different main activities.
 */
public class ConfirmSignUpActivity extends AppCompatActivity  implements View.OnClickListener {
    Button submit;
    EditText confirmed_code;
    String username;
    String password;
    String email_address;
    String userId;
    WebSocket ws;
    OkHttpClient client;
    static String url = "https://yii4rgbzte.execute-api.us-west-2.amazonaws.com/production";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_sign_up);
        //Initialize Components and clients
        submit = findViewById(R.id.submit_in_confirm_signup);
        confirmed_code = findViewById(R.id.confirmed_code_in_confirm_signup);
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        email_address = getIntent().getStringExtra("email_address");
        userId = Amplify.Auth.getCurrentUser().getUserId();
        client = new OkHttpClient();
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String code = confirmed_code.getText().toString();
        String email_address = getIntent().getStringExtra("email_address");
        Amplify.Auth.confirmSignUp(
                email_address,
                code,
                this::onSignUpSuccess,
                this::onFail
        );
    }

    private void onFail(AuthException e) {
        Toast.makeText(ConfirmSignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    //After signing up successfully, we need to sign in user to Employee Main Page/Customer Main Page
    private void onSignUpSuccess(AuthSignUpResult authSignUpResult) {
        Amplify.Auth.signIn(username, password, this::onLoginSuccess, this::onFail);
    }

    //Store data into DynamoDB and then jump to corresponding page.
    //POST
    private void onLoginSuccess(AuthSignInResult authSignInResult) {
        RequestBody formBody = new FormBody.Builder()
                .add("email", email_address)
                .add("username", username)
                .add("userId", userId)
                .add("password", password)
                .build();

        Request request = new Request.Builder().url(url + "/users").post(formBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    Intent intent = new Intent(ConfirmSignUpActivity.this, CustomerMainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    /**
     * private fun setupWebSocket() {
     *         val request = Request.Builder()
     *                 .url(getString(string.websocket_url))
     *                 .build()
     *         ws = OkHttpClient().newWebSocket(request, object: WebSocketListener() {
     *             override fun onOpen(webSocket: WebSocket, response: Response) {
     *                 super.onOpen(webSocket, response)
     *                 appendStatus(getString(string.websocket_opened))
     *             }
     *
     *             override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
     *                 super.onClosed(webSocket, code, reason)
     *                 appendStatus(getString(string.websocket_closed))
     *             }
     *
     *             override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
     *                 super.onFailure(webSocket, t, response)
     *                 appendStatus(getString(string.websocket_failure, t.message))
     *             }
     *
     *             override fun onMessage(webSocket: WebSocket, text: String) {
     *                 super.onMessage(webSocket, text)
     *                 appendStatus(getString(string.websocket_received_message, text))
     *             }
     *         })
     *     }
     */
}
