package com.kotlin.loyal.base

import android.os.Bundle
import com.kotlin.loyal.impl.Contact
import com.kotlin.loyal.libs.swipback.app.SwipeBackActivity
import com.kotlin.loyal.libs.swipback.utils.SwipeBackLayout

abstract class BaseSwipeActivity : SwipeBackActivity(), SwipeBackLayout.SwipeListener, Contact {
    protected val LEFT = SwipeBackLayout.EDGE_LEFT
    protected val RIGHT = SwipeBackLayout.EDGE_RIGHT
    protected val BOTTOM = SwipeBackLayout.EDGE_BOTTOM
    protected val NONE = 0
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
