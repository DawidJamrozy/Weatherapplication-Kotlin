package com.synexoit.weatherapplication.remote.entity


data class CityRemote(var placeId: String = "",
                      var name: String = "",
                      var address: String = "",
                      var addressDescription: String = "",
                      var refreshDate: String = "",
                      var sortPosition: Int = 0,
                      var latitude: Double = 0.0,
                      var longitude: Double = 0.0,
                      var timezone: String = "",
                      var currentlyCache: CurrentlyRemote?,
                      var hourlyCache: HourlyRemote?,
                      var dailyCache: DailyRemote?)