package com.synexoit.weatherapplication.remote.entity

data class HourlyRemote(var summary: String?,
                        var icon: String?,
                        var data: List<HourlyDataRemote>? = null)