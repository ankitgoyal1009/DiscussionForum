package com.sample.discussionforum.discussions.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.discussionforum.R;
import com.sample.discussionforum.comments.CommentsViewModel;
import com.sample.discussionforum.comments.data.Comment;
import com.sample.discussionforum.comments.ui.CommentsAdapter;
import com.sample.discussionforum.common.Status;
import com.sample.discussionforum.common.data.StatusAwareResponse;
import com.sample.discussionforum.discussions.DiscussionsViewModel;
import com.sample.discussionforum.discussions.data.Discussion;

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
        Discussion discussion = viewModel.getDiscussion(discussionId);

        CommentsViewModel commentsViewModel = ViewModelProviders.of(this).get(CommentsViewModel.class);

        TextView tvDiscussionTitle = findViewById(R.id.tv_discussion_title);
        tvDiscussionTitle.setText(discussion.getTitle());

        TextView tvDiscussionDetail = findViewById(R.id.tv_discussion);
        tvDiscussionDetail.setText(discussion.getDescription());

        //dummy comments
//        commentsViewModel.createComment("This is dummy comment");
        final CommentsAdapter adapter = new CommentsAdapter(this, commentsViewModel.getAllComment());
        commentsViewModel.getLiveData().observe(this, new Observer<StatusAwareResponse<Comment>>() {
            @Override
            public void onChanged(@Nullable StatusAwareResponse<Comment> response) {
                if (response == null) {
                    return;
                }
                if (response.getStatus() == Status.success) {
                    Toast.makeText(DiscussionDetailsActivity.this, "Added comment", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                } else if (response.getStatus() == Status.failed) {
                    Toast.makeText(DiscussionDetailsActivity.this, "Failed to add comment", Toast.LENGTH_SHORT).show();
                }
            }
        });

        RecyclerView commentsRv = findViewById(R.id.rv_comments);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        commentsRv.setLayoutManager(manager);
        commentsRv.setItemAnimator(new DefaultItemAnimator());
        commentsRv.setAdapter(adapter);
    }
}
