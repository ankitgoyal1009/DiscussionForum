package com.sample.discussionforum.login;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;
import android.text.TextUtils;

import com.sample.discussionforum.common.Status;
import com.sample.discussionforum.common.data.StatusAwareResponse;
import com.sample.discussionforum.login.data.User;

import java.util.concurrent.Executor;

public class LoginViewModel extends AndroidViewModel {
    private LoginRepository mRepository;
    private Application mApplication;
    private MutableLiveData<StatusAwareResponse> mLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mApplication = application;
        mRepository = LoginRepository.getsInstance();
        mLiveData = new MutableLiveData<>();
    }

    MutableLiveData<StatusAwareResponse> getLiveData() {
        return mLiveData;
    }


    /**
     * This is new user registration method.
     */
    void registerUser(final String displayName, final String email, final String pwd) {
        new Executor() {
            @Override
            public void execute(@NonNull Runnable runnable) {
                runnable.run();
            }
        }.execute(new Runnable() {
            @Override
            public void run() {
                StatusAwareResponse loadingResponse = new StatusAwareResponse();
                loadingResponse.setStatus(Status.loading);
                mLiveData.postValue(loadingResponse);
                User user = new User(displayName, email, pwd);
                mRepository.registerUser(mApplication, user, mLiveData);
            }
        });
    }

    /**
     * This is login method.
     */
    void login(String email, String pwd) {
        mRepository.login(mApplication, email, pwd, mLiveData);
    }

    public boolean isUserLoggedIn() {
        return !TextUtils.isEmpty(mRepository.getSession(mApplication));
    }

}
