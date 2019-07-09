package com.sample.discussionforum.login;

import android.app.Application;
import android.text.TextUtils;

import com.sample.discussionforum.login.data.User;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class LoginViewModel extends AndroidViewModel {
    private LoginRepository mRepository;
    private Application mApplication;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mApplication = application;
        mRepository = LoginRepository.getsInstance(application);
    }

    /**
     * This is new user registration method.
     */
    public LiveData<User> registerUser(final String displayName, final String email, final String pwd) {
        User user = new User(displayName, email, pwd);
        return mRepository.registerUser(mApplication, user);
    }

    public void createSession(User user) {
        mRepository.createSession(mApplication, user);
    }

    public boolean isUserLoggedIn() {
        return !TextUtils.isEmpty(mRepository.getSession(mApplication));
    }

    public LiveData<User> getUser(String email) {
        return mRepository.getUser(mApplication, email);
    }

    public void logout() {
        mRepository.logout(mApplication);
    }
}
