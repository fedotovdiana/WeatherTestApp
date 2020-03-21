package com.itis.group11801.fedotova.weathertest.net

import com.itis.group11801.fedotova.weathertest.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain
            .request()
            .url()
            .newBuilder()
            .addQueryParameter(
                "appid",
                BuildConfig.API_KEY
            )
            .build()

        val newRequest = chain
            .request()
            .newBuilder()
            .url(newUrl)
            .build()
        chain.proceed(newRequest)
    }

    private val unitsInterceptor = Interceptor { chain ->
        val newUrl = chain
            .request()
            .url()
            .newBuilder()
            .addQueryParameter(
                "units",
                BuildConfig.UNITS
            )
            .build()

        val newRequest = chain
            .request()
            .newBuilder()
            .url(newUrl)
            .build()
        chain.proceed(newRequest)
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(authInterceptor)
            .addInterceptor(unitsInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit
            .Builder()
            .client(client)
            .baseUrl(BuildConfig.API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    val weatherService: WeatherService by lazy {
        retrofit.create(
            WeatherService::class.java
        )
    }

}
