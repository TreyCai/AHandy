package com.ahandy.widget;

import com.ahandy.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * {@link android.widget.TextView} which forces itself to be laid out according the aspect ratio.
 */
public class AspectRatioTextView extends TextView {

    /**
     * Width / Height
     */
    private float mAspectRatio;

    public AspectRatioTextView(Context context) {
        super(context);
    }

    public AspectRatioTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AspectRatioTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioTextView);
        mAspectRatio = Math.abs(a.getFloat(R.styleable.AspectRatioTextView_aspectRatio, 1));
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // If aspect ratio is 0, then ignore the aspect ratio measurement.
        if (mAspectRatio == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSize == 0 && heightSize == 0) {
            // If there are no constraints on size, let FrameLayout measure
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            // Now use the smallest of the measured dimensions for both dimensions
            final int minSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
            if (getMeasuredWidth() > getMeasuredHeight()) {
                setMeasuredDimension((int) (minSize * mAspectRatio), minSize);
            } else {
                setMeasuredDimension(minSize, (int) (minSize / mAspectRatio));
            }
            return;
        }

        final int size;
        if (widthSize == 0 || heightSize == 0) {
            // If one of the dimensions has no restriction on size, set the dimensions according to
            // the biggest one
            size = Math.max(widthSize, heightSize);
            if (widthSize > heightSize) {
                widthMeasureSpec = measureWidth(false, size);
                heightMeasureSpec = measureHeight(true, size);
            } else {
                widthMeasureSpec = measureWidth(true, size);
                heightMeasureSpec = measureHeight(false, size);
            }
        } else {
            // Both dimensions have restrictions on size, set the dimensions according to the
            // smallest one
            size = Math.min(widthSize, heightSize);
            if (widthSize < heightSize) {
                widthMeasureSpec = measureWidth(false, size);
                heightMeasureSpec = measureHeight(true, size);
            } else {
                widthMeasureSpec = measureWidth(true, size);
                heightMeasureSpec = measureHeight(false, size);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int measureWidth(boolean isWidthDynamic, int size) {
        if (isWidthDynamic) {
            return MeasureSpec.makeMeasureSpec((int) (size * mAspectRatio), MeasureSpec.EXACTLY);
        } else {
            return MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        }
    }

    private int measureHeight(boolean isHeightDynamic, int size) {
        if (isHeightDynamic) {
            return MeasureSpec.makeMeasureSpec((int) (size / mAspectRatio), MeasureSpec.EXACTLY);
        } else {
            return MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        }
    }

    public float getAspectRatio() {
        return mAspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        mAspectRatio = aspectRatio;
    }
}
