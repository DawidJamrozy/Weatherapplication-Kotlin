package com.synexoit.weatherapplication.data.entity.darksky


data class City(var id: Long = 0,
                var placeId: String = "",
                var name: String = "",
                var address: String = "",
                var refreshDate: String = "",
                var sortPosition: Int = 0,
                var latitude: Double = 0.0,
                var longitude: Double = 0.0,
                var timezone: String = "",
                var currently: Currently,
                var hourly: Hourly,
                var daily: Daily)  {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is City) return false

        if (placeId != other.placeId) return false

        return true
    }

    override fun hashCode(): Int {
        return placeId.hashCode()
    }

}
