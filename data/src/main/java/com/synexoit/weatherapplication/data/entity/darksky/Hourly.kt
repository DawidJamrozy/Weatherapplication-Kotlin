package com.synexoit.weatherapplication.data.entity.darksky

data class Hourly(var id: Long = 0,
                  var summary: String?,
                  var icon: String?,
                  var data: List<HourlyData>? = null,
                  var cityId: Long = 0)