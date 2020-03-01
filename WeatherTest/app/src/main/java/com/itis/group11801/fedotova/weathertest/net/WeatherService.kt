package com.itis.group11801.fedotova.weathertest.net

import com.itis.group11801.fedotova.weathertest.net.cities.CitiesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    suspend fun weatherByName(@Query("q") name: String): WeatherResponse

    @GET("weather")
    suspend fun weatherByID(@Query("id") id: Int): WeatherResponse

    @GET("find")
    suspend fun findCitiesInCycle(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("cnt") cnt: Int = 20
    ): CitiesResponse
}