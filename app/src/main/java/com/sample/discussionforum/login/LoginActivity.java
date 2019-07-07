package com.sample.discussionforum.login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sample.discussionforum.R;
import com.sample.discussionforum.common.data.Error;
import com.sample.discussionforum.common.data.StatusAwareResponse;
import com.sample.discussionforum.discussions.ui.DiscussionsListActivity;

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
        mViewModel.getLiveData().observe(this, new Observer<StatusAwareResponse>() {
            @Override
            public void onChanged(@Nullable StatusAwareResponse statusAwareResponse) {
                if (statusAwareResponse == null) {
                    Toast.makeText(LoginActivity.this, "Unable to login", Toast.LENGTH_SHORT).show();
                    return;
                }

                Error error = statusAwareResponse.getError();
                if (error != null) {
                    Toast.makeText(LoginActivity.this, "Some error occurred while login: " + error.getCode() + " : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(LoginActivity.this, statusAwareResponse.getMsg(), Toast.LENGTH_SHORT).show();
                DiscussionsListActivity.startActivity(LoginActivity.this);
                LoginActivity.this.finish();
            }
        });
    }

    public void onLogin(View view) {
        String etEmailText = etEmail.getText().toString();
        String etPwdText = etPwd.getText().toString();
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
            mViewModel.login(etEmailText, etPwdText);
        }
    }

    public void onRegister(View view) {
        RegisterActivity.startActivity(this);
    }
}
