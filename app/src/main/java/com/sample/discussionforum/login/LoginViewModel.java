package com.sample.discussionforum.login;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.sample.discussionforum.common.Status;
import com.sample.discussionforum.common.data.StatusAwareResponse;
import com.sample.discussionforum.login.data.User;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

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

    public MutableLiveData<StatusAwareResponse> getLiveData() {
        return mLiveData;
    }

    public void registerUser(final String displayName, final String email, final String pwd) {
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
                User user = new User(displayName,email, pwd);
                mRepository.registerUser(mApplication, user, mLiveData);
            }
        });
    }

}
