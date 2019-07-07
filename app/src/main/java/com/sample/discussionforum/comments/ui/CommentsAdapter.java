package com.sample.discussionforum.comments.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sample.discussionforum.R;
import com.sample.discussionforum.comments.CommentsViewModel;
import com.sample.discussionforum.comments.data.Comment;
import com.sample.discussionforum.common.DateUtils;
import com.sample.discussionforum.common.Status;
import com.sample.discussionforum.common.data.StatusAwareResponse;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsViewHolder> {
    private AppCompatActivity mActivity;
    private List<Comment> mCommentList;
    private CommentsViewModel mCommentsViewModel;

    public CommentsAdapter(AppCompatActivity activity, List<Comment> commentList) {
        mActivity = activity;
        mCommentList = commentList;
        mCommentsViewModel = ViewModelProviders.of(activity).get(CommentsViewModel.class);
        mCommentsViewModel.getLiveData().observe(activity, new Observer<StatusAwareResponse<Comment>>() {
            @Override
            public void onChanged(@Nullable StatusAwareResponse<Comment> response) {
                if (response == null) {
                    return;
                }

                if (Status.failed == response.getStatus()) {
                    Toast.makeText(mActivity, response.getError().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setOrUpdateCommentList(List<Comment> commentList) {
        mCommentList = commentList;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_row_view, viewGroup, false);
        return new CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder viewHolder, int i) {
        final Comment comment = getItem(i);
        viewHolder.tvUserName.setText(comment.getUser().getDisplayName());
        viewHolder.tvCommentTime.setText(DateUtils.timeToString(comment.getCommentDate()));
        viewHolder.tvComment.setText(comment.getContent());
        viewHolder.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, comment.getContent());
                mActivity.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        viewHolder.ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCommentsViewModel.likeComment(comment.getId());
            }
        });

        if (comment.getLikeCount() > 0) {
            viewHolder.tvLikeCount.setText(mActivity.getString(R.string.like, String.valueOf(comment.getLikeCount())));
        }

        if (comment.getReplyCount() > 0) {
            viewHolder.tvCommentsCount.setText(mActivity.getString(R.string.replied, String.valueOf(comment.getReplyCount())));
        }
    }

    private Comment getItem(int position) {
        return mCommentList.get(position);
    }

    @Override
    public int getItemCount() {
        return mCommentList == null ? 0 : mCommentList.size();
    }
}
