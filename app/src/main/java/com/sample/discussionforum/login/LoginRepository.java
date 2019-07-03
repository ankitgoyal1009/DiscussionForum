package com.sample.discussionforum.login;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.sample.discussionforum.R;
import com.sample.discussionforum.common.Gson;
import com.sample.discussionforum.common.Status;
import com.sample.discussionforum.common.data.Error;
import com.sample.discussionforum.common.data.StatusAwareResponse;
import com.sample.discussionforum.login.data.User;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LoginRepository {
    private static final String DB_NAME = "discussions_pref";
    private static final String PREF_USERS = "users";
    private static final String PREF_SESSION = "session";
    private static LoginRepository sInstance;

    private LoginRepository() {
    }

    static LoginRepository getsInstance() {
        if (sInstance == null) {
            sInstance = new LoginRepository();
        }
        return sInstance;
    }

    /**
     * This method will create a new user in the system. It also checks if the same email is already
     * being used in the system.
     */
    void registerUser(Context context, User user, MutableLiveData<StatusAwareResponse> liveData) {
        SharedPreferences preferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        Map<String, User> existingUsers = getExistingUsers(context);

        if (existingUsers.containsKey(user.getEmail())) {
            // User already exists
            StatusAwareResponse response = new StatusAwareResponse();
            response.setStatus(Status.failed);
            Error error = new Error();
            error.setCode("302");
            error.setMessage(context.getString(R.string.error_user_already_exists));
            response.setError(error);
            liveData.postValue(response);
            return;
        }

        existingUsers.put(user.getEmail(), user);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_USERS, Gson.getInstance().toJson(existingUsers));
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

    /**
     * THis method return user object for a given email.
     */
    private User getUser(Context context, String email) {
        User user = null;
        Map<String, User> existingUsers = getExistingUsers(context);
        if (existingUsers.containsKey(email)) {
            user = existingUsers.get(email);
        }

        return user;
    }

    /**
     * This method returns existing users map if exists else empty map.
     */
    private Map<String, User> getExistingUsers(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        String existingUsersJson = preferences.getString(PREF_USERS, null);
        Map<String, User> existingUsers = new HashMap<>();
        if (!TextUtils.isEmpty(existingUsersJson)) {
            Type type = new TypeToken<Map<String, User>>() {
            }.getType();
            existingUsers = Gson.getInstance().fromJson(existingUsersJson, type);
        }
        return existingUsers;
    }

    /**
     * This method will check if user exists and pwd matches then it will post success else failure with error
     */
    void login(Context context, String email, String pwd, MutableLiveData<StatusAwareResponse> liveData) {
        User user = getUser(context, email);

        if (user == null) {
            //user not found
            StatusAwareResponse response = new StatusAwareResponse();
            response.setStatus(Status.failed);
            Error error = new Error();
            error.setCode("301");
            error.setMessage(context.getString(R.string.error_user_not_found));
            response.setError(error);
            liveData.postValue(response);
            return;
        }

        if (!user.getPwd().equals(pwd)) {
            //pwd mismatch
            StatusAwareResponse response = new StatusAwareResponse();
            response.setStatus(Status.failed);
            Error error = new Error();
            error.setCode("300");
            error.setMessage(context.getString(R.string.error_authentication_failed));
            response.setError(error);
            liveData.postValue(response);
            return;
        }

        //success
        createSession(context);
        StatusAwareResponse response = new StatusAwareResponse();
        response.setStatus(Status.success);
        response.setMsg(context.getString(R.string.msg_success));
        liveData.postValue(response);
    }

    /**
     * This method generates a session
     */
    private void createSession(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_SESSION, UUID.randomUUID().toString());
        editor.apply();
    }

    /**
     * This method returns current session token if available else null
     */
    @Nullable
    private String getSession(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        return preferences.getString(PREF_SESSION, null);
    }

    /**
     * This method will remove current session.
     */
    public void logout(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(PREF_SESSION);
        editor.apply();
    }
}
                                