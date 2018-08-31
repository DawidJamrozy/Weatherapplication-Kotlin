package com.synexoit.weatherapplication.remote.api

import com.synexoit.weatherapplication.remote.entity.darksky.CityRemote
import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {

    @GET("{lat},{lng}?")
    fun getCity(@Path("lat") lat: String,
                @Path("lng") lng: String,
                @Query("lang") lang: String,
                @Query("exclude") exclude: String,
                @Query("units") units: String): Maybe<CityRemote>
}