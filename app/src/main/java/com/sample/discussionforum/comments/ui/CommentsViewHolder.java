package com.sample.discussionforum.comments.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.discussionforum.R;

public class CommentsViewHolder extends RecyclerView.ViewHolder {
    public TextView tvUserName;
    public TextView tvCommentTime;
    public TextView tvComment;
    public TextView tvLikeCount;
    public TextView tvCommentsCount;
    public ImageView ivReply;
    public ImageView ivLike;
    public ImageView ivShare;
    public ImageView ivUserPhoto;

    public CommentsViewHolder(@NonNull View itemView) {
        super(itemView);
        tvUserName = itemView.findViewById(R.id.tv_user);
        tvCommentTime = itemView.findViewById(R.id.tv_comment_time);
        tvComment = itemView.findViewById(R.id.tv_comment);
        tvLikeCount = itemView.findViewById(R.id.tv_like_count);
        tvCommentsCount = itemView.findViewById(R.id.tv_comments_count);
        ivReply = itemView.findViewById(R.id.iv_reply);
        ivLike = itemView.findViewById(R.id.iv_like);
        ivShare = itemView.findViewById(R.id.iv_share);
    }
}
