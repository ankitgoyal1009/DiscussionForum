package com.sample.discussionforum.onboarding.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.sample.discussionforum.R;
import com.sample.discussionforum.onboarding.OnBoardingStates;
import com.sample.discussionforum.onboarding.ui.animation.CardMorphingAnimation;
import com.sample.discussionforum.onboarding.ui.animation.FeatureHighlightAnimation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

public class OnBoardingActivity extends AppCompatActivity {

    private OnBoardingCardView mCardView;
    private ImageView ivMan;
    private ImageView ivThumb;
    private ImageView ivLike;
    private OnBoardingStates mCurrentState = OnBoardingStates.START;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        mCardView = findViewById(R.id.cardview);
        ivMan = findViewById(R.id.iv_man);
        ivThumb = findViewById(R.id.iv_thumbsup);
        ivLike = findViewById(R.id.iv_like);
    }
    private void animateFeatureView(final View sourceView){
        // Calculating new position based on current position of source view.
        float x = (mCardView.getX()+mCardView.getWidth()/2)-sourceView.getX();
        float y = (mCardView.getY()+mCardView.getHeight()/2)-sourceView.getY();

        // This is a work around for default relativeLayout arrangement for all childViews.
        ViewCompat.setElevation(sourceView, ViewCompat.getElevation(mCardView)+1);

        FeatureHighlightAnimation.AnimationParams params = FeatureHighlightAnimation.AnimationParams.createAnimationParams();
        params.setxDisplacement(x)
                .setyDisplacement(y)
                .setAnimationDuration(1000)
                .setAlphaFrom(1f)
                .setAlphaTo(0);
        FeatureHighlightAnimation.startAnimation(sourceView, params);
    }

    public void startOnboarding(View view) {

        switch (mCurrentState){
            case START:{
                OnBoardingCardView.Params circle = OnBoardingCardView.Params.create()
                        .duration(300)
                        .cornerRadius(550)
                        .width(750)
                        .height(750).animationListener(new CardMorphingAnimation.Listener() {
                            @Override
                            public void onAnimationEnd() {
                                ivMan.setVisibility(View.VISIBLE);
                                ivLike.setVisibility(View.VISIBLE);
                                ivThumb.setVisibility(View.VISIBLE);
                            }
                        });
                mCardView.morph(circle);
                mCurrentState = OnBoardingStates.FIRST;
                break;
            }
            case FIRST:        {
                animateFeatureView(ivMan);
                mCurrentState = OnBoardingStates.SECOND;
                break;
            }
            case SECOND:        {
                animateFeatureView(ivLike);
                mCurrentState = OnBoardingStates.THIRD;
                break;
            }
            case THIRD:        {
                animateFeatureView(ivThumb);
                mCurrentState = OnBoardingStates.FINISHED;
                break;
            }
        }

    }

    public static void start(Context context) {
        Intent intent =  new Intent(context, OnBoardingActivity.class);
        context.startActivity(intent);
    }
}
