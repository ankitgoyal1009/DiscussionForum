package com.sample.discussionforum.onboarding.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.sample.discussionforum.R;
import com.sample.discussionforum.discussions.ui.DiscussionsListActivity;
import com.sample.discussionforum.login.LoginViewModel;
import com.sample.discussionforum.login.ui.LoginActivity;
import com.sample.discussionforum.onboarding.OnBoardingStates;
import com.sample.discussionforum.onboarding.ui.animation.CardMorphingAnimation;
import com.sample.discussionforum.onboarding.ui.animation.FeatureHighlightAnimation;
import com.sample.discussionforum.onboarding.ui.animation.ScaleAnimation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProviders;

public class OnBoardingActivity extends AppCompatActivity {

    public static final int ANIMATION_DURATION = 1000;
    private OnBoardingCardView mCardView;
    private ImageView ivComment;
    private ImageView ivThumb;
    private ImageView ivLike;
    private OnBoardingStates mCurrentState = OnBoardingStates.START;
    private TextSwitcher mTextSwitcher;
    private Button mNextButton;

    public static void start(Context context) {
        Intent intent = new Intent(context, OnBoardingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        mCardView = findViewById(R.id.cardview);
        ivComment = findViewById(R.id.iv_comment);
        ivThumb = findViewById(R.id.iv_thumbsup);
        ivLike = findViewById(R.id.iv_like);
        mTextSwitcher = findViewById(R.id.txt_feature_details);
        mNextButton = findViewById(R.id.btn_next);
        mTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                // TODO Auto-generated method stub
                // create a TextView
                TextView t = new TextView(OnBoardingActivity.this);
                // set the gravity of text to top and center horizontal
                t.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                // set displayed text size
                return t;
            }
        });

        Animation in = AnimationUtils.loadAnimation(this, R.anim.slide_in);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        in.setDuration(ANIMATION_DURATION);
        out.setDuration(ANIMATION_DURATION);
        // set the animation type to TextSwitcher
        mTextSwitcher.setInAnimation(in);
        mTextSwitcher.setOutAnimation(out);

        mTextSwitcher.setCurrentText(getString(R.string.onboarding_msg_main));

    }

    private void animateAndMakeItSmall(final View sourceView) {
        ViewCompat.setElevation(sourceView, ViewCompat.getElevation(mCardView) + 1);
        ScaleAnimation.AnimationParams params = ScaleAnimation.AnimationParams.createAnimationParams()
                .setAnimationDuration(ANIMATION_DURATION)
                .setFromSize(1.8f)
                .setToSize(1);
        ScaleAnimation.startAnimation(sourceView, params);
    }

    private void animateAndMakeItLarge(final View sourceView) {
        ViewCompat.setElevation(sourceView, ViewCompat.getElevation(mCardView) + 1);
        ScaleAnimation.AnimationParams params = ScaleAnimation.AnimationParams.createAnimationParams()
                .setAnimationDuration(ANIMATION_DURATION)
                .setFromSize(1)
                .setToSize(1.8f);

        ScaleAnimation.startAnimation(sourceView, params);
    }

    private void animateFeatureView(final View sourceView) {
        // Calculating new position based on current position of source view.
        float x = (mCardView.getX() + mCardView.getWidth() / 2) - sourceView.getX();
        float y = (mCardView.getY() + mCardView.getHeight() / 2) - sourceView.getY();

        // This is a work around for default relativeLayout arrangement for all childViews.
        ViewCompat.setElevation(sourceView, ViewCompat.getElevation(mCardView) + 1);

        FeatureHighlightAnimation.AnimationParams params = FeatureHighlightAnimation.AnimationParams.createAnimationParams();
        params.setxDisplacement(x)
                .setyDisplacement(y)
                .setAnimationDuration(ANIMATION_DURATION)
                .setAlphaFrom(1f)
                .setAlphaTo(0);
        FeatureHighlightAnimation.startAnimation(sourceView, params);
    }

    public void startOnboarding(View view) {

        switch (mCurrentState) {
            case START: {
                mCardView.getWidth();
                OnBoardingCardView.Params circle = OnBoardingCardView.Params.create()
                        .duration(300)
                        .cornerRadius(550)
                        .width(mCardView.getWidth())
                        .height(mCardView.getHeight()).animationListener(new CardMorphingAnimation.Listener() {
                            @Override
                            public void onAnimationEnd() {
                                ivComment.setVisibility(View.VISIBLE);
                                ivLike.setVisibility(View.VISIBLE);
                                ivThumb.setVisibility(View.VISIBLE);
                                animateAndMakeItLarge(ivComment);
                                mNextButton.setText(R.string.next);
                                mTextSwitcher.setText("You can comment on any topic");
                                mCurrentState = OnBoardingStates.FIRST;
                            }
                        });
                mCardView.morph(circle);
                break;
            }
            case FIRST: {
                animateAndMakeItLarge(ivLike);
                animateFeatureView(ivComment);
                animateAndMakeItSmall(ivComment);
                mTextSwitcher.setText("You can like comments");
                mCurrentState = OnBoardingStates.SECOND;
                break;
            }
            case SECOND: {
                animateFeatureView(ivLike);
                animateAndMakeItSmall(ivLike);
                animateAndMakeItLarge(ivThumb);
                mTextSwitcher.setText("You can upvote comments");
                mCurrentState = OnBoardingStates.THIRD;
                break;
            }
            case THIRD: {
                animateFeatureView(ivThumb);
                animateAndMakeItSmall(ivThumb);
                mTextSwitcher.setVisibility(View.GONE);
                mCurrentState = OnBoardingStates.FINISHED;
                mNextButton.setText(R.string.let_start);
                break;
            }
            case FINISHED: {
                final LoginViewModel model = ViewModelProviders.of(this).get(LoginViewModel.class);
                if (!model.isUserLoggedIn()) {
                    LoginActivity.startActivity(OnBoardingActivity.this);
                } else {
                    DiscussionsListActivity.startActivity(OnBoardingActivity.this);
                }
                OnBoardingActivity.this.finish();
            }
        }

    }
}
