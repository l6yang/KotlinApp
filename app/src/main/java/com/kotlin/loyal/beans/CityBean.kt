package com.kotlin.loyal.beans

import java.io.Serializable

class CityBean : Serializable {
    var cityName: String? = null
    var cityCode: String? = null
    var cityLetter: String? = null

    constructor() {}

    constructor(cityName: String, cityCode: String, cityLetter: String) {
        this.cityName = cityName
        this.cityCode = cityCode
        this.cityLetter = cityLetter
    }
}
