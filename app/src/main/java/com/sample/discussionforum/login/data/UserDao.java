package com.sample.discussionforum.login.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Query("DELETE from User")
    void deleteAll();

    @Query("SELECT * from user where email =:email")
    LiveData<User> getUser(String email);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);
}
