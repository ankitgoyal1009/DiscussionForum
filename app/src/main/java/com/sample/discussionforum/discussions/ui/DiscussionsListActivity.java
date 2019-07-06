package com.sample.discussionforum.discussions.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.sample.discussionforum.R;
import com.sample.discussionforum.discussions.DiscussionsViewModel;
import com.sample.discussionforum.discussions.data.Discussion;

import java.util.List;

public class DiscussionsListActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, DiscussionsListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discussions_list);
        DiscussionsViewModel viewModel = ViewModelProviders.of(this).get(DiscussionsViewModel.class);
        viewModel.initDiscussions();
        List<Discussion> discussions = viewModel.getPublishedDiscussions();
        DiscussionAdapter adapter = new DiscussionAdapter(this, discussions);
        ListView listView = findViewById(R.id.lv_discussions);
        listView.setAdapter(adapter);
    }
}
