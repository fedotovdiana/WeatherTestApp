package com.itis.group11801.fedotova.weathertest.net

import com.itis.group11801.fedotova.weathertest.net.cities.CitiesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    fun weatherByName(@Query("q") name: String): Observable<WeatherResponse>

    @GET("weather")
    fun weatherByID(@Query("id") id: Int): Observable<WeatherResponse>

    @GET("find")
    fun findCitiesInCycle(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("cnt") cnt: Int = 20
    ): Observable<CitiesResponse>
}
