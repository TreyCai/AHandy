package com.ahandy.animation;

import android.view.animation.Interpolator;

/**
 * Created on 14-6-24.
 */
public class BetterAccelerateDecelerateInterpolator implements Interpolator {
    public BetterAccelerateDecelerateInterpolator() {
    }

    @Override
    public float getInterpolation(float input) {
        float x = 2 * input;
        if (input < 0.5f) {
            return (float) (0.5f * Math.pow(x, 5));
        }
        x = (input - 0.5f) * 2 - 1;
        return (float) (0.5f * Math.pow(x, 5) + 1);
    }
}
