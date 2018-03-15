package com.kotlin.loyal.base

import android.databinding.ViewDataBinding
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Rationale
import com.yanzhenjie.permission.RationaleListener

abstract class BasePreMissActivity<T : ViewDataBinding> : BaseActivity<T>(), RationaleListener {
    fun requestPermissions(reqCode: Int, permissions: Array<String>) {
        AndPermission.with(this)
                .requestCode(reqCode)
                .permission(*permissions)
                .rationale(this)
                .callback(this)
                .start()
    }

    override fun showRequestPermissionRationale(requestCode: Int, rationale: Rationale?) {
        AndPermission.rationaleDialog(this, rationale).show()
    }
}