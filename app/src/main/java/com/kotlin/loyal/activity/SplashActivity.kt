package com.kotlin.loyal.activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.kotlin.loyal.R
import com.kotlin.loyal.base.BaseActivity
import com.kotlin.loyal.utils.IntentUtil
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity(), View.OnClickListener {
    override fun actLayoutRes(): Int {
        return R.layout.activity_splash;
    }

    override val isTransStatus: Boolean
        get() = false
    private val runnable = SplashRunnable(3)

    override fun afterOnCreate() {
        //binding?.drawable = ResUtil.getBackground(this)
        pub_id.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        pub_id!!.removeCallbacks(runnable)
        IntentUtil.toStartActivity(this, LoginActivity::class.java)
        finish()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        pub_id!!.visibility = View.VISIBLE
        pub_id!!.text = String.format(getString(R.string.skip), 3)
        pub_id!!.postDelayed(runnable, 1000)
    }

    private inner class SplashRunnable internal constructor(what: Int) : Runnable {
        private var mWhat = 3

        init {
            this.mWhat = what
        }

        override fun run() {
            if (mWhat == 0) {
                onClick(pub_id)
                return
            }
            pub_id!!.text = String.format(getString(R.string.skip), --mWhat)
            pub_id!!.postDelayed(this, 1000)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean =
            keyCode != KeyEvent.KEYCODE_BACK && super.onKeyDown(keyCode, event)

    override fun onDestroy() {
        super.onDestroy()
        pub_id!!.removeCallbacks(runnable)
    }
}
