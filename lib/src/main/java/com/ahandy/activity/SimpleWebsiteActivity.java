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

import com.ahandy.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SimpleWebsiteActivity extends Activity {

    /**
     * The key name uses for retrieve url from intent.
     */
    public static final String INTENT_KEY_URL = "intent_key_url";
    /**
     * The key name uses for retrieve indeterminate data for progress bar from intent.
     */
    public static final String INTENT_KEY_INDETERMINATE = "intent_key_indeterminate";

    private WebView mWebView;
    private ProgressBar mProgressBar;

    private String mUrl;
    private boolean mShowRefresh = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_webview);
        mWebView = (WebView) findViewById(R.id.web);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);

        mUrl = getIntent().getStringExtra(INTENT_KEY_URL);
        if (TextUtils.isEmpty(mUrl)) {
            Toast.makeText(this, R.string.toast_url_is_empty, Toast.LENGTH_LONG).show();
            mShowRefresh = false;
            invalidateOptionsMenu();
            return;
        }

        if (getIntent().getBooleanExtra(INTENT_KEY_INDETERMINATE, false)) {
            mProgressBar.setIndeterminate(true);
        } else {
            mProgressBar.setProgress(0);
            mWebView.setWebChromeClient(new UpdateProgressClient());
        }

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(mUrl);
    }

    private class UpdateProgressClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            mProgressBar.setProgress(newProgress);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.simple_refresh, menu);
        if (!mShowRefresh) {
            menu.findItem(R.id.action_refresh).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            mWebView.loadUrl(mUrl);
        }
        return super.onOptionsItemSelected(item);
    }

    public WebView getWebView() {
        return mWebView;
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    public boolean isRefreshing() {
        return mShowRefresh;
    }
}
