package com.ahandy.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

public class CrossFadeAnimation {

    private View mFadeInView;

    private View mFadeOutView;

    private int mDuration;

    private Animator.AnimatorListener mFadeInAnimatorListener;

    private Animator.AnimatorListener mFadeOutAnimatorListener;

    public CrossFadeAnimation(final View fadeInView, final View fadeOutView) {
        this(fadeInView, fadeOutView, AnimateDuration.SHORT);
    }

    public CrossFadeAnimation(final View fadeInView, final View fadeOutView, int duration) {
        this(fadeInView, fadeOutView, duration, null, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                fadeOutView.setVisibility(View.GONE);
            }
        });
    }

    public CrossFadeAnimation(final View fadeInView, final View fadeOutView, int duration,
            Animator.AnimatorListener fadeInAnimatorListener,
            Animator.AnimatorListener fadeOutAnimatorListener) {
        if (fadeInView == null) {
            throw new NullPointerException("fadeInView is null.");
        }
        if (fadeOutView == null) {
            throw new NullPointerException("fadeOutView is null.");
        }
        mFadeInView = fadeInView;
        mFadeOutView = fadeOutView;
        mDuration = duration <= 0 ? AnimateDuration.SHORT : duration;
        mFadeInAnimatorListener = fadeInAnimatorListener;
        mFadeOutAnimatorListener = fadeOutAnimatorListener;
    }

    public void start() {
        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        mFadeInView.setAlpha(0f);
        mFadeInView.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        mFadeInView.animate()
                .alpha(1f)
                .setDuration(mDuration)
                .setListener(mFadeInAnimatorListener);

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        mFadeOutView.animate()
                .alpha(0f)
                .setDuration(mDuration)
                .setListener(mFadeOutAnimatorListener);
    }
}
