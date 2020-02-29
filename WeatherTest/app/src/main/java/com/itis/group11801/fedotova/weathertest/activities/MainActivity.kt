package com.itis.group11801.fedotova.weathertest.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*
import com.itis.group11801.fedotova.weathertest.R
import com.itis.group11801.fedotova.weathertest.fragments.SearchFragment

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

//    private lateinit var service: WeatherService

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            replace(
                R.id.container,
                SearchFragment.newInstance()
            )
            addToBackStack(SearchFragment::class.java.name)
            commit()
        }
//        service = ApiFactory.weatherService
//
//        launch {
//            val response = withContext(Dispatchers.IO) {
//                service.weatherByName("Kazan")
//            }
//            Toast.makeText(this@MainActivity, response.main.toString(), Toast.LENGTH_SHORT)
//
//        }

    }
}
