package com.sample.discussionforum.likes;

import android.app.Application;

import com.sample.discussionforum.likes.data.Like;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class LikesViewModel extends AndroidViewModel {
    private LikesRepository mRepository;
    private Application mApplication;

    public LikesViewModel(@NonNull Application application) {
        super(application);
        mRepository = LikesRepository.getInstance(application);
        mApplication = application;
    }

    public LiveData<Integer> getLikesCountForComment(String commentId){
        return mRepository.getLikesCountForComment(commentId);
    }

    public LiveData<Like> getLikesCountForCommentByUser(String commentId, String userId){
        return mRepository.isCommentLikedByUser(commentId, userId);
    }

    public LiveData<List<Like>> getAllLikesByUser(String userId){
        return mRepository.getAllLikesByUser(userId);
    }

    public void likeComment(String commentId, String userId){
        Like likes = new Like();
        likes.setCommentId(commentId);
        likes.setUserId(userId);
        mRepository.likeComment(likes);
    }

    public void disLikeComment(long likeId){
        mRepository.dislikeComment(likeId);
    }


}
