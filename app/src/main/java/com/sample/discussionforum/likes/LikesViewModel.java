package com.sample.discussionforum.likes;

import android.app.Application;

import com.sample.discussionforum.likes.data.Likes;

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

    public LiveData<Likes> getLikesCountForCommentByUser(String commentId, String userId){
        return mRepository.isCommentLikedByUser(commentId, userId);
    }

    public void likeComment(String commentId, String userId){
        Likes likes = new Likes();
        likes.setCommentId(commentId);
        likes.setUserId(userId);
        mRepository.likeComment(likes);
    }

    public void disLikeComment(long likeId){
        mRepository.dislikeComment(likeId);
    }


}
