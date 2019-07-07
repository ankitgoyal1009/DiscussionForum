package com.sample.discussionforum.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUtil {
    public static final String DB_NAME = "discussions_pref";
    public static final String PREF_USERS = "users";
    public static final String PREF_SESSION = "session";
    public static final String PREF_DISCUSSIONS = "discussions";
    public static final String PREF_CURRENT_LOGGEDIN_USER = "logged_in_user";
    public static final String PREF_COMMENTS = "comments";

    public static SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
    }
}
