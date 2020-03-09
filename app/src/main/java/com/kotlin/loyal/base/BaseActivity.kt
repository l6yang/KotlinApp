package com.kotlin.loyal.base

import android.os.Bundle
import butterknife.ButterKnife
import com.kotlin.loyal.impl.Contact
import com.kotlin.loyal.utils.StateBarUtil
import com.loyal.base.ui.activity.ABasicPerMissionActivity

abstract class BaseActivity : ABasicPerMissionActivity(), Contact {
    abstract val isTransStatus: Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(actLayoutRes())
        StateBarUtil.setTranslucentStatus(this, isTransStatus)//沉浸式状态栏
        //ButterKnife.bind(this)
        afterOnCreate()
    }

    override fun bindViews() {
        ButterKnife.bind(this);
    }
}
