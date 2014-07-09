package com.ahandy.util;

import android.graphics.Color;

public class ColorUtils {

    public static int transparentColor(int alpha, int color) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return Color.argb(alpha, r, g, b);
    }
}
