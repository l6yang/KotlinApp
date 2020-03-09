package com.kotlin.loyal.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.kotlin.loyal.R

object StateBarUtil {

    @JvmOverloads fun setTranslucentStatus(activity: Activity, trans: Boolean = false) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            // activity.getWindow().setStatusBarColor(Color.TRANSPARENT);  //直接用这个方法会有兼容性问题
            val window = activity.window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = if (trans) Color.TRANSPARENT else ContextCompat.getColor(activity,R.color.statusBar)//calculateStatusColor(Color.WHITE, (int) alphaValue)
        }
        setStateBarColor(activity, if (trans) Color.TRANSPARENT else ContextCompat.getColor(activity,R.color.statusBar))
    }

    private fun setStateBarColor(activity: Activity, @ColorInt color: Int) {
        // 设置状态栏颜色
        val contentLayout = activity.findViewById(android.R.id.content) as ViewGroup
        setupStatusBarView(activity, contentLayout, color)//Color.parseColor("#FF5677FC"));
        // 设置Activity layout的fitsSystemWindows
        val contentChild = contentLayout.getChildAt(0)
        contentChild.fitsSystemWindows = true
    }

    private fun setupStatusBarView(activity: Activity, contentLayout: ViewGroup, @ColorInt color: Int) {
        val statusBarView = View(activity)
        val lp = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity))
        contentLayout.addView(statusBarView, lp)
        statusBarView.setBackgroundColor(color)
    }

    /**
     * 获得状态栏高度
     */
    fun getStatusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }
}
