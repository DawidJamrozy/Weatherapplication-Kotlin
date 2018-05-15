package com.synexoit.weatherapp.data.entity.darksky

import android.databinding.BindingAdapter
import android.os.Parcelable
import android.widget.ImageView
import com.synexoit.weatherapp.GlideApp
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.util.ViewType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DayData(var tempMin: Int,
                   var tempMax: Int,
                   var icon: String,
                   var dayName: String) : ViewType, Parcelable {

    override fun getViewType(): Int = R.layout.item_day

    override fun getUniqueId(): String = ""

    companion object {
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
            "rain" -> R.string.rain
            "fog" -> R.string.mist
            "partly-cloudy-day" -> R.string.partly_cloudy_day
            "partly-cloudy-night" -> R.string.partly_cloudy_night
            "snow" -> R.string.snow
            "sleet" -> R.string.sleet
            "clear-day" -> R.string.clear
            "clear-night" -> R.string.clear
            "wind" -> R.string.wind
            "cloudy" -> R.string.cloudy
            else -> 0
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
}