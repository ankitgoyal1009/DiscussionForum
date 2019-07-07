package com.sample.discussionforum.discussions.data;

import com.sample.discussionforum.login.data.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface DiscussionsDao {
    @Query("DELETE from Discussion")
    void deleteAll();

    @Query("SELECT * from Discussion where id =:id")
    LiveData<Discussion> getDiscussion(String id);

    @Query("SELECT * from Discussion ")
    LiveData<List<Discussion>> getAllDiscussion();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Discussion discussion);
}
