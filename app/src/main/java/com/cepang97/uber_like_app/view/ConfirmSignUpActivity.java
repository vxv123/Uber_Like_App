package com.cepang97.uber_like_app.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cepang97.uber_like_app.R;



// Abandoned //

/**
 * Confirm Account Sign Up Successfully first
 * Then sign in to Amplify,
 * Then based on different user type, goes to different main activities.
 */
public class ConfirmSignUpActivity extends AppCompatActivity {
    Button submit;
    EditText confirmed_code;
    String username;
    String password;
    String email_address;
    String userId;

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
        //Amplify.Auth.getCurrentUser();
        //userId = Amplify.Auth.getCurrentUser().getUserId();

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
