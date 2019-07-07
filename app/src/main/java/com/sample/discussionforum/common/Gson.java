package com.sample.discussionforum.common;

import com.google.gson.GsonBuilder;

public class Gson {
    private static com.google.gson.Gson sInstance =
            new GsonBuilder()
                    .create();

    public static com.google.gson.Gson getInstance() {
        return sInstance;
    }

}
