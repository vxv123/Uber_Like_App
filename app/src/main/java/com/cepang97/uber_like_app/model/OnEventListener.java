package com.cepang97.uber_like_app.model;

public interface OnEventListener<String> {
    public void onSuccess(String s);
    public void onFailure(Exception e);

}
