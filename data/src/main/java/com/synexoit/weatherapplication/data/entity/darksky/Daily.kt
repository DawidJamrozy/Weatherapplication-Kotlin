package com.synexoit.weatherapplication.data.entity.darksky


data class Daily(var id: Long = 0,
                 var summary: String? = "",
                 var icon: String? = "",
                 var data: List<DailyData>,
                 var cityId: Long = 0)