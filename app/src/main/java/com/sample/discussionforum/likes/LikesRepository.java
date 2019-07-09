package com.sample.discussionforum.likes;

import android.content.Context;
import android.os.AsyncTask;

import com.sample.discussionforum.common.data.LocalDB;
import com.sample.discussionforum.likes.data.Likes;
import com.sample.discussionforum.likes.data.LikesDao;

import androidx.lifecycle.LiveData;

class LikesRepository {
    private static LikesRepository sInstance;
    private static LikesDao likesDao;

    private LikesRepository() {
    }

    static LikesRepository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new LikesRepository();
        }
        likesDao = LocalDB.getInstance(context).getLikesDao();
        return sInstance;
    }

    LiveData<Integer> getLikesCountForComment(String commentId) {
        return likesDao.getLikeCountForComment(commentId);
    }

    LiveData<Likes> isCommentLikedByUser(String commentId, String userId) {
        return likesDao.isCommentLikedByUser(commentId, userId);
    }

    void likeComment(final Likes likes) {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object[] objects) {
                likesDao.likeComment(likes);
                return null;
            }}.execute();
    }

    void dislikeComment(final long likeId) {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object[] objects) {
                likesDao.disLikeComment(likeId);
                return null;
            }}.execute();
    }

    }
