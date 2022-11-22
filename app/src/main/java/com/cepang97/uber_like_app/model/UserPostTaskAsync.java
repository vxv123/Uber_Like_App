package com.cepang97.uber_like_app.model;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;


import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.amazonaws.regions.Regions;
import com.cepang97.uberlikeapp.UberLikeAppClient;
import com.cepang97.uberlikeapp.model.UserPostInput;
import com.cepang97.uberlikeapp.model.UserPostOutput;


public class UserPostTaskAsync extends AsyncTask<User, Void, String> {
    private final UberLikeAppClient client;
    private Context mContext;
    private OnEventListener<String> mCallBack;
    public Exception mException;



    public UserPostTaskAsync(Context mContext, OnEventListener mCallBack) {
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
            String userId = users[0].getUserId();
            String username = users[0].getUsername();
            String userType = users[0].getType();
            UserPostInput body = new UserPostInput();
            body.setEmail("pcdota123@!");
            body.setUserId("pcdota123@!");
            body.setPassword("pcdota123@!");
            body.setUsername("pcdota123@!");
            body.setType("customer");

            UserPostOutput result = client.usersPost(body);

            String res_val = result.getMessage();
            System.out.println(res_val);

            return res_val;
        }catch (Exception e) {
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
