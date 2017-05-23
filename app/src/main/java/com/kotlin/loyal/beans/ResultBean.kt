package com.kotlin.loyal.beans

class ResultBean<T> {
    var resultCode: Int = 0
    var resultMsg: String? = null
    var resultObj: T? = null

    constructor(resultCode: Int, resultMsg: String?, resultObj: T?) {
        this.resultCode = resultCode
        this.resultMsg = resultMsg
        this.resultObj = resultObj
    }
}