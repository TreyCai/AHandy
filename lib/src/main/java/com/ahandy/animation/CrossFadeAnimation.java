package com.ahandy.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

public class CrossFadeAnimation {
    // TODO need refactor

    public CrossFadeAnimation(final View fadeInView, final View fadeOutView) {
        this(fadeInView, fadeOutView, AnimateDuration.SHORT, null,
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        fadeOutView.setVisibility(View.GONE);
                    }
                });
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
        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        fadeInView.setAlpha(0f);
        fadeInView.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        fadeInView.animate()
                .alpha(1f)
                .setDuration(duration)
                .setListener(fadeInAnimatorListener);

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        fadeOutView.animate()
                .alpha(0f)
                .setDuration(duration)
                .setListener(fadeOutAnimatorListener);
    }
}
