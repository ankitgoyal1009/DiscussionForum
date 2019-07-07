package com.sample.discussionforum.discussions.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.discussionforum.R;
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

        TextView tvDiscussionTitle = findViewById(R.id.tv_discussion_title);
        tvDiscussionTitle.setText(discussion.getTitle());

        TextView tvDiscussionDetail = findViewById(R.id.tv_discussion);
        tvDiscussionDetail.setText(discussion.getDescription());
    }
}
