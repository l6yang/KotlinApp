package com.kotlin.loyal.base

import android.databinding.ViewDataBinding
import android.os.Bundle

import com.kotlin.loyal.impl.Contact
import com.kotlin.loyal.libs.swipback.app.SwipeBackActivity
import com.kotlin.loyal.libs.swipback.utils.SwipeBackLayout

abstract class BaseSwipeActivity<T : ViewDataBinding> : SwipeBackActivity<T>(), SwipeBackLayout.SwipeListener, Contact {
    protected var LEFT = SwipeBackLayout.EDGE_LEFT
    protected var RIGHT = SwipeBackLayout.EDGE_RIGHT
    protected var BOTTOM = SwipeBackLayout.EDGE_BOTTOM
    protected var NONE = 0
    private var mSwipeBackLayout: SwipeBackLayout? = null
    private var direction = NONE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSwipeBackLayout = swipeBackLayout
        mSwipeBackLayout!!.addSwipeListener(this)
        direction = setEdgePosition()
        setGesturePossible()
    }

    private fun updateEdge(edge: Int) {
        direction = edge
        setGesturePossible()
    }

    private fun setGesturePossible() {
        if (direction == NONE) {
            mSwipeBackLayout!!.setEnableGesture(false)
        } else {
            mSwipeBackLayout!!.setEdgeTrackingEnabled(direction)
            mSwipeBackLayout!!.setEnableGesture(true)
        }
    }

    abstract fun setEdgePosition(): Int

    override fun onScrollStateChange(state: Int, scrollPercent: Float) {

    }

    override fun onEdgeTouch(edgeFlag: Int) {

    }

    override fun onScrollOverThreshold() {

    }
}
