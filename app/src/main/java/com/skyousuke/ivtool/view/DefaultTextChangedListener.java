package com.skyousuke.ivtool.view;

import android.text.TextWatcher;

/**
 * @author S.Kyousuke <surasek@gmail.com>
 */
public abstract class DefaultTextChangedListener implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

}
