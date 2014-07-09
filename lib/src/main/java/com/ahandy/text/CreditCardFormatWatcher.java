package com.ahandy.text;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

/**
 * A formatter which formats credit card numbers. It will automatically insert space(by default)
 * after every four digits.
 */
public class CreditCardFormatWatcher implements TextWatcher {

    public char mSeparator;

    public CreditCardFormatWatcher() {
        mSeparator = ' ';
    }

    public CreditCardFormatWatcher(char separator) {
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
        // Remove separator
        if (s.length() > 0 && (s.length() % 5) == 0) {
            final char c = s.charAt(s.length() - 1);
            if (mSeparator == c) {
                s.delete(s.length() - 1, s.length());
            }
        }
        // Insert separator where needed.
        if (s.length() > 0 && (s.length() % 5) == 0) {
            char c = s.charAt(s.length() - 1);
            // Only if its a digit where there should be a separator we insert a separator
            if (Character.isDigit(c)
                    && TextUtils.split(s.toString(), String.valueOf(mSeparator)).length <= 3) {
                s.insert(s.length() - 1, String.valueOf(mSeparator));
            }
        }
    }
}
