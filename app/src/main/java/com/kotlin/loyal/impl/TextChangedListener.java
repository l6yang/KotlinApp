package com.kotlin.loyal.impl;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

public class TextChangedListener implements TextWatcher {
    private final ImageView imageView;

    public TextChangedListener(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable != null) {
            imageView.setVisibility(editable.length() > 0 ? View.VISIBLE : View.GONE);
        }
    }
}