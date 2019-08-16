package com.sample.discussionforum.onboarding.ui.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

public class FeatureHighlightAnimation {

    public static void startAnimation(View sourceView, AnimationParams params) {
        ObjectAnimator animationX = ObjectAnimator.ofFloat(sourceView, "translationX", params.getxDisplacement());
        animationX.setDuration(params.getAnimationDuration());

        ObjectAnimator animationY = ObjectAnimator.ofFloat(sourceView, "translationY", params.getyDisplacement());
        animationY.setDuration(params.getAnimationDuration());

        ObjectAnimator fadeOutAnimator = ObjectAnimator.ofFloat(sourceView, "alpha", params.getAlphaFrom(), params.getAlphaTo());
        fadeOutAnimator.setDuration(params.getAnimationDuration());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animationX).with(animationY);
        animatorSet.play(animationX).with(fadeOutAnimator);
        animatorSet.start();

    }

    public static class AnimationParams {
        private float xDisplacement;
        private float yDisplacement;
        private long animationDuration;
        private float alphaFrom;
        private float alphaTo;

        private AnimationParams() {
        }

        public static AnimationParams createAnimationParams() {
            return new AnimationParams();
        }
        public float getxDisplacement() {
            return xDisplacement;
        }

        public AnimationParams setxDisplacement(float xDisplacement) {
            this.xDisplacement = xDisplacement;
            return this;
        }

        public float getyDisplacement() {
            return yDisplacement;
        }

        public AnimationParams setyDisplacement(float yDisplacement) {
            this.yDisplacement = yDisplacement;
            return this;
        }

        public long getAnimationDuration() {
            return animationDuration;
        }

        public AnimationParams setAnimationDuration(long animationDuration) {
            this.animationDuration = animationDuration;
            return this;
        }

        public float getAlphaFrom() {
            return alphaFrom;
        }

        public AnimationParams setAlphaFrom(float alphaFrom) {
            this.alphaFrom = alphaFrom;
            return this;
        }

        public float getAlphaTo() {
            return alphaTo;
        }

        public AnimationParams setAlphaTo(float alphaTo) {
            this.alphaTo = alphaTo;
            return this;
        }
    }
}
