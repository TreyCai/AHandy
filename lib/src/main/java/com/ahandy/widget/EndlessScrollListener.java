package com.ahandy.widget;

import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public abstract class EndlessScrollListener implements OnScrollListener {

    private static final String LOGTAG = "EndlessScrollListener";

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int mVisibleThreshold = 0;

    private int mCurrentPage = 0;
    private int mMaxPage = 0;

    public EndlessScrollListener() {
    }

    public EndlessScrollListener(int visibleThreshold) {
        mVisibleThreshold = visibleThreshold;
    }

    public EndlessScrollListener(int visibleThreshold, int startPage) {
        mVisibleThreshold = visibleThreshold;
        mCurrentPage = startPage;
    }

    public EndlessScrollListener(int visibleThreshold, int startPage, int maxPage) {
        mVisibleThreshold = visibleThreshold;
        mCurrentPage = startPage;
        mMaxPage = maxPage;
    }

    public void setMaxPage(int maxPage) {
        mMaxPage = maxPage;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {
    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            if (view.getLastVisiblePosition() >= view.getCount() - 1 - mVisibleThreshold) {
                mCurrentPage++;
                // load more list items
                if (mCurrentPage <= mMaxPage) {
                    onLoadMore(mCurrentPage);
                } else {
                    Log.d(LOGTAG, "Reach the max page: " + mMaxPage + ".");
                }
            }
        }
    }
}
