package com.sample.discussionforum.discussions;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateUtils;

import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.sample.discussionforum.R;
import com.sample.discussionforum.common.Gson;
import com.sample.discussionforum.common.Utils;
import com.sample.discussionforum.data.SharedPrefUtil;
import com.sample.discussionforum.discussions.data.Discussion;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.sample.discussionforum.data.SharedPrefUtil.getSharedPref;

public class DiscussionRepository {
    private static DiscussionRepository sInstance;

    private DiscussionRepository() {
    }

    static DiscussionRepository getInstance() {
        if (sInstance == null) {
            sInstance = new DiscussionRepository();
        }
        return sInstance;
    }

    public void initDummyDiscussions(Context context) {
        Map<String, Discussion> existingDiscussions = getDiscussions(context);
        if (existingDiscussions == null || existingDiscussions.size() <= 0) {
            Type type = new TypeToken<List<Discussion>>() {
            }.getType();
            List<Discussion> list;
            try {
                list = Gson.getInstance().fromJson(
                        Utils.readRawFile(context, R.raw.dummy), type);
            } catch (JsonParseException e) {
                list = Collections.emptyList();
            }
            if (list == null) {
                // TODO: 05/07/19 Ankit post error from here
                return;
            }
            existingDiscussions = new LinkedHashMap<>();
            Calendar calendar = Calendar.getInstance();
            for (int i = 0; i < list.size(); i++) {
                Discussion discussion = list.get(i);
                discussion.setDate(calendar.getTime());
                existingDiscussions.put(discussion.getId(), discussion);
                calendar.setTimeInMillis(calendar.getTimeInMillis() + DateUtils.DAY_IN_MILLIS);
            }
            saveDiscussions(context, existingDiscussions);
        }
    }

    public Discussion getDiscussion(Context context, String discussionId) {
        Discussion discussion = null;
        SharedPreferences preferences = getSharedPref(context);
        Type type = new TypeToken<Map<String, Discussion>>() {
        }.getType();
        Map<String, Discussion> discussions = Gson.getInstance().fromJson(preferences.getString(SharedPrefUtil.PREF_DISCUSSIONS, null), type);
        if (discussions != null && discussions.size() > 0) {
            discussion = discussions.get(discussionId);
        }
        return discussion;
    }

    public Map<String, Discussion> getPublishedDiscussions(Context context) {
        Map<String, Discussion> publishedDiscussions = new LinkedHashMap<>();
        Map<String, Discussion> allDiscussions = getDiscussions(context);
        if (allDiscussions != null && allDiscussions.size() > 0) {
            Date currentDate = new Date();
            for (Map.Entry<String, Discussion> entry : allDiscussions.entrySet()) {
                Discussion discussion = entry.getValue();
                if (discussion.getDate().compareTo(currentDate) <= 0) {
                    publishedDiscussions.put(entry.getKey(), discussion);
                }
            }
        }

        return publishedDiscussions;
    }

    private Map<String, Discussion> getDiscussions(Context context) {
        Map<String, Discussion> discussions;
        SharedPreferences preferences = getSharedPref(context);
        Type type = new TypeToken<Map<String, Discussion>>() {
        }.getType();
        discussions = Gson.getInstance().fromJson(preferences.getString(SharedPrefUtil.PREF_DISCUSSIONS, null), type);
        return discussions;
    }

    private void saveDiscussions(Context context, Map<String, Discussion> discussions) {
        SharedPreferences preferences = getSharedPref(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SharedPrefUtil.PREF_DISCUSSIONS, Gson.getInstance().toJson(discussions));
        editor.apply();
    }
}