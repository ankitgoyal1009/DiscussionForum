package com.sample.discussionforum.discussions;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.sample.discussionforum.common.data.StatusAwareResponse;
import com.sample.discussionforum.discussions.data.Discussion;

import java.util.ArrayList;
import java.util.List;

public class DiscussionsViewModel extends AndroidViewModel {
    private MutableLiveData<StatusAwareResponse<Discussion>> mLiveData;
    private DiscussionRepository mRepository;
    private Application mApplication;

    public DiscussionsViewModel(@NonNull Application application) {
        super(application);
        mLiveData = new MutableLiveData<>();
        mRepository = DiscussionRepository.getInstance();
        mApplication = application;
    }

    public void initDiscussions() {
        mRepository.initDummyDiscussions(mApplication);
    }

    public List<Discussion> getPublishedDiscussions() {
        return new ArrayList<>(mRepository.getPublishedDiscussions(mApplication).values());
    }

    public Discussion getDiscussion(String discussionId) {
        return mRepository.getDiscussion(mApplication, discussionId);
    }
}
