package com.sample.discussionforum.comments.data;

import com.sample.discussionforum.discussions.data.Discussion;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CommentsDao {
    @Query("DELETE from Comment")
    void deleteAll();

    @Query("SELECT * from Comment where id =:id")
    LiveData<Comment> getComment(String id);

    @Query("SELECT * from Comment where parentCommentId=:parentId")
    LiveData<List<Comment>> getAllChildComments(String parentId);

    @Query("SELECT * from Comment where discussionId=:discussionId")
    LiveData<List<Comment>> getAllCommentsForDiscussion(String discussionId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Comment comment);

//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    void update(Comment comment);
}
