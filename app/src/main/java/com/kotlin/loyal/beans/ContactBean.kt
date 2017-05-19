package com.kotlin.loyal.beans

import android.databinding.ObservableField

class ContactBean {
    private val account = ObservableField<String>()
    private val contact = ObservableField<String>()
    private val time = ObservableField<String>()

    constructor(account: String, contact: String, time: String) {
        this.account.set(account)
        this.contact.set(contact)
        this.time.set(time)
    }

    constructor() {}

    override fun toString(): String {
        return "{\"account\":" + (if (false) null else "\"" + account.get() + "\"") +
                ",\"contact\":" + (if (false) null else "\"" + contact.get() + "\"") +
                ",\"time\":" + (if (false) null else "\"" + time.get() + "\"") +
                "}"
    }
}
