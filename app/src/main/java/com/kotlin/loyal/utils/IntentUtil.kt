package com.kotlin.loyal.utils

import android.app.Activity
import android.content.Intent

object IntentUtil {
    fun toStartActivity(currentActivity: Activity, nextActivity: Class<*>) {
        toStartActivity(currentActivity, Intent(currentActivity, nextActivity))
    }

    fun toStartActivityForResult(currentActivity: Activity, nextActivity: Class<*>, reqCode: Int) {
        toStartActivityForResult(currentActivity, Intent(currentActivity, nextActivity), reqCode)
    }

    fun toStartActivity(currentActivity: Activity, intent: Intent) {
        intent.putExtras(currentActivity.intent)
        currentActivity.startActivity(intent)
    }

    fun toStartActivityForResult(currentActivity: Activity, intent: Intent, reqCode: Int) {
        intent.putExtras(currentActivity.intent)
        currentActivity.startActivityForResult(intent, reqCode)
    }
}
