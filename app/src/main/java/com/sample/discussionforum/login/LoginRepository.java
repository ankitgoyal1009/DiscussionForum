package com.sample.discussionforum.login;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.sample.discussionforum.R;
import com.sample.discussionforum.common.Gson;
import com.sample.discussionforum.common.Status;
import com.sample.discussionforum.common.data.Error;
import com.sample.discussionforum.common.data.StatusAwareResponse;
import com.sample.discussionforum.login.data.User;

public class LoginRepository {
    private static final String DB_NAME = "discussions_pref";
    private static final String PREF_USER = "user";
    private static LoginRepository sInstance;

    private LoginRepository() {
    }

    public static LoginRepository getsInstance() {
        if (sInstance == null) {
            sInstance = new LoginRepository();
        }
        return sInstance;
    }

    public void registerUser(Context context, User user, MutableLiveData<StatusAwareResponse> liveData) {
        SharedPreferences preferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_USER, Gson.getInstance().toJson(user));
        if (editor.commit()) {
            StatusAwareResponse response = new StatusAwareResponse();
            response.setStatus(Status.success);
            response.setMsg(context.getString(R.string.registration_success));
            liveData.postValue(response);
        } else {
            StatusAwareResponse response = new StatusAwareResponse();
            response.setStatus(Status.failed);
            Error error = new Error();
            error.setCode("404");
            error.setMessage(context.getString(R.string.error_user_not_created));
            response.setError(error);
            liveData.postValue(response);
        }

    }

    public User getUser(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        String userJson = preferences.getString(PREF_USER, null);
        User user = null;
        try {
            user = Gson.getInstance().fromJson(userJson, User.class);
        } catch (JsonSyntaxException e) {
            Log.e("LoginRepository", "Parsing exception while getting user", e);
        }
        return user;
    }
}
                                