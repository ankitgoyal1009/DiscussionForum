package com.sample.discussionforum.login;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sample.discussionforum.R;
import com.sample.discussionforum.common.Status;
import com.sample.discussionforum.common.data.StatusAwareResponse;

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
        mViewModel.getLiveData().observe(this, new Observer<StatusAwareResponse>() {
            @Override
            public void onChanged(@Nullable StatusAwareResponse statusAwareResponse) {
                if (statusAwareResponse == null) {
                    return;
                }

                if (Status.success == statusAwareResponse.getStatus()) {
                    Toast.makeText(RegisterActivity.this, statusAwareResponse.getMsg(), Toast.LENGTH_SHORT).show();
                    RegisterActivity.this.finish();
                } else if (statusAwareResponse.getStatus() == Status.loading) {
                    Toast.makeText(RegisterActivity.this, R.string.msg_user_create, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, statusAwareResponse.getError().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidEmail(String email){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches();
    }

    public void onRegister(View view) {
        boolean valid = true;
        String displayName = etDisplayName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
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
            mViewModel.registerUser(displayName, email, pwd);
        }
    }
}
