package com.synexoit.weatherapplication.remote.entity.darksky


data class DailyRemote(var summary: String?,
                       var icon: String?,
                       var data: List<DailyDataRemote>)