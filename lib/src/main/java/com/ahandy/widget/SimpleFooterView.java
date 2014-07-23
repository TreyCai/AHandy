package com.ahandy.widget;

import com.ahandy.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * A simple footer view for ListView.
 */
public class SimpleFooterView extends FrameLayout {

    private long mAnimationDuration;

    protected TextView mLoadingText;
    protected ProgressBar mProgress;

    protected State mState = State.IDLE;

    public static enum State {
        IDLE, END, LOADING
    }

    public SimpleFooterView(Context context) {
        super(context);
        init(context);
    }

    public SimpleFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SimpleFooterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_simple_footer_view, null);
        mProgress = (ProgressBar) findViewById(R.id.progress);
        mLoadingText = (TextView) findViewById(R.id.text);
        mAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        setState(State.IDLE);
    }

    public State getState() {
        return mState;
    }

    public void setState(final State state, long delay) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                setState(state);
            }
        }, delay);
    }

    public void setState(State status) {
        if (mState == status) {
            return;
        }
        mState = status;

        setVisibility(View.VISIBLE);

        switch (status) {
        case LOADING:
            mLoadingText.setVisibility(View.GONE);
            mProgress.setVisibility(View.VISIBLE);
            break;
        case END:
            mLoadingText.setVisibility(View.VISIBLE);
            mLoadingText.animate().withLayer().alpha(1).setDuration(mAnimationDuration);
            mProgress.setVisibility(View.GONE);
            break;
        default:
            setVisibility(View.GONE);
            break;
        }
    }
}
