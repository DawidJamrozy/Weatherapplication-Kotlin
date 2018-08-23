package com.synexoit.weatherapplication.remote.api

import com.synexoit.weatherapplication.remote.entity.google.GeocodeCity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleApi {

    @GET("json?")
    fun getCityDetails(@Query("latlng") latlng: String,
                       @Query("location_type") locationType: String,
                       @Query("result_type") resultType: String,
                       @Query("language") lang: String,
                       @Query("key") key: String): Single<GeocodeCity>
}