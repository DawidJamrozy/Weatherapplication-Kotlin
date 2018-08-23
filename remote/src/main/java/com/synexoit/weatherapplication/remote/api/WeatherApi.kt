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
    //TODO 23.08.2018 by Dawid Jamroży remove unused methods

    @GET()
fun getasd()
/*

    @GET("json?")
    fun getCityName(@Query("input") city: String,
                    @Query("types") types: String,
                    @Query("language") language: String,
                    @Query("key") key: String): Observable<CityPlace>

    @GET("json?")
    fun getCityLatLng(@Query("placeid") placeID: String,
                      @Query("language") language: String,
                      @Query("key") key: String): Observable<CityLatLng>
*/
/*

    @GET("json?")
    fun getCityFromLatLng(@Query("latlng") latLng: String,
                          @Query("location_type") locationType: String,
                          @Query("result_type") resultType: String,
                          @Query("language") lang: String,
                          @Query("key") key: String): Observable<GeocodeCity>
*/


}