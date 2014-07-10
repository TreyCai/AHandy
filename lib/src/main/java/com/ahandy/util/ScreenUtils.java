package com.ahandy.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

/**
 * An utility class to get screen size and density.
 */
public class ScreenUtils {

    /**
     * @param context Context to get system service and window manager
     * @return int[0] is width, int[1] is height, all in pixels
     */
    public static int[] getScreenSizeInPx(Context context) {
        if (context == null) {
            throw new NullPointerException("The context cannot be null.");
        }

        int[] screenSize = new int[2];
        Display display = getDisplay(context);
        Point size = new Point();
        display.getSize(size);
        screenSize[0] = size.x; // width
        screenSize[1] = size.y; // height
        return screenSize;
    }

    /**
     * @param context Context to get system service and window manager
     * @return int[0] is width, int[1] is height, all in dip
     */
    public static int[] getScreenSizeInDp(Context context) {
        if (context == null) {
            throw new NullPointerException("The context cannot be null.");
        }

        int[] screenSize = getScreenSizeInPx(context);
        for (int i = 0; i < screenSize.length; i++) {
            screenSize[i] = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, screenSize[i], getDisplayMetrics(context));
        }
        return screenSize;
    }

    /**
     * @param context Context to get system service and window manager
     * @return screen width in px
     */
    public static int getScreenWidthInPx(Context context) {
        return getScreenSizeInPx(context)[0];
    }

    /**
     * @param context Context to get system service and window manager
     * @return screen height in px
     */
    public static int getScreenHeightInPx(Context context) {
        return getScreenSizeInPx(context)[1];
    }

    /**
     * @param context Context to get system service and window manager
     * @return screen width in dp
     */
    public static int getScreenWidthInDp(Context context) {
        return getScreenSizeInDp(context)[0];
    }

    /**
     * @param context Context to get system service and window manager
     * @return screen height in dp
     */
    public static int getScreenHeightInDp(Context context) {
        return getScreenSizeInDp(context)[1];
    }

    /**
     * @param context Context to get display metrics
     * @return true if density is high
     */
    public static boolean isHighDensity(Context context) {
        if (context == null) {
            throw new NullPointerException("The context cannot be null.");
        }
        return getDensityDpi(context) == DisplayMetrics.DENSITY_HIGH;
    }

    /**
     * @param context Context to get display metrics
     * @return true if density is low
     */
    public static boolean isLowDensity(Context context) {
        if (context == null) {
            throw new NullPointerException("The context cannot be null.");
        }
        return getDensityDpi(context) == DisplayMetrics.DENSITY_LOW;
    }

    /**
     * @param context Context to get display metrics
     * @return true if density is medium
     */
    public static boolean isMediumDensity(Context context) {
        if (context == null) {
            throw new NullPointerException("The context cannot be null.");
        }
        return getDensityDpi(context) == DisplayMetrics.DENSITY_MEDIUM;
    }

    /**
     * @param context Context to get display metrics
     * @return true if density is low
     */
    public static boolean isTVDensity(Context context) {
        if (context == null) {
            throw new NullPointerException("The context cannot be null.");
        }
        return getDensityDpi(context) == DisplayMetrics.DENSITY_TV;
    }

    /**
     * @param context Context to get display metrics
     * @return true if density is x-high
     */
    public static boolean isXHighDensity(Context context) {
        if (context == null) {
            throw new NullPointerException("The context cannot be null.");
        }
        return getDensityDpi(context) == DisplayMetrics.DENSITY_XHIGH;
    }

    /**
     * @param context Context to get display metrics
     * @return true if density is xx-high
     */
    @TargetApi(16)
    public static boolean isXXHighDensity(Context context) {
        if (context == null) {
            throw new NullPointerException("The context cannot be null.");
        }
        return getDensityDpi(context) == DisplayMetrics.DENSITY_XXHIGH;
    }

    /**
     * @param context Context to get display metrics
     * @return true if density is xxx-high
     */
    @TargetApi(18)
    public static boolean isXXXHighDensity(Context context) {
        if (context == null) {
            throw new NullPointerException("The context cannot be null.");
        }
        return getDensityDpi(context) == DisplayMetrics.DENSITY_XXXHIGH;
    }

    private static int getDensityDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    private static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    private static Display getDisplay(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay();
    }

    private static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }
}
