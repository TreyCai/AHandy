package com.ahandy.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * A {@link android.webkit.WebView} that has a callback for scrolling changed.
 */
public class ObservableWebView extends WebView {

    public ObservableWebView(Context context) {
        super(context);
    }

    public ObservableWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScroll(l,t,oldl, oldt);
        }
    }

    private OnScrollChangedListener mOnScrollChangedListener;

    public static interface OnScrollChangedListener{
        public void onScroll(int l, int t, int oldl, int oldt);
    }

    public OnScrollChangedListener getOnScrollChangedListener() {
        return mOnScrollChangedListener;
    }

    public void setOnScrollChangedListener(
            OnScrollChangedListener onScrollChangedListener) {
        mOnScrollChangedListener = onScrollChangedListener;
    }
}
