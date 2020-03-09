package com.kotlin.loyal.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.kotlin.loyal.R
import com.kotlin.loyal.base.BaseActivity
import com.kotlin.loyal.impl.Contact
import com.kotlin.loyal.impl.TextChangedListener
import com.kotlin.loyal.utils.FileUtil
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.PermissionNo
import com.yanzhenjie.permission.PermissionYes
import kotlinx.android.synthetic.main.activity_login.*
import java.io.File

class LoginActivity : BaseActivity() {

    override val isTransStatus: Boolean
        get() = false

    override fun actLayoutRes(): Int {
        return R.layout.activity_login
    }

    override fun afterOnCreate() {
         //drawable = ResUtil.getBackground(this)
        //binding?.loginBean = PreferUtil.getLoginBean(this)
        //kotlin 中没有new 关键字
        account.addTextChangedListener(TextChangedListener(account_clear))
        password.addTextChangedListener(TextChangedListener(password_clear))
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
    }

    fun onClick(view: View) {
    when(view.id){

    }
    }

    @PermissionYes(Contact.Int.permission)
    private fun onPerMissSuccess(grantedPermissions: List<String>) {
        val file = File(FileUtil.path_apk, FileUtil.apkFileName)
        if (file.exists())
            FileUtil.deleteFile(file)
        FileUtil.createFiles()
        //UpdateService.startActionUpdate(this, Contact.Str.ACTION_UPDATE, null)
    }

    @PermissionNo(Contact.Int.permission)
    private fun onPerMissFail(deniedPermissions: List<String>) {
        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
            // 第一种：用默认的提示语。
            AndPermission.defaultSettingDialog(this, Contact.Int.permissionMemory).show()
        } else {
            showDialog("您已拒绝授予权限，程序将退出", true)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK)
            return
        when (requestCode) {
            Contact.Int.reqCode_register -> if (data != null) account.setText(replaceNull(data.getStringExtra("account")))
        }
    }
}

