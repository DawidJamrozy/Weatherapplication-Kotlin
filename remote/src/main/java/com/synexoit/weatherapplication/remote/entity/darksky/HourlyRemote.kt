package com.synexoit.weatherapplication.remote.entity.darksky

data class HourlyRemote(var summary: String?,
                        var icon: String?,
                        var data: List<HourlyDataRemote>)