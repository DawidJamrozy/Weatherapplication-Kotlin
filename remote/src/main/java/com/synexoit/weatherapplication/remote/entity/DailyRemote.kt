package com.synexoit.weatherapplication.remote.entity


data class DailyRemote(var summary: String?,
                       var icon: String?,
                       var data: List<DailyDataRemote>? = null)