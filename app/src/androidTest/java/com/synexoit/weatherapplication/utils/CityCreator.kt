package com.synexoit.weatherapplication.utils

import com.synexoit.weatherapplication.data.entity.darksky.City
import com.synexoit.weatherapplication.data.entity.darksky.Currently
import com.synexoit.weatherapplication.data.entity.darksky.Daily
import com.synexoit.weatherapplication.data.entity.darksky.Hourly

class CityCreator {
    companion object {
        fun createEmptyCity() = City(currently = Currently(), hourly = Hourly(data = listOf()), daily = Daily(data = listOf()))

     /*   fun createCity() = City(0, DataFactory.randomUuid(), "Kraków", " Kraków, Polska",
                "", 0, 50.0783829, 19.8794657, "Europe/Warsaw",)*/

    }
}