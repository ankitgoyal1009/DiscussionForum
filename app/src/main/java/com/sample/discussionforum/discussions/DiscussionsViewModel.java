package com.sample.discussionforum.discussions;

import android.app.Application;

import com.sample.discussionforum.discussions.data.Discussion;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class DiscussionsViewModel extends AndroidViewModel {
    private DiscussionRepository mRepository;
    private Application mApplication;

    public DiscussionsViewModel(@NonNull Application application) {
        super(application);
        mRepository = DiscussionRepository.getInstance(application);
        mApplication = application;
    }

    public void initDummyDiscussions() {
        mRepository.initDummyDiscussions(mApplication);
    }

    public LiveData<List<Discussion>> getPublishedDiscussions() {
        return mRepository.getPublishedDiscussions();
    }

    public LiveData<Discussion> getDiscussion(String discussionId) {
        return mRepository.getDiscussion(discussionId);
    }

    public LiveData<List<Discussion>> getAllDiscussions() {
        return mRepository.getAllDiscussions();
    }
}
