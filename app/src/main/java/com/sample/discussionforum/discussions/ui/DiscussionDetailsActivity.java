package com.sample.discussionforum.discussions.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.discussionforum.R;
import com.sample.discussionforum.comments.CommentsViewModel;
import com.sample.discussionforum.comments.data.Comment;
import com.sample.discussionforum.comments.ui.CommentsAdapter;
import com.sample.discussionforum.discussions.DiscussionsViewModel;
import com.sample.discussionforum.discussions.data.Discussion;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DiscussionDetailsActivity extends AppCompatActivity {

    public static final String DISCUSSION_ID = "discussion_id";

    public static void startActivity(Context context, String discussionId) {
        Intent intent = new Intent(context, DiscussionDetailsActivity.class);
        intent.putExtra(DISCUSSION_ID, discussionId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_details);
        String discussionId = "";
        if (getIntent() != null) {
            if (getIntent().hasExtra(DISCUSSION_ID)) {
                discussionId = getIntent().getStringExtra(DISCUSSION_ID);
            }
        }
        if (TextUtils.isEmpty(discussionId)) {
            Toast.makeText(DiscussionDetailsActivity.this, R.string.error_general, Toast.LENGTH_SHORT).show();
            return;
        }
        DiscussionsViewModel viewModel = ViewModelProviders.of(this).get(DiscussionsViewModel.class);
        viewModel.getDiscussion(discussionId).observe(this, new Observer<Discussion>() {
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

            }
        });

        final CommentsAdapter adapter = new CommentsAdapter(this);
        CommentsViewModel commentsViewModel = ViewModelProviders.of(this).get(CommentsViewModel.class);
        commentsViewModel.getAllComment(discussionId).observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> comments) {
                if (comments != null) {
                    adapter.setOrUpdateCommentList(comments);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        //dummy comments
        commentsViewModel.createComment(discussionId, null, "This is dummy comment");
        RecyclerView commentsRv = findViewById(R.id.rv_comments);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        commentsRv.setLayoutManager(manager);
        commentsRv.setItemAnimator(new DefaultItemAnimator());
        commentsRv.setAdapter(adapter);
    }
}