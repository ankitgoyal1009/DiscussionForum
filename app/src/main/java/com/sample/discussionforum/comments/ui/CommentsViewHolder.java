package com.sample.discussionforum.comments.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.discussionforum.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentsViewHolder extends RecyclerView.ViewHolder {
    public TextView tvUserName;
    public TextView tvCommentTime;
    public TextView tvComment;
    public TextView tvUpvoteCount;
    public TextView tvCommentsCount;
    public ImageView ivReply;
    public ImageView ivUpvote;
    public ImageView ivShare;
    public ImageView ivUserPhoto;

    public CommentsViewHolder(@NonNull View itemView) {
        super(itemView);
        tvUserName = itemView.findViewById(R.id.tv_user);
        tvCommentTime = itemView.findViewById(R.id.tv_comment_time);
        tvComment = itemView.findViewById(R.id.tv_comment);
        tvUpvoteCount = itemView.findViewById(R.id.tv_upvote_count);
        tvCommentsCount = itemView.findViewById(R.id.tv_comments_count);
        ivReply = itemView.findViewById(R.id.iv_reply);
        ivUpvote = itemView.findViewById(R.id.iv_upvote);
        ivShare = itemView.findViewById(R.id.iv_share);
    }
}
