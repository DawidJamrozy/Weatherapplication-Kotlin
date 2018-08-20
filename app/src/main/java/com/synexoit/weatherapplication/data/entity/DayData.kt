package com.synexoit.weatherapplication.data.entity

import android.databinding.BindingAdapter
import android.os.Parcelable
import android.widget.ImageView
import com.synexoit.weatherapplication.GlideApp
import com.synexoit.weatherapplication.R
import com.synexoit.weatherapplication.data.extensions.empty
import com.synexoit.weatherapplication.util.ViewType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DayData(var tempMin: Int,
                   var tempMax: Int,
                   var icon: String,
                   var dayName: String) : ViewType, Parcelable {

    override val viewType: Int
        get() = R.layout.item_day

    override val uniqueId: String
        get() = String.empty()

    companion object {
        private const val RAIN  = "rain"
        private const val FOG  = "fog"
        private const val PARTLY_CLOUDY_DAY  = "partly-cloudy-day"
        private const val PARTLY_CLOUDY_NIGHT  = "partly-cloudy-night"
        private const val SNOW  = "snow"
        private const val SLEET  = "sleet"
        private const val CLEAR_DAY  = "clear-day"
        private const val CLEAR_NIGHT  = "clear-night"
        private const val WIND  = "wind"
        private const val CLOUDY  = "cloudy"

        @BindingAdapter("imageSrc")
        @JvmStatic
        fun loadImage(imageView: ImageView, imageSrc: Int) {
            GlideApp.with(imageView)
                    .load(imageSrc)
                    .into(imageView)
        }
    }

    fun getSummary(): Int {
        return when (icon) {
            RAIN -> R.string.rain
            FOG -> R.string.mist
            PARTLY_CLOUDY_DAY -> R.string.partly_cloudy_day
            PARTLY_CLOUDY_NIGHT -> R.string.partly_cloudy_night
            SNOW -> R.string.snow
            SLEET -> R.string.sleet
            CLEAR_DAY -> R.string.clear
            CLEAR_NIGHT -> R.string.clear
            WIND -> R.string.wind
            CLOUDY -> R.string.cloudy
            else -> 0
        }
    }

    fun getImageSrc(): Int {
        return when (icon) {
            RAIN -> R.drawable.rain
            FOG -> R.drawable.fog
            PARTLY_CLOUDY_DAY -> R.drawable.partly_cloudy_day
            PARTLY_CLOUDY_NIGHT -> R.drawable.partly_cloudy_night
            SNOW -> R.drawable.snow
            SLEET -> R.drawable.sleet
            CLEAR_DAY -> R.drawable.clear_day
            CLEAR_NIGHT -> R.drawable.clear_night
            WIND -> R.drawable.wind
            CLOUDY -> R.drawable.cloudy
            else -> 0
        }
    }
}