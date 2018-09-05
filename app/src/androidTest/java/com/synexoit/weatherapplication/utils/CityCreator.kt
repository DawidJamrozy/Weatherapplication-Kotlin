package com.synexoit.weatherapplication.utils

import com.synexoit.weatherapplication.data.entity.darksky.City
import com.synexoit.weatherapplication.data.entity.darksky.Currently
import com.synexoit.weatherapplication.data.entity.darksky.Daily
import com.synexoit.weatherapplication.data.entity.darksky.Hourly

class CityCreator {
    companion object {
        fun createEmptyCity() = City(currently = Currently(), hourly = Hourly(data = listOf()), daily = Daily(data = listOf()))

    }
}