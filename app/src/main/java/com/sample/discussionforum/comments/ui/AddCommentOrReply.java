package com.sample.discussionforum.comments.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.sample.discussionforum.R;
import com.sample.discussionforum.comments.CommentsViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class AddCommentOrReply extends AppCompatActivity {

    public static final String PARENT_COMMENT_ID = "parentCommentId";
    public static final int ACTIVITY_START_CODE = 100;
    private static final String DISCUSSION_ID = "discussionId";
    private EditText etComment;
    private CommentsViewModel mViewModel;

    public static void startActivity(AppCompatActivity activity, String discussionId, String parentCommentId) {
        Intent intent = new Intent(activity, AddCommentOrReply.class);
        intent.putExtra(DISCUSSION_ID, discussionId);
        intent.putExtra(PARENT_COMMENT_ID, parentCommentId);
        activity.startActivityForResult(intent, ACTIVITY_START_CODE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment_or_reply);

        etComment = findViewById(R.id.et_comment);
        mViewModel = ViewModelProviders.of(this).get(CommentsViewModel.class);
    }

    public void postComment(View view) {
        String commentText = etComment.getText().toString().trim();
        if (TextUtils.isEmpty(commentText)) {
            etComment.setError(getString(R.string.error_required));
            return;
        }
        String discussionId = getIntent().getStringExtra(DISCUSSION_ID);
        String parenCommentId = getIntent().getStringExtra(PARENT_COMMENT_ID);
        mViewModel.createComment(discussionId, parenCommentId, commentText);
        Intent intent = new Intent();
        intent.putExtra(PARENT_COMMENT_ID, parenCommentId);
        setResult(RESULT_OK, intent);
        this.finish();
    }
}
