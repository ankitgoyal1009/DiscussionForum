package com.sample.discussionforum.discussions.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.discussionforum.R;
import com.sample.discussionforum.comments.CommentsViewModel;
import com.sample.discussionforum.comments.data.Comment;
import com.sample.discussionforum.comments.ui.AddCommentOrReply;
import com.sample.discussionforum.comments.ui.CommentsAdapter;
import com.sample.discussionforum.common.DateUtils;
import com.sample.discussionforum.common.ui.BaseActivity;
import com.sample.discussionforum.discussions.DiscussionsViewModel;
import com.sample.discussionforum.discussions.data.Discussion;
import com.sample.discussionforum.likes.LikesViewModel;
import com.sample.discussionforum.likes.data.Like;
import com.sample.discussionforum.login.LoginViewModel;
import com.sample.discussionforum.login.data.User;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.sample.discussionforum.comments.ui.AddCommentOrReply.PARENT_COMMENT_ID;

public class DiscussionDetailsActivity extends BaseActivity {

    public static final String DISCUSSION_ID = "discussion_id";
    private CommentsViewModel mCommentsViewModel;
    private EditText etAddComment;
    private String mDiscussionId;
    private LinearLayout mNoCommentLL;
    private RelativeLayout mCommentsContainerRl;
    private TextView mCommentsCountTV;

    public static void startActivity(Context context, String discussionId) {
        Intent intent = new Intent(context, DiscussionDetailsActivity.class);
        intent.putExtra(DISCUSSION_ID, discussionId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_details);
        mDiscussionId = "";
        if (getIntent() != null) {
            if (getIntent().hasExtra(DISCUSSION_ID)) {
                mDiscussionId = getIntent().getStringExtra(DISCUSSION_ID);
            }
        }
        if (TextUtils.isEmpty(mDiscussionId)) {
            Toast.makeText(DiscussionDetailsActivity.this, R.string.error_general, Toast.LENGTH_SHORT).show();
            return;
        }
        DiscussionsViewModel viewModel = ViewModelProviders.of(this).get(DiscussionsViewModel.class);
        viewModel.getDiscussion(mDiscussionId).observe(this, new Observer<Discussion>() {
            @Override
            public void onChanged(Discussion discussion) {
                if (discussion == null) {
                    Toast.makeText(DiscussionDetailsActivity.this, R.string.error_general, Toast.LENGTH_SHORT).show();
                    return;
                }
                TextView tvDiscussionTitle = findViewById(R.id.tv_discussion_title);
                tvDiscussionTitle.setText(discussion.getTitle());

                TextView tvDiscussionDetail = findViewById(R.id.tv_discussion);
                tvDiscussionDetail.setText(discussion.getDescription());

                TextView tvDiscussionDate = findViewById(R.id.tv_discussion_date);
                tvDiscussionDate.setText(DateUtils.dateToString(discussion.getDate()));

            }
        });

        mCommentsCountTV = findViewById(R.id.tv_comments_count);
        mCommentsContainerRl = findViewById(R.id.rl_comments_container);
        LikesViewModel likesViewModel = ViewModelProviders.of(this).get(LikesViewModel.class);
        LoginViewModel loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        User user = loginViewModel.getLoggedInUser();

        final CommentsAdapter adapter = new CommentsAdapter(this);

        likesViewModel.getAllLikesByUser(user.getEmail()).observe(this, new Observer<List<Like>>() {
            @Override
            public void onChanged(List<Like> likes) {
                if(likes!= null) {
                    adapter.setOrUpdateLikesList(likes);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        mCommentsViewModel = ViewModelProviders.of(this).get(CommentsViewModel.class);
        mCommentsViewModel.getAllComment(mDiscussionId).observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> comments) {
                if (comments != null) {
                    adapter.setOrUpdateCommentList(comments);
                    adapter.notifyDataSetChanged();
                    int commentsCount = comments.size();
                    showAddCommentContainer(commentsCount);
                    mCommentsCountTV.setText(getString(R.string.comments, String.valueOf(commentsCount)));
                }
            }
        });

        RecyclerView mCommentsRv = findViewById(R.id.rv_comments);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mCommentsRv.setLayoutManager(manager);
        mCommentsRv.setItemAnimator(new DefaultItemAnimator());
        mCommentsRv.setAdapter(adapter);

        mNoCommentLL = findViewById(R.id.ll_no_comment_container);
        showAddCommentContainer(adapter.getItemCount());
        etAddComment = findViewById(R.id.et_comment);
    }

    private void showAddCommentContainer(int itemCount) {
        if (itemCount > 0) {
            mCommentsContainerRl.setVisibility(View.VISIBLE);
            mNoCommentLL.setVisibility(View.GONE);
        } else {
            mCommentsContainerRl.setVisibility(View.GONE);
            mNoCommentLL.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (AddCommentOrReply.ACTIVITY_START_CODE == requestCode) {
            if (RESULT_OK == resultCode) {
                if (data != null && data.hasExtra(PARENT_COMMENT_ID)) {
                    final LiveData<Comment> commentLiveData = mCommentsViewModel.getComment(data.getStringExtra(PARENT_COMMENT_ID));
                    commentLiveData.observe(this, new Observer<Comment>() {
                        @Override
                        public void onChanged(Comment comment) {
                            if (comment != null) {
                                int replyCount = comment.getReplyCount() + 1;
                                comment.setReplyCount(replyCount);
                                mCommentsViewModel.increaseReplyCount(comment);
                                // Removing observer here else it will go into infinite loop
                                commentLiveData.removeObservers(DiscussionDetailsActivity.this);
                            }
                        }
                    });
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void postComment(View view) {
        String commentString = etAddComment.getText().toString().trim();
        if (TextUtils.isEmpty(commentString)) {
            etAddComment.setError(getString(R.string.error_required));
            return;
        }

        mCommentsViewModel.createComment(mDiscussionId, null, commentString);
    }

    public void newComment(View view) {
        AddCommentOrReply.startActivity(this, mDiscussionId, null);
    }
}
