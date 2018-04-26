package com.synexoit.weatherapp.data.model.darksky

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.util.ViewType
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "city")
data class City(val name: String,
                val placeId: String,
                val addressDescription: String,
                val refreshDate: String,
                val sortPosition: Int,
                val latitude: Double,
                val longitude: Double,
                val timezone: String,
                val currently: Currently,
                val hourly: Hourly,
                val daily: Daily,
                @PrimaryKey(autoGenerate = true)
                var roomId: Long = 0) : ViewType, Parcelable {



    override fun getViewType(): Int = R.layout.item_city
}
