package com.sample.discussionforum.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.sample.discussionforum.common.Gson;
import com.sample.discussionforum.common.data.LocalDB;
import com.sample.discussionforum.login.data.User;
import com.sample.discussionforum.login.data.UserDao;

import java.util.UUID;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import static com.sample.discussionforum.data.SharedPrefUtil.PREF_CURRENT_LOGGEDIN_USER;

public class LoginRepository {
    private static final String DB_NAME = "discussions_pref";
    private static final String PREF_SESSION = "session";
    private static LoginRepository sInstance;

    private LoginRepository() {
    }

    static LoginRepository getsInstance(Context context) {
        if (sInstance == null) {
            sInstance = new LoginRepository();
        }

        LocalDB.getInstance(context);
        return sInstance;
    }

    /**
     * This method will create a new user in the system. It also checks if the same email is already
     * being used in the system.
     */
    LiveData<User> registerUser(final Context context, final User user) {
        LiveData<User> userLD = getUser(context, user.getEmail());
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object[] objects) {
                UserDao userDao = LocalDB.getInstance(context).getUserDao();
                userDao.insert(user);
                return null;
            }
        }.execute();
        return userLD;
    }

    /**
     * THis method return user object for a given email.
     */
    LiveData<User> getUser(Context context, String email) {
        UserDao userDao = LocalDB.getInstance(context).getUserDao();
        return userDao.getUser(email);
    }

    /**
     * This method generates a session
     */
    public void createSession(Context context, User user) {
        SharedPreferences preferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_SESSION, UUID.randomUUID().toString());
        editor.putString(PREF_CURRENT_LOGGEDIN_USER, Gson.getInstance().toJson(user));
        editor.apply();
    }

    /**
     * This method returns current session token if available else null
     */
    @Nullable
    String getSession(Context context) {
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
        editor.remove(PREF_CURRENT_LOGGEDIN_USER);
        editor.apply();
    }
}
                                