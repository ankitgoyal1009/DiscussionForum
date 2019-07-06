package com.sample.discussionforum.discussions.ui;


import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sample.discussionforum.R;
import com.sample.discussionforum.common.DateUtils;
import com.sample.discussionforum.discussions.data.Discussion;

import java.util.Date;
import java.util.List;

public class DiscussionAdapter extends BaseAdapter {
    private AppCompatActivity mActivity;
    private List<Discussion> mDiscussions;

    public DiscussionAdapter(AppCompatActivity activity, List<Discussion> discussions) {
        mActivity = activity;
        mDiscussions = discussions;
    }

    public void updateDiscussions(List<Discussion> discussions) {
        mDiscussions = discussions;
    }

    @Override
    public int getCount() {
        return mDiscussions == null ? 0 : mDiscussions.size();
    }

    @Override
    public Discussion getItem(int i) {
        return mDiscussions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Long.parseLong(getItem(i).getId());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewGroupHolder holder;
        if (view == null) {
            view = mActivity.getLayoutInflater().inflate(R.layout.discussions_row, null);
            holder = new ViewGroupHolder();
            holder.tvDiscussion = view.findViewById(R.id.tv_discussion);
            holder.tvDiscussionDate = view.findViewById(R.id.tv_discussion_date);
            view.setTag(holder);
        } else {
            holder = (ViewGroupHolder) view.getTag();
        }

        Date publishedDate = getItem(i).getDate();

        if (DateUtils.isItToday(publishedDate.getTime())) {
            view.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.pale_yellow));
        } else {
            view.setBackgroundColor(ContextCompat.getColor(mActivity, android.R.color.white));
        }

        holder.tvDiscussion.setText(getItem(i).getTitle());
        holder.tvDiscussionDate.setText(DateUtils.dateToString(publishedDate));
        return view;
    }

    static class ViewGroupHolder {
        TextView tvDiscussion;
        TextView tvDiscussionDate;
    }
}
