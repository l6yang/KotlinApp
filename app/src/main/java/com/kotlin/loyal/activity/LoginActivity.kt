package com.kotlin.loyal.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.kotlin.loyal.R
import com.kotlin.loyal.base.BasePreMissActivity
import com.kotlin.loyal.databinding.ActivityLoginBinding
import com.kotlin.loyal.handler.LoginHandler
import com.kotlin.loyal.impl.Contact
import com.kotlin.loyal.impl.TextChangedListener
import com.kotlin.loyal.service.UpdateService
import com.kotlin.loyal.utils.FileUtil
import com.kotlin.loyal.utils.PreferUtil
import com.kotlin.loyal.utils.ResUtil
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.PermissionNo
import com.yanzhenjie.permission.PermissionYes
import com.yanzhenjie.permission.RationaleListener
import kotlinx.android.synthetic.main.activity_login.*
import java.io.File

class LoginActivity : BasePreMissActivity<ActivityLoginBinding>(), RationaleListener {
    override val isTransStatus: Boolean
        get() = false

    override val layoutRes: Int
        get() = R.layout.activity_login

    override fun afterOnCreate() {
        binding?.drawable = ResUtil.getBackground(this)
        binding?.loginBean = PreferUtil.getLoginBean(this)
        binding?.click = LoginHandler(this, binding)
        //kotlin 中没有new 关键字
        account.addTextChangedListener(TextChangedListener(account_clear))
        password.addTextChangedListener(TextChangedListener(password_clear))
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        requestPermissions(Contact.Int.permissionReadPhone, Array(0) { Manifest.permission.READ_PHONE_STATE;Manifest.permission.WRITE_EXTERNAL_STORAGE; Manifest.permission.READ_EXTERNAL_STORAGE })
    }

    /*  @PermissionYes(Contact.Int.permissionReadPhone)
      private fun onReadPhoneSuccess(grantedPermissions: List<String>) {
          requestPermissions(Contact.Int.permissionMemory, Array(0) { Manifest.permission.WRITE_EXTERNAL_STORAGE; Manifest.permission.READ_EXTERNAL_STORAGE })
      }

      @PermissionNo(Contact.Int.permissionReadPhone)
      private fun onReadPhoneFail(deniedPermissions: List<String>) {
          if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
              // 第一种：用默认的提示语。
              AndPermission.defaultSettingDialog(this, Contact.Int.permissionReadPhone).show()
          } else {
              showDialog("您已拒绝\"获取设备状态权限\"，程序将退出", true)
          }
      }

      @PermissionYes(Contact.Int.permissionMemory)
      private fun onMemorySuccess(grantedPermissions: List<String>) {
          val file = File(FileUtil.path_apk, FileUtil.apkFileName)
          if (file.exists())
              FileUtil.deleteFile(file)
          FileUtil.createFiles()
          UpdateService.startActionUpdate(this, Contact.Str.ACTION_UPDATE, null)
      }

      @PermissionNo(Contact.Int.permissionMemory)
      private fun onMemoryFailed(deniedPermissions: List<String>) {
          if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
              // 第一种：用默认的提示语。
              AndPermission.defaultSettingDialog(this, Contact.Int.permissionMemory).show()
          } else {
              showDialog("您已拒绝\"存储权限\"，程序将退出", true)
          }
      }*/

    @PermissionYes(Contact.Int.permission)
    private fun onPerMissSuccess(grantedPermissions: List<String>) {
        val file = File(FileUtil.path_apk, FileUtil.apkFileName)
        if (file.exists())
            FileUtil.deleteFile(file)
        FileUtil.createFiles()
        UpdateService.startActionUpdate(this, Contact.Str.ACTION_UPDATE, null)
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

