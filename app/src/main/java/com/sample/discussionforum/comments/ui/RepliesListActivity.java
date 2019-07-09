package com.sample.discussionforum.comments.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sample.discussionforum.R;
import com.sample.discussionforum.comments.CommentsViewModel;
import com.sample.discussionforum.comments.data.Comment;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RepliesListActivity extends AppCompatActivity {
    public static final String PARENT_COMMENT_ID = "parentCommentId";

    public static void startActivity(Context context, String commentId) {
        Intent intent = new Intent(context, RepliesListActivity.class);
        intent.putExtra(PARENT_COMMENT_ID, commentId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replies_list);
        String parenCommentId = "";
        if (getIntent() != null) {
            if (getIntent().hasExtra(PARENT_COMMENT_ID)) {
                parenCommentId = getIntent().getStringExtra(PARENT_COMMENT_ID);
            }
        }
        final CommentsAdapter adapter = new CommentsAdapter(this);
        adapter.setAllowActions(false);
        CommentsViewModel commentsViewModel = ViewModelProviders.of(this).get(CommentsViewModel.class);
        commentsViewModel.getAllRepliesOnComment(parenCommentId).observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> comments) {
                if (comments != null) {
                    adapter.setOrUpdateCommentList(comments);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        RecyclerView mCommentsRv = findViewById(R.id.rv_comments);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mCommentsRv.setLayoutManager(manager);
        mCommentsRv.setItemAnimator(new DefaultItemAnimator());
        mCommentsRv.setAdapter(adapter);

    }
}
