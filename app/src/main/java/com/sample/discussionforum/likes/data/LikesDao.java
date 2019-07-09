package com.sample.discussionforum.likes.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface LikesDao {
    @Query("SELECT COUNT(id) from `Like` where commentId =:commentId")
    LiveData<Integer> getLikeCountForComment(String commentId);

    @Query("SELECT * from `Like` where commentId =:commentId and userId=:userId")
    LiveData<Like> isCommentLikedByUser(String commentId, String userId);

    @Query("SELECT * from `Like` where userId=:userId")
    LiveData<List<Like>> getAllLikesByUser(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void likeComment(Like like);

    @Query("DELETE from `Like` where id=:id")
    void disLikeComment(long id);

}
