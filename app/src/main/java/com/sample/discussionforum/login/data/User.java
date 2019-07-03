package com.sample.discussionforum.login.data;

public class User {
    String displayName;
    String email;
    String pwd;

    public User(String displayName, String email, String pwd) {
        this.displayName = displayName;
        this.email = email;
        this.pwd = pwd;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getPwd() {
        return pwd;
    }
}
