package com.kotlin.loyal.activity

import android.text.TextUtils
import android.view.View
import com.kotlin.loyal.R
import com.kotlin.loyal.base.BaseSwipeActivity
import com.kotlin.loyal.beans.LoginBean
import com.kotlin.loyal.databinding.ActivityRegisterBinding
import com.kotlin.loyal.handler.RegisterHandler
import com.kotlin.loyal.impl.TextChangedListener
import com.kotlin.loyal.utils.ResUtil
import kotlinx.android.synthetic.main.pub_title.*

class RegisterActivity : BaseSwipeActivity<ActivityRegisterBinding>(), View.OnClickListener {
    override val layoutRes: Int
        get() = R.layout.activity_register

    override val isTransStatus: Boolean
        get() = false

    override fun afterOnCreate() {
        val loginBean = LoginBean()
        val account = intent.getStringExtra("account")
        binding!!.loginBean = loginBean
        binding!!.drawable = ResUtil.getBackground(this)
        binding!!.click = RegisterHandler(this, binding)
        loginBean.account.set(account)
        loginBean.editable.set(TextUtils.isEmpty(account))
        val fromLogin = TextUtils.isEmpty(account)
        val extra: String? = intent.getStringExtra("extra")
        initViews(fromLogin, extra)
    }

    private fun initViews(fromLogin: Boolean, extra: String?) {
        pub_back!!.setOnClickListener(this)
        pub_menu!!.visibility = View.GONE
        if (TextUtils.isEmpty(extra)) {
            pub_title!!.text = if (fromLogin) "快速注册" else "修改密码"
            binding!!.accountClear.setImageResource(if (fromLogin) R.mipmap.edit_clear else R.color.white)
            binding!!.accountClear.isEnabled = true
        } else {
            pub_title!!.setText(R.string.destroy_account)
            binding!!.accountClear.setImageResource(R.color.white)
            binding!!.accountClear.isEnabled = false
            binding!!.password.visibility = View.GONE
            binding!!.repeatMm.visibility = View.GONE
        }
        binding!!.account.addTextChangedListener(TextChangedListener(binding!!.accountClear))
        binding!!.nickname.addTextChangedListener(TextChangedListener(binding!!.nicknameClear))
        binding!!.password.addTextChangedListener(TextChangedListener(binding!!.passwordClear))
        binding!!.repeatMm.addTextChangedListener(TextChangedListener(binding!!.repeatClear))
    }

    override fun setEdgePosition(): Int {
        return LEFT
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.pub_back -> {
                System.gc()
                finish()
            }
        }
    }
}
