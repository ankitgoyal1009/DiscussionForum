package com.sample.discussionforum.comments.ui;

import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sample.discussionforum.R;
import com.sample.discussionforum.comments.CommentsViewModel;
import com.sample.discussionforum.comments.data.Comment;
import com.sample.discussionforum.common.DateUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsViewHolder> {
    private AppCompatActivity mActivity;
    private List<Comment> mCommentList;
    private CommentsViewModel mCommentsViewModel;
    private boolean allowActions = true;

    public CommentsAdapter(AppCompatActivity activity) {
        mActivity = activity;
        mCommentsViewModel = ViewModelProviders.of(activity).get(CommentsViewModel.class);
    }

    public void setAllowActions(boolean allowActions) {
        this.allowActions = allowActions;
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
        if (allowActions) {
            viewHolder.actionDivider.setVisibility(View.VISIBLE);
            viewHolder.llActionContainer.setVisibility(View.VISIBLE);

            viewHolder.ivShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, comment.getContent());
                    mActivity.startActivity(Intent.createChooser(sharingIntent, mActivity.getString(R.string.share_via)));
                }
            });
            TextView tvCommentsCount = viewHolder.tvCommentsCount;
            tvCommentsCount.setTextColor(ContextCompat.getColor(mActivity, R.color.colorPrimaryDark));
            tvCommentsCount.setPaintFlags(tvCommentsCount.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            tvCommentsCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RepliesListActivity.startActivity(mActivity, comment.getId());
                }
            });

            viewHolder.ivUpvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int upvoteCount = comment.getUpvoteCount() + 1;
                    comment.setUpvoteCount(upvoteCount);
                    mCommentsViewModel.upvoteComment(comment);
                }
            });

            viewHolder.ivReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddCommentOrReply.startActivity(mActivity, comment.getDiscussionId(), comment.getId());
                }
            });

            if (comment.getUpvoteCount() > 0) {
                viewHolder.tvUpvoteCount.setText(mActivity.getString(R.string.upvote, String.valueOf(comment.getUpvoteCount())));
            }

            if (comment.getReplyCount() > 0) {
                tvCommentsCount.setText(mActivity.getString(R.string.replied, String.valueOf(comment.getReplyCount())));
            }
        } else {
            viewHolder.actionDivider.setVisibility(View.GONE);
            viewHolder.llActionContainer.setVisibility(View.GONE);
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
