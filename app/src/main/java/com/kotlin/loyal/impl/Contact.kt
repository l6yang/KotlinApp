package com.kotlin.loyal.impl

interface Contact {

    object Str {
        val appId = "9319579"
        val appKey = "QAZQ5Gh8H6nAG2GOuPM0THDZ"
        val secretKey = "c4f7e878acdb1314a1b5fe3ae502d645"
        val TIME_ALL = "yyyy-MM-dd HH:mm:ss"
        val TIME_WEEK = "yyyy-MM-dd EEEE"
        val TIME_YEAR_MONTH_DAY = "yyyy-MM-dd"
        val HOURS_MIN = "HH:mm"
        val MONTH_DAY_HOUR_MIN = "MM-dd HH:mm"
        val YEAR_MONTH = "yyyy-MM"
        val KEY_IP = "ip"
        val KEY_ACCOUNT = "account"
        val KEY_CITY = "city"
        val KEY_WEATHER = "weather"
        val KEY_PASSWORD = "password"
        val KEY_SERVER = "server"
        val service_action_loc = "com.mwm.loyal.service.action.loc"
        val ACTION_UPDATE = "com.mwm.loyal.activity.action.update"
        val ACTION_DOWN = "com.mwm.loyal.activity.action.downLoad"
        val method_register = "doRegister"
        val method_login = "doLogin"
        val method_queryAccount = "doQueryAccount"
        val method_showIcon = "doShowIconByIO"
        val method_update = "doUpdateAccount"
        val method_account_locked = "doAccountLocked"
        val method_update_icon = "doUpdateIcon"
        val method_feedBack = "doFeedBack"
        val method_getSelfFeed = "doGetSelfFeed"
        val method_deleteSelfFeed = "deleteSelfFeed"
        val method_ucrop_test = "doUCropTest"
        val method_scan = "doScan"
        val method_apkVerCheck = "doCheckApkVer"
        val method_destroyAccount = "destroyAccount"
        val KAY_ENCRYPT_DECODE = "com.mwm.forLoyal"
        val ipAdd = "192.168.31.96"
        val port = ":8080"
        private val http = "http://"
        val https = "https://"
        val action: String = "action.do?method="
        val defaultCity = "西安"
        val defaultWeather = "0"
        val share = "share"

        fun getServerUrl(method: String): String {
            return baseUrl + action + method
        }

        fun getAction(method: String): String {
            return action
        }

        val baseUrl: String
            get() = http + ipAdd + port + "/mwm/"
    }

    object Int {
        val reqCode_Main_noRequest = 100
        val reqCode_Main_Setting = 102
        val reqCode_Main_Zing = 103
        val reqCode_Main_UCrop = 104
        val reqCode_Main_Preview = 105
        val reqCode_Main_icon = 106
        val reqCode_Main_weather = 107
        val reqCode_Weather_city = 108
        val reqCode_Settings_account = 109
        val reqCode_UpdateMM = 110
        val reqCode_register = 111
        val reqCode_destroy = 112
        val permissionMemory = 600
        val permissionCamera = 601
        val permissionReadPhone = 602
        val permissionLocation = 603
        val rx2Weather = 706
        //异步
        val async2Null = -102
        val delayed2Activity = -103
    }
}
