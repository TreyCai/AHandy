package com.ahandy.text;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

/**
 * A formatter which formats MAC address.
 */
public class MACAddressFormatWatcher implements TextWatcher {

    public char mSeparator;

    public MACAddressFormatWatcher() {
        mSeparator = ':';
    }

    public MACAddressFormatWatcher(char separator) {
        mSeparator = separator;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        // Invalid input
        if (s.length() > 17) {
            s.delete(s.length() - 1, s.length());
        }

        // Remove separator
        if (s.length() > 0 && (s.length() % 3) == 0) {
            final char c = s.charAt(s.length() - 1);
            if (mSeparator == c) {
                s.delete(s.length() - 1, s.length());
            }
        }

        // Insert separator where needed.
        if (s.length() > 0 && (s.length() % 3) == 0) {
            // Only if its a digit where there should be a separator we insert a separator
            if (TextUtils.split(s.toString(), String.valueOf(mSeparator)).length <= 6) {
                s.insert(s.length() - 1, String.valueOf(mSeparator));
            }
        }
    }
}
