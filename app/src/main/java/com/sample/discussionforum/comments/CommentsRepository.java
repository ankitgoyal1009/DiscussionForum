package com.sample.discussionforum.comments;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.sample.discussionforum.comments.data.Comment;
import com.sample.discussionforum.common.Gson;
import com.sample.discussionforum.data.SharedPrefUtil;
import com.sample.discussionforum.login.data.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommentsRepository {
    private static CommentsRepository sInstance;

    private CommentsRepository() {
    }

    public static CommentsRepository getInstance() {
        if (sInstance == null) {
            sInstance = new CommentsRepository();
        }
        return sInstance;
    }

    public Comment getComment(Context context, String commentId) {
        List<Comment> allComments = getAllComments(context);
        for (Comment comment : allComments) {
            if (comment.getId().equals(commentId)) {
                return comment;
            }
        }
        return null;
    }

    public boolean createComment(Context context, String content, String parentCommentId) {
        SharedPreferences preferences = SharedPrefUtil.getSharedPref(context);
        Comment comment = new Comment(UUID.randomUUID().toString());
        comment.setContent(content);
        comment.setCommentDate(System.currentTimeMillis());
        if (!TextUtils.isEmpty(parentCommentId)) {
            comment.setParentCommentId(parentCommentId);
        }
        //fixme should we exposed login repository to be used here os this is fine?
        String userJson = preferences.getString(SharedPrefUtil.PREF_CURRENT_LOGGEDIN_USER, null);
        if (TextUtils.isEmpty(userJson)) {
            return false;
        }
        User user = Gson.getInstance().fromJson(userJson, User.class);
        comment.setUser(user);
        SharedPreferences.Editor editor = preferences.edit();
        List<Comment> existingComments = getAllComments(context);
        existingComments.add(comment);
        editor.putString(SharedPrefUtil.PREF_COMMENTS, Gson.getInstance().toJson(existingComments));
        editor.commit();
        return true;
    }

    public List<Comment> getAllComments(Context context) {
        SharedPreferences preference = SharedPrefUtil.getSharedPref(context);
        String existingCommentsJson = preference.getString(SharedPrefUtil.PREF_COMMENTS, null);
        if (!TextUtils.isEmpty(existingCommentsJson)) {
            Type type = new TypeToken<List<Comment>>() {
            }.getType();
            return Gson.getInstance().fromJson(existingCommentsJson, type);
        }
        return new ArrayList<>();
    }

    public boolean saveComment(Context context, Comment comment) {
        if (comment == null) {
            return false;
        }
        List<Comment> comments = getAllComments(context);
        boolean newComment = true;
        for (int i = 0; i < comments.size(); i++) {
            Comment comment1 = comments.get(i);
            if (comment1.getId().equals(comment.getId())) {
                comments.set(i, comment);
                newComment = false;
                break;
            }
        }

        if (newComment) {
            comments.add(comment);
        }

        SharedPreferences preferences = SharedPrefUtil.getSharedPref(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SharedPrefUtil.PREF_COMMENTS, Gson.getInstance().toJson(comments));
        editor.commit();
        return true;
    }

    public boolean likeComment(Context context, String commentId) {
        Comment comment = getComment(context, commentId);
        comment.setLikeCount(comment.getLikeCount() + 1);
        return saveComment(context, comment);
    }

    public void upvote(String commentId) {

    }
}
