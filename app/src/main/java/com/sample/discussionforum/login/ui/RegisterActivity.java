package com.sample.discussionforum.login.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sample.discussionforum.R;
import com.sample.discussionforum.login.LoginViewModel;
import com.sample.discussionforum.login.data.User;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class RegisterActivity extends AppCompatActivity {
    private LoginViewModel mViewModel;
    private EditText etDisplayName;
    private EditText etEmail;
    private EditText etPwd;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etDisplayName = findViewById(R.id.et_display_name);
        etEmail = findViewById(R.id.et_email);
        etPwd = findViewById(R.id.et_password);

        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches();
    }

    public void onRegister(View view) {
        boolean valid = true;
        final String displayName = etDisplayName.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();
        final String pwd = etPwd.getText().toString().trim();
        if (TextUtils.isEmpty(displayName)) {
            etDisplayName.setError(getString(R.string.error_required));
            valid = false;
        }

        if (TextUtils.isEmpty(email)) {
            etEmail.setError(getString(R.string.error_required));
            valid = false;
        }

        if (!isValidEmail(email)) {
            etEmail.setError(getString(R.string.error_invalid_email));
            valid = false;
        }

        if (TextUtils.isEmpty(pwd)) {
            etPwd.setError(getString(R.string.error_required));
            valid = false;
        }

        if (valid) {
            final LiveData<User> userLiveData = mViewModel.getUser(email);
            userLiveData.observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if (user != null) {
                        Toast.makeText(RegisterActivity.this, R.string.error_user_already_exists, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    registerUser(displayName, email, pwd);
                    userLiveData.removeObservers(RegisterActivity.this);
                }
            });
        }
    }

    private void registerUser(String displayName, String email, String pwd) {
        final LiveData<User> userLiveData = mViewModel.registerUser(displayName, email, pwd);
        userLiveData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    Toast.makeText(RegisterActivity.this, R.string.registration_success, Toast.LENGTH_SHORT).show();
                    RegisterActivity.this.finish();
                    userLiveData.removeObservers(RegisterActivity.this);
                }
            }
        });
    }
}
