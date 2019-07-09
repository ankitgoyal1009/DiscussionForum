package com.sample.discussionforum.comments;

import android.app.Application;

import com.sample.discussionforum.comments.data.Comment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CommentsViewModel extends AndroidViewModel {
    private CommentsRepository mRepository;
    private Application mApplication;

    public CommentsViewModel(@NonNull Application application) {
        super(application);
        mRepository = CommentsRepository.getInstance(application);
        mApplication = application;
    }

    public LiveData<List<Comment>> getAllComment(String discussionId) {
        return mRepository.getAllComments(discussionId);
    }

    public LiveData<List<Comment>> getAllRepliesOnComment(String commentId) {
        return mRepository.getAllRepliesOnComment(commentId);
    }

    public LiveData<Comment> getComment(String commentId) {
        return mRepository.getComment(commentId);
    }

    public void createComment(String discussionId, String parentCommentId, String content) {
        mRepository.createComment(mApplication, discussionId, parentCommentId, content);
    }

    public void upvoteComment(Comment commentId) {
        mRepository.updateComment(commentId);
    }

    public void increaseReplyCount(Comment commentId) {
        mRepository.increaseReplyCount(commentId);
    }
}
