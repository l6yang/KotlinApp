package com.kotlin.loyal.beans

class ResultBean<T> {
    private var resultCode: Int = 0
    private var resultMsg: String? = null
    private var resultObj: T? = null

    constructor(resultCode: Int, resultMsg: String?, resultObj: T?) {
        this.resultCode = resultCode
        this.resultMsg = resultMsg
        this.resultObj = resultObj
    }
}