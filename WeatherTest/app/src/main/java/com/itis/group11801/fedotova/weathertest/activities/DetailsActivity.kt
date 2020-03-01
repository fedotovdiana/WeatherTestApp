package com.itis.group11801.fedotova.weathertest.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.itis.group11801.fedotova.weathertest.R
import com.itis.group11801.fedotova.weathertest.net.ApiFactory
import com.itis.group11801.fedotova.weathertest.net.WeatherService
import com.itis.group11801.fedotova.weathertest.utils.ID
import com.itis.group11801.fedotova.weathertest.utils.degreeToDirection
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.coroutines.*

class DetailsActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    lateinit var service: WeatherService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        service = ApiFactory.weatherService
        val id = intent.extras?.getInt(ID) ?: 0

        launch {
            val response = withContext(Dispatchers.IO) {
                service.weatherByID(id)
            }
            val wind = "${degreeToDirection(response.wind.deg.toInt())}, " +
                    "${response.wind.speed.toInt()} m/s"
            val temp = "${response.main.temp} C"
            val humidity = "${response.main.humidity} %"
            val fl = "${response.main.feelsLike} C"
            tv_city.text = response.name
            tv_show_temp.text = temp
            tv_show_humidity.text = humidity
            tv_show_wind.text = wind
            tv_show_feels_like.text = fl
        }
    }
}
