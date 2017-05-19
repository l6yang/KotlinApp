package com.kotlin.loyal.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.mwm.loyal.libs.swipback.app.SwipeBackActivity;
import com.mwm.loyal.libs.swipback.utils.SwipeBackLayout;
import com.mwm.loyal.imp.Contact;

public abstract class BaseSwipeActivity<T extends ViewDataBinding> extends SwipeBackActivity<T> implements SwipeBackLayout.SwipeListener, Contact {
    protected int LEFT = SwipeBackLayout.EDGE_LEFT;
    protected int RIGHT = SwipeBackLayout.EDGE_RIGHT;
    protected int BOTTOM = SwipeBackLayout.EDGE_BOTTOM;
    protected int NONE = 0;
    private SwipeBackLayout mSwipeBackLayout;
    private int direction = NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.addSwipeListener(this);
        direction = setEdgePosition();
        setGesturePossible();
    }

    private void updateEdge(int edge) {
        direction = edge;
        setGesturePossible();
    }

    private void setGesturePossible() {
        if (direction == NONE) {
            mSwipeBackLayout.setEnableGesture(false);
        } else {
            mSwipeBackLayout.setEdgeTrackingEnabled(direction);
            mSwipeBackLayout.setEnableGesture(true);
        }
    }

    public abstract int setEdgePosition();

    @Override
    public void onScrollStateChange(int state, float scrollPercent) {

    }

    @Override
    public void onEdgeTouch(int edgeFlag) {

    }

    @Override
    public void onScrollOverThreshold() {

    }
}
