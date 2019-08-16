package com.sample.discussionforum.onboarding.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.sample.discussionforum.R;
import com.sample.discussionforum.onboarding.ui.animation.CardMorphingAnimation;

public class OnBoardingActivity extends AppCompatActivity {

    private OnBoardingCardView mCardView;
    private ImageView ivMan;
    private ImageView ivThumb;
    private ImageView ivLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        mCardView = findViewById(R.id.cardview);
        ivMan = findViewById(R.id.iv_man);
        ivThumb = findViewById(R.id.iv_man2);
        ivLogout = findViewById(R.id.iv_man3);
    }

    public void animateMe(View view) {
        OnBoardingCardView.Params circle = OnBoardingCardView.Params.create()
                .duration(300)
                .cornerRadius(550)
                .width(750)
                .height(750).animationListener(new CardMorphingAnimation.Listener() {
                    @Override
                    public void onAnimationEnd() {
                        ivMan.setVisibility(View.VISIBLE);
                        ivLogout.setVisibility(View.VISIBLE);
                        ivThumb.setVisibility(View.VISIBLE);
                    }
                });
        mCardView.morph(circle);

    }

    public static void start(Context context) {
        Intent intent =  new Intent(context, OnBoardingActivity.class);
        context.startActivity(intent);
    }
}
