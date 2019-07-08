package com.sample.discussionforum.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sample.discussionforum.R;
import com.sample.discussionforum.discussions.ui.DiscussionsListActivity;
import com.sample.discussionforum.login.data.User;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel mViewModel;
    private EditText etEmail;
    private EditText etPwd;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.et_email);
        etPwd = findViewById(R.id.et_password);
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    public void onLogin(View view) {
        String etEmailText = etEmail.getText().toString();
        final String etPwdText = etPwd.getText().toString();
        boolean valid = true;
        if (TextUtils.isEmpty(etEmailText)) {
            etEmail.setError(getString(R.string.error_required));
            valid = false;
        }

        if (TextUtils.isEmpty(etPwdText)) {
            etPwd.setError(getString(R.string.error_required));
            valid = false;
        }

        if (valid) {
            mViewModel.getUser(etEmailText).observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if (user == null) {
                        Toast.makeText(LoginActivity.this, R.string.error_user_not_found, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (etPwdText.equals(user.getPwd())) {
                        mViewModel.createSession(user);
                        DiscussionsListActivity.startActivity(LoginActivity.this);
                        LoginActivity.this.finish();
                    } else {
                        Toast.makeText(LoginActivity.this, R.string.error_authentication_failed, Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    public void onRegister(View view) {
        RegisterActivity.startActivity(this);
    }
}
