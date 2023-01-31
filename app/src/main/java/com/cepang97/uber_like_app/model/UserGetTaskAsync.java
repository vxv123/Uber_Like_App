package com.cepang97.uber_like_app.model;

import android.content.Context;
import android.os.AsyncTask;

import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.cepang97.uberlikeapp.UberLikeAppClient;
import com.cepang97.uberlikeapp.model.UserGetInput;
import com.cepang97.uberlikeapp.model.UserGetOutput;

public class UserGetTaskAsync extends AsyncTask<User, Void, String> {

    private final UberLikeAppClient client;
    private Context mContext;
    private OnEventListener<String> mCallBack;
    public Exception mException;


    public UserGetTaskAsync(Context mContext, OnEventListener mCallBack){
        this.mContext = mContext;
        this.mCallBack = mCallBack;

        ApiClientFactory factory = new ApiClientFactory();
        client = factory.build(UberLikeAppClient.class);
    }

    @Override
    protected String doInBackground(User... users) {
        try{
            String email = users[0].getEmail();
            String password = users[0].getPassword();
            String type = users[0].getType();

            UserGetInput body = new UserGetInput();
            body.setEmail(email);
            body.setPassword(password);
            body.setType(type);

            UserGetOutput result = client.usersGet(body);

            String res_val = result.getMessage();

            return res_val;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (mCallBack != null) {
            if (mException == null) {
                mCallBack.onSuccess(result);
            } else {
                mCallBack.onFailure(mException);
            }
        }
    }
}
