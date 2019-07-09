package com.sample.discussionforum.discussions.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sample.discussionforum.R;
import com.sample.discussionforum.common.ui.BaseActivity;
import com.sample.discussionforum.discussions.DiscussionsViewModel;
import com.sample.discussionforum.discussions.data.Discussion;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class DiscussionsListActivity extends BaseActivity {

    private DiscussionAdapter adapter;
    private DiscussionsViewModel viewModel;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, DiscussionsListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discussions_list);
        viewModel = ViewModelProviders.of(this).get(DiscussionsViewModel.class);
        // This is to generate dummy discussions first time.
        viewModel.getAllDiscussions().observe(this, new Observer<List<Discussion>>() {
            @Override
            public void onChanged(List<Discussion> discussions) {
                if (discussions == null || discussions.size() == 0) {
                    viewModel.initDummyDiscussions();
                }
            }
        });
        adapter = new DiscussionAdapter(this);
        viewModel.getPublishedDiscussions().observe(this, new Observer<List<Discussion>>() {
            @Override
            public void onChanged(List<Discussion> discussions) {
                if (discussions != null) {
                    adapter.updateDiscussions(discussions);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        ListView listView = findViewById(R.id.lv_discussions);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = ((Discussion) adapterView.getAdapter().getItem(i)).getId();
                DiscussionDetailsActivity.startActivity(DiscussionsListActivity.this, id);
            }
        });
    }
}
