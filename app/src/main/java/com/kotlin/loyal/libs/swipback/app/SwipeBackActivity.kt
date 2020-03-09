package com.kotlin.loyal.libs.swipback.app

import android.os.Bundle
import com.kotlin.loyal.base.BaseActivity
import com.kotlin.loyal.libs.swipback.utils.SwipeBackLayout
import com.kotlin.loyal.libs.swipback.utils.SwipeBackUtil

abstract class SwipeBackActivity : BaseActivity(), SwipeBackActivityBase {
    private var mHelper: SwipeBackActivityHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHelper = SwipeBackActivityHelper(this)
        mHelper!!.onActivityCreate()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mHelper!!.onPostCreate()
    }

    override fun getSwipeBackLayout(): SwipeBackLayout {
        return mHelper!!.swipeBackLayout
    }

    override fun setSwipeBackEnable(enable: Boolean) {
        swipeBackLayout.setEnableGesture(enable)
    }

    override fun scrollToFinishActivity() {
        SwipeBackUtil.convertActivityToTranslucent(this)
        swipeBackLayout.scrollToFinishActivity()
    }
}