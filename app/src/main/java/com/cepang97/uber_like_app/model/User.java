package com.cepang97.uber_like_app.model;

public class User {
    public String email;
    public String password;
    public String username;
    public String userId;
    public String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User(String email, String password, String username, String userId, String type){
        this.email = email;
        this.password = password;
        this.username = username;
        this.userId = userId;
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
