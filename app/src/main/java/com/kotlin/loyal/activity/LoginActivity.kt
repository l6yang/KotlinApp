package com.kotlin.loyal.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import com.kotlin.loyal.R
import com.kotlin.loyal.utils.IntentUtil
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.submit -> attemptLogin()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //account.addTextChangedListener(new Text);
        submit.setOnClickListener(this)
    }

    private fun attemptLogin() {
        // Reset errors.
        account!!.error = null
        password!!.error = null

        // Store values at the time of the login attempt.
        val email = account!!.text.toString()
        val password = password!!.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            this.password!!.error = getString(R.string.error_invalid_password)
            focusView = this.password
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            account!!.error = getString(R.string.error_field_required)
            focusView = account
            cancel = true
        }

        if (cancel) {
            focusView!!.requestFocus()
        } else {
            //val intent = Intent(this, MainActivity)
            IntentUtil.toStartActivity(this, MainActivity::class.java)
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 4
    }
}

