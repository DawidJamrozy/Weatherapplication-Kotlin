package com.synexoit.weatherapp.data.api

import com.synexoit.weatherapp.data.model.City
import com.synexoit.weatherapp.data.model.CityPlace
import com.synexoit.weatherapp.data.model.CityLatLng
import com.synexoit.weatherapp.data.model.GeocodeCity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface WeatherApi {


    @GET("{lat},{lng}?")
    fun getCity(@Path("lat") lat: String,
                @Path("lng") lng: String,
                @Query("lang") lang: String,
                @Query("exclude") exclude: String,
                @Query("units") units: String): Observable<City>

    @GET("json?")
    fun getCityName(@Query("input") city: String,
                    @Query("types") types: String,
                    @Query("language") language: String,
                    @Query("key") key: String): Observable<CityPlace>

    @GET("json?")
    fun getCityLatLng(@Query("placeid") placeID: String,
                      @Query("language") language: String,
                      @Query("key") key: String): Observable<CityLatLng>

    @GET("json?")
    fun getCityFromLatLng(@Query("latlng") latLng: String,
                          @Query("location_type") locationType: String,
                          @Query("result_type") resultType: String,
                          @Query("language") lang: String,
                          @Query("key") key: String): Observable<GeocodeCity>


}