package com.sample.discussionforum.onboarding.ui.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

import androidx.core.view.ViewCompat;

public class ScaleAnimation {
    public static void startAnimation(View sourceView, AnimationParams params) {

        ObjectAnimator animationScaleX = ObjectAnimator.ofFloat(sourceView, View.SCALE_X,params.getFromSize(), params.getToSize());
        animationScaleX.setDuration(params.getAnimationDuration());

        ObjectAnimator animationScaleY = ObjectAnimator.ofFloat(sourceView, View.SCALE_Y,params.getFromSize(),params.getToSize());
        animationScaleY.setDuration(params.getAnimationDuration());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animationScaleX)
                .with(animationScaleY);
        animatorSet.start();
    }

    public static class AnimationParams {
        private float fromSize;
        private float toSize;
        private long animationDuration;

        private AnimationParams() {
        }

        public static AnimationParams createAnimationParams() {
            return new AnimationParams();
        }

        public float getFromSize() {
            return fromSize;
        }

        public AnimationParams setFromSize(float fromSize) {
            this.fromSize = fromSize;
            return this;
        }

        public float getToSize() {
            return toSize;
        }

        public AnimationParams setToSize(float toSize) {
            this.toSize = toSize;
            return this;
        }

        public long getAnimationDuration() {
            return animationDuration;
        }

        public AnimationParams setAnimationDuration(long animationDuration) {
            this.animationDuration = animationDuration;
            return this;
        }
    }
}
