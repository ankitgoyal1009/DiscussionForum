package com.sample.discussionforum.onboarding.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.StateSet;

import com.sample.discussionforum.R;
import com.sample.discussionforum.onboarding.ui.animation.CardMorphingAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

public class OnBoardingCardView extends CardView {
    private OnBoardingGradientDrawable mDrawable;
    private int mCornerRadius;

    public OnBoardingCardView(@NonNull Context context) {
        super(context);
        initView();
    }

    public OnBoardingCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public OnBoardingCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public OnBoardingGradientDrawable getDrawable() {
        return mDrawable;
    }

    private void initView() {
        Resources resources = getResources();
        int cornerRadius = (int) resources.getDimension(R.dimen.padding_4dp);
        int blue = resources.getColor(R.color.colorPrimary);

        StateListDrawable background = new StateListDrawable();
        mDrawable = createDrawable(blue, cornerRadius);

        mCornerRadius = cornerRadius;

        background.addState(StateSet.WILD_CARD, mDrawable.getGradientDrawable());

        setBackground(background);
    }

    private OnBoardingGradientDrawable createDrawable(int color, int cornerRadius) {
        OnBoardingGradientDrawable drawable = new OnBoardingGradientDrawable(new GradientDrawable());
        drawable.getGradientDrawable().setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);
        drawable.setCornerRadius(cornerRadius);
        return drawable;
    }

    public static class Params {
        private int cornerRadius;
        private int width;
        private int height;
        private int duration;
        private CardMorphingAnimation.Listener animationListener;

        private Params() {

        }

        public static Params create() {
            return new Params();
        }

        public Params cornerRadius(int cornerRadius) {
            this.cornerRadius = cornerRadius;
            return this;
        }

        public Params width(int width) {
            this.width = width;
            return this;
        }

        public Params height(int height) {
            this.height = height;
            return this;
        }

        public Params duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Params animationListener(CardMorphingAnimation.Listener animationListener) {
            this.animationListener = animationListener;
            return this;
        }
    }

    public void morph(@NonNull Params params) {
        morphWithAnimation(params);
        mCornerRadius = params.cornerRadius;
    }

    private void morphWithAnimation(@NonNull final Params params) {
        CardMorphingAnimation.Params animationParams = CardMorphingAnimation.Params.create(this)
                .cornerRadius(mCornerRadius, params.cornerRadius)
                .height(getHeight(), params.height)
                .width(getWidth(), params.width)
                .duration(params.duration)
                .listener(new CardMorphingAnimation.Listener() {
                    @Override
                    public void onAnimationEnd() {
                        finalizeMorphing(params);
                    }
                });

        CardMorphingAnimation animation = new CardMorphingAnimation(animationParams);
        animation.start();
    }
    private void finalizeMorphing(@NonNull Params params) {
        if (params.animationListener != null) {
            params.animationListener.onAnimationEnd();
        }
    }

}
