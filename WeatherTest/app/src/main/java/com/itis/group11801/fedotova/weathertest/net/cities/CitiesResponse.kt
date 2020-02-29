package com.itis.group11801.fedotova.weathertest.net.cities


import com.google.gson.annotations.SerializedName

data class CitiesResponse(
    @SerializedName("message")
    var message: String,
    @SerializedName("cod")
    var cod: String,
    @SerializedName("count")
    var count: Int,
    @SerializedName("list")
    var list: List<City>
)

data class City(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("coord")
    var coord: Coord,
    @SerializedName("main")
    var main: Main,
    @SerializedName("dt")
    var dt: Int,
    @SerializedName("wind")
    var wind: Wind,
    @SerializedName("sys")
    var sys: Sys,
    @SerializedName("clouds")
    var clouds: Clouds,
    @SerializedName("weather")
    var weather: List<Weather>,
    @SerializedName("rain")
    var rain: Any,
    @SerializedName("snow")
    var snow: Any
)

data class Clouds(
    @SerializedName("all")
    var all: Int
)

data class Coord(
    @SerializedName("lat")
    var lat: Double,
    @SerializedName("lon")
    var lon: Double
)

data class Main(
    @SerializedName("temp")
    var temp: Double,
    @SerializedName("pressure")
    var pressure: Int,
    @SerializedName("humidity")
    var humidity: Int,
    @SerializedName("temp_min")
    var tempMin: Double,
    @SerializedName("temp_max")
    var tempMax: Double
)

data class Sys(
    @SerializedName("country")
    var country: String
)

data class Weather(
    @SerializedName("id")
    var id: Int,
    @SerializedName("main")
    var main: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("icon")
    var icon: String
)

data class Wind(
    @SerializedName("wind")
    var speed: Double,
    @SerializedName("deg")
    var deg: Int,
    @SerializedName("gust")
    var gust: Int
)
