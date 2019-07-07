package com.sample.discussionforum.comments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.sample.discussionforum.comments.data.Comment;
import com.sample.discussionforum.comments.data.CommentsDao;
import com.sample.discussionforum.common.Gson;
import com.sample.discussionforum.common.data.LocalDB;
import com.sample.discussionforum.data.SharedPrefUtil;
import com.sample.discussionforum.login.data.User;

import java.util.List;
import java.util.UUID;

import androidx.lifecycle.LiveData;

public class CommentsRepository {
    private static CommentsRepository sInstance;
    private static CommentsDao commentsDao;

    private CommentsRepository() {
    }

    public static CommentsRepository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new CommentsRepository();
        }
        commentsDao = LocalDB.getInstance(context).getCommentsDao();
        return sInstance;
    }

    public LiveData<Comment> getComment(String commentId) {
        return commentsDao.getComment(commentId);
    }

    public void createComment(final Context context, final String discussionId, final String parentCommentId, final String content) {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object[] objects) {

                SharedPreferences preferences = SharedPrefUtil.getSharedPref(context);
                Comment comment = new Comment(UUID.randomUUID().toString());
                comment.setContent(content);
                comment.setCommentDate(System.currentTimeMillis());
                if (!TextUtils.isEmpty(parentCommentId)) {
                    comment.setParentCommentId(parentCommentId);
                }

                String userJson = preferences.getString(SharedPrefUtil.PREF_CURRENT_LOGGEDIN_USER, null);
                if (TextUtils.isEmpty(userJson)) {
                    return false;
                }
                User user = Gson.getInstance().fromJson(userJson, User.class);
                comment.setUser(user);
                comment.setDiscussionId(discussionId);
                commentsDao.insert(comment);
                return true;
            }
        }.execute();
    }

    public LiveData<List<Comment>> getAllComments(String discussionId) {
        return commentsDao.getAllCommentsForDiscussion(discussionId);
    }

    public void saveComment(final Comment comment) {
        if (comment != null) {
            new AsyncTask<Object, Object, Object>() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    commentsDao.insert(comment);
                    return null;
                }
            }.execute();
        }
    }

    public void updateComment(final Comment comment) {
        if (comment != null) {
            new AsyncTask<Object, Object, Object>() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    commentsDao.update(comment);
                    return null;
                }
            }.execute();
        }
    }
}
