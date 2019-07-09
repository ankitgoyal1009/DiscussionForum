package com.sample.discussionforum.likes.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface LikesDao {
    @Query("SELECT COUNT(id) from Likes where commentId =:commentId")
    LiveData<Integer> getLikeCountForComment(String commentId);

    @Query("SELECT * from Likes where commentId =:commentId and userId=:userId")
    LiveData<Likes> isCommentLikedByUser(String commentId, String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void likeComment(Likes likes);

    @Query("DELETE from likes where id=:id")
    void disLikeComment(long id);

}
