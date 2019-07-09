package com.sample.discussionforum;

import android.os.Bundle;
import android.os.Handler;

import com.sample.discussionforum.discussions.ui.DiscussionsListActivity;
import com.sample.discussionforum.login.ui.LoginActivity;
import com.sample.discussionforum.login.LoginViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // TODO: 02/07/19 Ankit check if user has logged in or not if not then go to login screen.
        // else check if DB has dummy discussions, if no then create and go to discussions list screen
        // if discussions are already loaded then go directly to discussions list screen

        final LoginViewModel model = ViewModelProviders.of(this).get(LoginViewModel.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!model.isUserLoggedIn()) {
                    LoginActivity.startActivity(SplashScreen.this);
                } else {
                    DiscussionsListActivity.startActivity(SplashScreen.this);
                }
                SplashScreen.this.finish();

            }
        }, 2000);
    }
}
