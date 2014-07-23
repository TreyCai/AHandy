/*
 * Copyright (c) 2014. Trey Walker <imtreywalker@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.ahandy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

public class SwipeRefreshWebsiteActivity extends Activity
        implements SwipeRefreshLayout.OnRefreshListener {

    /**
     * The key name uses for retrieve url from intent.
     */
    public static final String INTENT_KEY_URL = "intent_key_url";

    private SwipeRefreshLayout mRefreshLayout;
    private WebView mWebView;

    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh_website);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        mWebView = (WebView) findViewById(R.id.web);

        mUrl = getIntent().getStringExtra(INTENT_KEY_URL);
        if (TextUtils.isEmpty(mUrl)) {
            Toast.makeText(this, R.string.toast_url_is_empty, Toast.LENGTH_LONG).show();
            return;
        }

        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mRefreshLayout.setRefreshing(true);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new UpdateRefreshClient());
        mWebView.loadUrl(mUrl);
    }

    @Override
    public void onRefresh() {
        mWebView.loadUrl(mUrl);
    }

    private class UpdateRefreshClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                mRefreshLayout.setRefreshing(false);
            }
        }
    }

    public SwipeRefreshLayout getRefreshLayout() {
        return mRefreshLayout;
    }

    public WebView getWebView() {
        return mWebView;
    }
}
