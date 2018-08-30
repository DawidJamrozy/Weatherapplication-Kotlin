package com.synexoit.weatherapplication.presentation.data.entity

import com.synexoit.weatherapplication.data.entity.darksky.City

data class CityPlace(val name: String,
                     val address: String,
                     val latitude: Double,
                     val longitude: Double,
                     val id: String) {

    companion object {
        fun from(city: City): CityPlace = CityPlace(city.name, city.address, city.latitude,
                city.longitude, city.placeId)
    }
}
