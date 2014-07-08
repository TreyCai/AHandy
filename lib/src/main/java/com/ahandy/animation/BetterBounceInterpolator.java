package com.ahandy.animation;

import android.view.animation.Interpolator;

/**
 * Created on 14-6-24.
 */
public class BetterBounceInterpolator implements Interpolator {
    @Override
    public float getInterpolation(float input) {
        return -(float) Math.abs(Math.sin(Math.PI * (input + 1) * (input + 1)) * (1 - input));
    }
}
