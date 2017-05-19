package com.kotlin.loyal.beans

import android.databinding.BaseObservable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt

import com.kotlin.loyal.utils.ApkUtil
/**
 * 登录页面数据
 */
class LoginBean : BaseObservable {
    var account = ObservableField<String>()
    var password = ObservableField<String>()
    var nickname = ObservableField<String>()
    var icon = ObservableField<String>()
    var sign = ObservableField<String>()
    var device = ObservableField<String>()
    var locked = ObservableInt()
    var serial = ObservableField<String>()
    var server = ObservableField<String>()
    var oldPassword = ObservableField<String>()
    var editable = ObservableBoolean()

    /**
     * [com.kotlin.loyal.handler.LoginHandler]
     * [com.kotlin.loyal.handler.RegisterHandler]
     */
    constructor(account: String, password: String) {
        this.account.set(account)
        this.password.set(password)
        this.device.set(ApkUtil.deviceID)
        this.serial.set(ApkUtil.deviceSerial)
    }

    constructor(account: String, password: String, oldPassword: String) {
        this.account.set(account)
        this.password.set(password)
        this.oldPassword.set(oldPassword)
        this.device.set(ApkUtil.deviceID)
        this.serial.set(ApkUtil.deviceSerial)
    }

    /**
     * [com.kotlin.loyal.handler.RegisterHandler]
     */
    constructor(account: String, password: String, isServer: Boolean, param: String) {
        this.account.set(account)
        this.password.set(password)
        if (isServer)
            this.server.set(param)
        else
            this.nickname.set(param)
        this.device.set(ApkUtil.deviceID)
        this.serial.set(ApkUtil.deviceSerial)
    }

    constructor() {}

    constructor(account: String, password: String, nickname: String, icon: String, sign: String, device: String, locked: Int, serial: String, server: String, editable: Boolean) {
        this.account.set(account)
        this.password.set(password)
        this.nickname.set(nickname)
        this.icon.set(icon)
        this.sign.set(sign)
        this.device.set(device)
        this.locked.set(locked)
        this.serial.set(serial)
        this.server.set(server)
        this.editable.set(editable)
    }

    override fun toString(): String {
        return "{\"account\":" + (if (null == account.get()) null else "\"" + account.get() + "\"") +
                ",\"password\":" + (if (null == password.get()) null else "\"" + password.get() + "\"") +
                ",\"nickname\":" + (if (null == nickname.get()) null else "\"" + nickname.get() + "\"") +
                ",\"icon\":" + (if (null == icon.get()) null else "\"" + icon.get() + "\"") +
                ",\"sign\":" + (if (null == sign.get()) null else "\"" + sign.get() + "\"") +
                ",\"device\":" + (if (null == device.get()) null else "\"" + device.get() + "\"") +
                ",\"locked\":" + locked.get() +
                ",\"serial\":" + (if (null == serial.get()) null else "\"" + serial.get() + "\"") +
                ",\"oldPassword\":" + (if (null == oldPassword.get()) null else "\"" + oldPassword.get() + "\"") +
                "}"
    }
}