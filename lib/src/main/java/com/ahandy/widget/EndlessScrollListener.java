package com.ahandy.widget;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public abstract class EndlessScrollListener implements OnScrollListener {

    // The minimum amount of items to have below your current scroll position before loading more.
    private int mVisibleThreshold = 5;

    // The current offset index of data you have loaded
    private int mCurrentPage = 0;

    // The total number of items in the dataset after the last load
    private int mPreviousTotalItemCount = 0;

    // True if we are still waiting for the last set of data to load.
    private boolean mLoading = true;

    // Sets the starting page index
    private int mStartingPageIndex = 0;

    public EndlessScrollListener() {
    }

    public EndlessScrollListener(int visibleThreshold) {
        mVisibleThreshold = visibleThreshold;
    }

    public EndlessScrollListener(int visibleThreshold, int startPage) {
        mVisibleThreshold = visibleThreshold;
        mStartingPageIndex = startPage;
        mCurrentPage = startPage;
    }

    // This happens many times a second during a scroll, so be wary of the code you
    // place here.
    // We are given a few useful parameters to help us work out if we need to load
    // some more data,
    // but first we check if we are waiting for the previous load to finish.
    @Override
    public void onScroll(
            AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        // If there are no items in the list, assume that initial items are loading
        if (!mLoading && (totalItemCount < mPreviousTotalItemCount)) {
            mCurrentPage = mStartingPageIndex;
            mPreviousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                mLoading = true;
            }
        }

        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current
        // page number and total item count.
        if (mLoading) {
            if (totalItemCount > mPreviousTotalItemCount) {
                mLoading = false;
                mPreviousTotalItemCount = totalItemCount;
                mCurrentPage++;
            }
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the mVisibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the
        // data.
        if (!mLoading
                && (totalItemCount - visibleItemCount) <= (firstVisibleItem + mVisibleThreshold)) {
            onLoadMore(mCurrentPage + 1, totalItemCount);
            mLoading = true;
        }
    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page, int totalItemsCount);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // Don't take any action on changed
    }
}
