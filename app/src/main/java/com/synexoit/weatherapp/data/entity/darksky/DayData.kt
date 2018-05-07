package com.synexoit.weatherapp.data.entity.darksky

import android.os.Parcelable
import com.synexoit.weatherapp.R
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*


@Parcelize
data class DayData(var tempMin: Int,
                   var tempMax: Int,
                   var icon: String,
                   var time: Int,
                   var simpleDayDateFormat: SimpleDateFormat = SimpleDateFormat(DAY_FORMAT)) : Parcelable {

    companion object {
        private val DAY_FORMAT = "EEEE"
    }

    fun getSummary(): Int {
       return when (icon) {
            "rain" ->  R.string.rain
            "fog" ->  R.string.mist
            "partly-cloudy-day" ->  R.string.partly_cloudy_day
            "partly-cloudy-night" ->  R.string.partly_cloudy_night
            "snow" ->  R.string.snow
            "sleet" ->  R.string.sleet
            "clear-day" ->  R.string.clear
            "clear-night" ->  R.string.clear
            "wind" ->  R.string.wind
            "cloudy" -> R.string.cloudy
            else ->  0
        }
    }

    fun getImageSrc(): Int {
        return when (icon) {
            "rain" -> R.drawable.rain
            "fog" -> R.drawable.fog
            "partly-cloudy-day" -> R.drawable.partly_cloudy_day
            "partly-cloudy-night" -> R.drawable.partly_cloudy_night
            "snow" -> R.drawable.snow
            "sleet" -> R.drawable.sleet
            "clear-day" -> R.drawable.clear_day
            "clear-night" -> R.drawable.clear_night
            "wind" -> R.drawable.wind
            "cloudy" -> R.drawable.cloudy
            else -> 0
        }
    }

    fun getDayName(): String {
        val dayName = simpleDayDateFormat.format(Date(time * 1000L))
        return dayName.substring(0, 1).toUpperCase() + dayName.substring(1)
    }
}