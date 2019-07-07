package com.sample.discussionforum.login.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    String displayName;
    @PrimaryKey
    @NonNull
    String email;
    String pwd;

    public User() {
    }

    public User(String displayName, @NonNull String email, String pwd) {
        this.displayName = displayName;
        this.email = email;
        this.pwd = pwd;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
