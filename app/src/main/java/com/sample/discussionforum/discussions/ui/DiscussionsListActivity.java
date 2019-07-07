package com.sample.discussionforum.discussions.ui;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = ((Discussion) adapterView.getAdapter().getItem(i)).getId();
                DiscussionDetailsActivity.startActivity(DiscussionsListActivity.this, id);
            }
        });
    }
}
