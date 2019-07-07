package com.sample.discussionforum.comments;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.sample.discussionforum.comments.data.Comment;
import com.sample.discussionforum.common.Status;
import com.sample.discussionforum.common.data.Error;
import com.sample.discussionforum.common.data.StatusAwareResponse;

import java.util.List;

public class CommentsViewModel extends AndroidViewModel {
    private MutableLiveData<StatusAwareResponse<Comment>> mLiveData;
    private CommentsRepository mRepository;
    private Application mApplication;

    public CommentsViewModel(@NonNull Application application) {
        super(application);
        mLiveData = new MutableLiveData<>();
        mRepository = CommentsRepository.getInstance();
        mApplication = application;

    }

    public MutableLiveData<StatusAwareResponse<Comment>> getLiveData() {
        return mLiveData;
    }

    public List<Comment> getAllComment() {
        return mRepository.getAllComments(mApplication);
    }

    public Comment getComment(String commentId) {
        return mRepository.getComment(mApplication, commentId);
    }

    public void createComment(String content) {
        StatusAwareResponse<Comment> response = new StatusAwareResponse<>();
        if (
                mRepository.createComment(mApplication, content, null)) {
            response.setStatus(Status.success);

        } else {
            response.setStatus(Status.failed);
        }
        mLiveData.postValue(response);
    }

    public void likeComment(String commentId) {
        StatusAwareResponse<Comment> response = new StatusAwareResponse<>();
        if (mRepository.likeComment(mApplication, commentId)) {
            response.setStatus(Status.success);
        } else {
            Error error = new Error();
            error.setMessage("Unable to like");
            response.setStatus(Status.failed);

        }
        mLiveData.postValue(response);
    }

    public void upvote(String commentId) {
        mRepository.upvote(commentId);
    }

    public void reply(String parentCommentId, String content) {
        mRepository.createComment(mApplication, content, parentCommentId);
    }
}
