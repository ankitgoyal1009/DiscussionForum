package com.sample.discussionforum.onboarding.ui.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.ViewGroup;

import com.sample.discussionforum.onboarding.ui.OnBoardingCardView;
import com.sample.discussionforum.onboarding.ui.OnBoardingGradientDrawable;

import androidx.annotation.NonNull;

public class CardMorphingAnimation {
    private Params mParams;

    public CardMorphingAnimation(@NonNull Params params) {
        mParams = params;
    }

    public void start() {
        OnBoardingGradientDrawable background = mParams.mCardView.getDrawable();

        ObjectAnimator cornerAnimation =
                ObjectAnimator.ofFloat(background, "cornerRadius", mParams.fromCornerRadius, mParams.toCornerRadius);

        ValueAnimator heightAnimation = ValueAnimator.ofInt(mParams.fromHeight, mParams.toHeight);
        heightAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mParams.mCardView.getLayoutParams();
                layoutParams.height = val;
                mParams.mCardView.setLayoutParams(layoutParams);
            }
        });

        ValueAnimator widthAnimation = ValueAnimator.ofInt(mParams.fromWidth, mParams.toWidth);
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mParams.mCardView.getLayoutParams();
                layoutParams.width = val;
                mParams.mCardView.setLayoutParams(layoutParams);
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(mParams.duration);
        animatorSet.playTogether(cornerAnimation, heightAnimation, widthAnimation);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mParams.animationListener != null) {
                    mParams.animationListener.onAnimationEnd();
                }
            }
        });
        animatorSet.start();
    }

    public interface Listener {
        void onAnimationEnd();
    }

    public static class Params {

        private float fromCornerRadius;
        private float toCornerRadius;

        private int fromHeight;
        private int toHeight;

        private int fromWidth;
        private int toWidth;

        private int duration;

        private OnBoardingCardView mCardView;
        private CardMorphingAnimation.Listener animationListener;

        private Params(@NonNull OnBoardingCardView cardView) {
            this.mCardView = cardView;
        }

        public static Params create(@NonNull OnBoardingCardView button) {
            return new Params(button);
        }

        public Params duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Params listener(@NonNull CardMorphingAnimation.Listener animationListener) {
            this.animationListener = animationListener;
            return this;
        }

        public Params cornerRadius(int fromCornerRadius, int toCornerRadius) {
            this.fromCornerRadius = fromCornerRadius;
            this.toCornerRadius = toCornerRadius;
            return this;
        }

        public Params height(int fromHeight, int toHeight) {
            this.fromHeight = fromHeight;
            this.toHeight = toHeight;
            return this;
        }

        public Params width(int fromWidth, int toWidth) {
            this.fromWidth = fromWidth;
            this.toWidth = toWidth;
            return this;
        }
    }

}

