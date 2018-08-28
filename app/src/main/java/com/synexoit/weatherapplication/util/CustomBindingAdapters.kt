package com.synexoit.weatherapplication.util

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView
import com.synexoit.weatherapplication.GlideApp
import com.synexoit.weatherapplication.R

class CustomBindingAdapters {

    companion object {
        private const val RAIN = "rain"
        private const val FOG = "fog"
        private const val PARTLY_CLOUDY_DAY = "partly-cloudy-day"
        private const val PARTLY_CLOUDY_NIGHT = "partly-cloudy-night"
        private const val SNOW = "snow"
        private const val SLEET = "sleet"
        private const val CLEAR_DAY = "clear-day"
        private const val CLEAR_NIGHT = "clear-night"
        private const val WIND = "wind"
        private const val CLOUDY = "cloudy"

        @BindingAdapter("icon")
        @JvmStatic
        fun setIcon(imageView: ImageView, icon: String) {
            val drawable = when (icon) {
                RAIN -> R.drawable.ic_rain
                FOG -> R.drawable.ic_fog
                PARTLY_CLOUDY_DAY -> R.drawable.ic_partly_cloudy_day
                PARTLY_CLOUDY_NIGHT -> R.drawable.ic_partly_cloudy_night
                SNOW -> R.drawable.ic_snow
                SLEET -> R.drawable.ic_sleet
                CLEAR_DAY -> R.drawable.ic_clear_day
                CLEAR_NIGHT -> R.drawable.ic_clear_night
                WIND -> R.drawable.ic_wind
                CLOUDY -> R.drawable.ic_cloudy
                else -> 0
            }

            GlideApp.with(imageView)
                    .load(drawable)
                    .into(imageView)
        }

        @BindingAdapter("summary")
        @JvmStatic
        fun setSummary(textView: TextView, icon: String) {
            val resource = when (icon) {
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
            textView.text = textView.resources.getString(resource)
        }

        @BindingAdapter("textRes")
        @JvmStatic
        fun textRes(textView: TextView, textResId: Int) {
            textView.setText(textResId)
        }

        @BindingAdapter("value", "unit")
        @JvmStatic
        fun setFormattedText(textView: TextView, value: Int, unit: Int) {
            val resource = textView.resources
            textView.text = resource.getString(R.string.day_details, value, resource.getString(unit))
        }

        @BindingAdapter("imageSrc")
        @JvmStatic
        fun loadImage(imageView: ImageView, imageSrc: Int) {
            GlideApp.with(imageView)
                    .load(imageSrc)
                    .into(imageView)
        }
    }
}