package com.synexoit.weatherapplication.remote.module

import android.content.Context
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.synexoit.weatherapplication.remote.R
import com.synexoit.weatherapplication.remote.api.GoogleApi
import com.synexoit.weatherapplication.remote.api.WeatherApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
class ApiModule {

    /**
     * Provides JSONObjectMapper instance
     */
    @Provides
    @Singleton
    fun provideJSONObjectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        objectMapper.registerModule(KotlinModule())
        return objectMapper
    }

    /**
     * Provides OkHttpClient instance
     */
    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.readTimeout(20, TimeUnit.SECONDS)
        builder.writeTimeout(20, TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)

        return builder.build()
    }


    /**
     * Provides Retrofit client for darksky API
     */
    @Provides
    @Singleton
    @Named("darksky")
    internal fun provideDarkSkyClient(context: Context, okHttpClient: OkHttpClient, objectMapper: ObjectMapper): Retrofit {
        return Retrofit.Builder()
                .baseUrl(context.getString(R.string.DARKSKY_API_KEY))
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

    /**
     * Provides Retrofit client for google API
     */
    @Provides
    @Singleton
    @Named("google")
    internal fun provideGoogleClient(context: Context, okHttpClient: OkHttpClient, objectMapper: ObjectMapper): Retrofit {
        return Retrofit.Builder()
                .baseUrl(context.getString(R.string.GOOGLE_URL))
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

    /**
     * Provides WeatherApi instance
     */
    @Provides
    @Singleton
    internal fun provideDarkSkyApi(@Named("darksky") retrofit: Retrofit): WeatherApi {
        return retrofit.create<WeatherApi>(WeatherApi::class.java)
    }

    /**
     * Provides GoogleApi instance
     */
    @Provides
    @Singleton
    internal fun provideGoogleApi(@Named("google") retrofit: Retrofit): GoogleApi {
        return retrofit.create<GoogleApi>(GoogleApi::class.java)
    }


    /**
     * Provides GoogleApi key
     */
    @Provides
    @Singleton
    internal fun provideGoogleApiKey(context: Context): String {
        return context.getString(R.string.GOOGLE_API_KEY)
    }
}