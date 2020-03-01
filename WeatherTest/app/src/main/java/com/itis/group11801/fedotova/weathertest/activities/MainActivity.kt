package com.itis.group11801.fedotova.weathertest.activities

import android.content.Intent
import android.os.Build.ID
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.itis.group11801.fedotova.weathertest.R
import com.itis.group11801.fedotova.weathertest.net.ApiFactory
import com.itis.group11801.fedotova.weathertest.net.WeatherService
import com.itis.group11801.fedotova.weathertest.recycler.CityAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.HttpException

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    lateinit var adapter: CityAdapter
    lateinit var service: WeatherService

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        service = ApiFactory.weatherService
        initSearchView()

        val data: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

        adapter = CityAdapter(data) {
            showDetails(it)
        }
        rv_cities.adapter = adapter
    }

    private fun showDetails(id: Int) {
        val intent = Intent(this@MainActivity, DetailsActivity::class.java)
        intent.putExtra(ID, id)
        startActivity(intent)
    }

    private fun initSearchView() {
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    find(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun find(query: String): Boolean {
        try {
            launch {
                val response = withContext(Dispatchers.IO) {
                    service.weatherByName(query)
                }
                showDetails(response.id)
            }
        } catch (e: HttpException) {
            Snackbar.make(main_layout, getString(R.string.tv_error), Snackbar.LENGTH_LONG).show()
            Log.e("EXCEPTION", "$e")
        }
        return false
    }

    private fun findCities(lat: Double, lon: Double): Boolean {
        launch {
            val response = withContext(Dispatchers.IO) {
                service.findCitiesInCycle(lat, lon)
            }
            //TODO addToList
        }
        return false
    }
}
