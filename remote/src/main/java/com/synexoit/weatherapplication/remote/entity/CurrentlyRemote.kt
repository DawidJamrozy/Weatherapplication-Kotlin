package com.synexoit.weatherapplication.remote.entity

data class CurrentlyRemote(var time: Long = 0,
                           var summary: String = "",
                           var icon: String = "",
                           var precipIntensity: Double = 0.0,
                           var precipProbability: Double = 0.0,
                           var temperature: Double = 0.0,
                           var apparentTemperature: Double = 0.0,
                           var dewPoint: Double = 0.0,
                           var humidity: Double = 0.0,
                           var windSpeed: Double = 0.0,
                           var windBearing: Double = 0.0,
                           var cloudCover: Double = 0.0,
                           var pressure: Double = 0.0,
                           var ozone: Double = 0.0)