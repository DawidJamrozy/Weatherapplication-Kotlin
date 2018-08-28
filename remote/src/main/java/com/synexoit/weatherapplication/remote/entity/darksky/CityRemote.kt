package com.synexoit.weatherapplication.remote.entity.darksky

import com.fasterxml.jackson.annotation.JsonProperty


data class CityRemote(var placeId: String = "",
                      var name: String = "",
                      var address: String = "",
                      var addressDescription: String = "",
                      var refreshDate: String = "",
                      var sortPosition: Int = 0,
                      var latitude: Double = 0.0,
                      var longitude: Double = 0.0,
                      var timezone: String = "",
                      @JsonProperty("currently")
                      var currentlyCache: CurrentlyRemote,
                      @JsonProperty("hourly")
                      var hourlyCache: HourlyRemote,
                      @JsonProperty("daily")
                      var dailyCache: DailyRemote)