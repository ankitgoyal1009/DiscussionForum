package com.sample.discussionforum;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sample.discussionforum.login.LoginActivity;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // TODO: 02/07/19 Ankit check if user has logged in or not if not then go to login screen.
        // else check if DB has dummy discussions, if no then create and go to discussions list screen
        // if discussions are already loaded then go directly to discussions list screen

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LoginActivity.startActivity(SplashScreen.this);
                SplashScreen.this.finish();
            }
        }, 2000);
    }
}
