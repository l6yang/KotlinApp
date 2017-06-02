package com.kotlin.loyal.impl

interface Contact {
    /*在Kotlin中，属性值由var和val关键字声明，其中，
    var声明的属性值为可变的，
    而val声明的属性值是只读属性，也就是说其值是不可变的。
    声明的属性值必须得初始化,属性必须初始化，否则编译报错.或者将此属性用abstract修饰符修饰。在abstract修饰的属性值，即使不用初始化，必须声明其数据类型，并在其子类初始化。
abstract class Person {
    abstract var name: String
    var age: Int = 10
    val sex: String = "M"
    private var mobile: String = "606066"
}
默认为被public修饰符修饰，也就意味着其他类可以通过其类任意调用。如果禁止其他类访问其，本身可以使用private修饰符修饰。
    */
    object Str {
        const val appId = "9319579"
        const val appKey = "QAZQ5Gh8H6nAG2GOuPM0THDZ"
        const val secretKey = "c4f7e878acdb1314a1b5fe3ae502d645"
        const val TIME_ALL = "yyyy-MM-dd HH:mm:ss"
        const val TIME_WEEK = "yyyy-MM-dd EEEE"
        const val TIME_YEAR_MONTH_DAY = "yyyy-MM-dd"
        const val HOURS_MIN = "HH:mm"
        const val MONTH_DAY_HOUR_MIN = "MM-dd HH:mm"
        const val YEAR_MONTH = "yyyy-MM"
        const val KEY_IP = "ip"
        const val KEY_ACCOUNT = "account"
        const val KEY_CITY = "city"
        const val KEY_WEATHER = "weather"
        const val KEY_PASSWORD = "password"
        const val KEY_SERVER = "server"
        const val service_action_loc = "com.mwm.loyal.service.action.loc"
        const val ACTION_UPDATE = "com.mwm.loyal.activity.action.update"
        const val ACTION_DOWN = "com.mwm.loyal.activity.action.downLoad"
        const val method_register = "doRegister"
        const val method_login = "doLogin"
        const val method_queryAccount = "doQueryAccount"
        const val method_showIcon = "doShowIconByIO"
        const val method_update = "doUpdateAccount"
        const val method_account_locked = "doAccountLocked"
        const val method_update_icon = "doUpdateIcon"
        const val method_feedBack = "doFeedBack"
        const val method_getSelfFeed = "doGetSelfFeed"
        const val method_deleteSelfFeed = "deleteSelfFeed"
        const val method_ucrop_test = "doUCropTest"
        const val method_scan = "doScan"
        const val method_apkVerCheck = "doCheckApkVer"
        const val method_destroyAccount = "destroyAccount"
        const val KAY_ENCRYPT_DECODE = "com.mwm.forLoyal"
        const val ipAdd = "192.168.1.15"
        const val port = ":8080"
        private const val http = "http://"
        const val https = "https://"
        const val action = "action.do?method="
        const val defaultCity = "西安"
        const val defaultWeather = "0"
        const val share = "share"

        fun getServerUrl(method: String): String {
            return baseUrl + action + method
        }

        const val baseUrl: String
                = http + ipAdd + port + "/mwm/"

        fun getIconUrl(account: String): String {
            return baseUrl + action + method_showIcon + "&account=$account"
        }
    }

    object Int {
        const val reqCode_Main_noRequest = 100
        const val reqCode_Main_Setting = 102
        const val reqCode_Main_Zing = 103
        const val reqCode_Main_UCrop = 104
        const val reqCode_Main_Preview = 105
        const val reqCode_Main_icon = 106
        const val reqCode_Main_weather = 107
        const val reqCode_Weather_city = 108
        const val reqCode_Settings_account = 109
        const val reqCode_UpdateMM = 110
        const val reqCode_register = 111
        const val reqCode_destroy = 112
        const val permissionMemory = 600
        const val permissionCamera = 601
        const val permissionReadPhone = 602
        const val permissionLocation = 603
        const val rx2Weather = 706
        //异步
        const val async2Null = -102
        const val delayed2Activity = -103
    }
}
