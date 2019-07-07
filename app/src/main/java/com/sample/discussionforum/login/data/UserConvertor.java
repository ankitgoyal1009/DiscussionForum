package com.sample.discussionforum.login.data;


import com.sample.discussionforum.common.Gson;

import java.util.Date;

import androidx.room.TypeConverter;

public class UserConvertor {
    @TypeConverter
    public User getUser(String user) {
        return Gson.getInstance().fromJson(user, User.class);
    }

    @TypeConverter
    public String setUser(User user) {
        return Gson.getInstance().toJson(user);
    }

    @TypeConverter
    public Date getDate(long date) {
        return new Date(date);
    }

    @TypeConverter
    public long setDate(Date date) {
        return date.getTime();
    }
}
