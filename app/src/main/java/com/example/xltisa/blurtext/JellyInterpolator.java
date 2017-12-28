package com.example.xltisa.blurtext;

import android.animation.TimeInterpolator;

/**
 * Created by Rewnosor Xltisa on 2017/12/27.
 * The custom Interpolator for animation
 */
class JellyInterpolator implements TimeInterpolator {
    private float factor;

    public JellyInterpolator() {
        this.factor = 0.15f;
    }

    @Override
    public float getInterpolation(float input) {
        return (float) (Math.pow(2, -10 * input)
                * Math.sin((input - factor / 4) * (2 * Math.PI) / factor) + 1);
    }
}
