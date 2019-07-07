package com.sample.discussionforum.discussions;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.format.DateUtils;

import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.sample.discussionforum.R;
import com.sample.discussionforum.common.Gson;
import com.sample.discussionforum.common.Utils;
import com.sample.discussionforum.common.data.LocalDB;
import com.sample.discussionforum.data.SharedPrefUtil;
import com.sample.discussionforum.discussions.data.Discussion;
import com.sample.discussionforum.discussions.data.DiscussionsDao;
import com.sample.discussionforum.login.data.UserDao;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import static com.sample.discussionforum.data.SharedPrefUtil.getSharedPref;

public class DiscussionRepository {
    private static DiscussionRepository sInstance;
    private static DiscussionsDao discussionsDao;

    private DiscussionRepository() {
    }

    static DiscussionRepository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DiscussionRepository();
        }

        discussionsDao = LocalDB.getInstance(context).getDiscussionsDao();
        return sInstance;
    }

    public void initDummyDiscussions(final Context context) {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object[] objects) {
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
                    return null;
                }

                Calendar calendar = Calendar.getInstance();
                for (int i = 0; i < list.size(); i++) {
                    Discussion discussion = list.get(i);
                    // Reducing an hour to make sure we get all record immediately after inserting. Ideally we should be taking start of the day
                    long l = calendar.getTime().getTime() - DateUtils.HOUR_IN_MILLIS;
                    discussion.setDate(new Date(l));
                    calendar.setTimeInMillis(calendar.getTimeInMillis() + DateUtils.DAY_IN_MILLIS);
                }
                discussionsDao.insertAll(list);

                return null;
            }
        }.execute();
    }

    public LiveData<Discussion> getDiscussion(String discussionId) {
        return discussionsDao.getDiscussion(discussionId);
    }

    public LiveData<List<Discussion>> getPublishedDiscussions() {
        return discussionsDao.getAllPublishedDiscussion(System.currentTimeMillis());
    }

    public LiveData<List<Discussion>> getAllDiscussions() {
        return discussionsDao.getAllDiscussion();
    }
}