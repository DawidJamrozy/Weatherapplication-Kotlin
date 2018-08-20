package com.synexoit.weatherapplication.remote.module

import android.content.Context
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.synexoit.weatherapplication.remote.R
import com.synexoit.weatherapplication.remote.api.WeatherApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class ApiModule {

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
     * Provides OkHttpClient
     */
    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.readTimeout(45, TimeUnit.SECONDS)
        builder.writeTimeout(45, TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)

        return builder.build()
    }

    @Provides
    @Singleton
    internal fun provideDarkSkyClient(context: Context, okHttpClient: OkHttpClient, objectMapper: ObjectMapper): Retrofit {
        //TODO 30.07.2018 Dawid Jamroży save key to config
        return Retrofit.Builder()
                .baseUrl(context.getString(R.string.API_KEY))
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    internal fun provideDarkSkyApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create<WeatherApi>(WeatherApi::class.java)
    }

}