package com.sample.discussionforum.onboarding.ui;

import android.graphics.drawable.GradientDrawable;

public class OnBoardingGradientDrawable {
    private GradientDrawable mGradientDrawable;
    private float mRadius;
    private int mColor;

    public OnBoardingGradientDrawable(GradientDrawable drawable) {
        mGradientDrawable = drawable;
    }

    public void setCornerRadius(float radius) {
        mRadius = radius;
        mGradientDrawable.setCornerRadius(radius);
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
        mGradientDrawable.setColor(color);
    }

    public float getRadius() {
        return mRadius;
    }

    public GradientDrawable getGradientDrawable() {
        return mGradientDrawable;
    }
}
