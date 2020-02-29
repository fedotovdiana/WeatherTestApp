package com.itis.group11801.fedotova.weathertest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import com.itis.group11801.fedotova.weathertest.R
import com.itis.group11801.fedotova.weathertest.net.ApiFactory
import com.itis.group11801.fedotova.weathertest.net.WeatherService
import com.itis.group11801.fedotova.weathertest.recycler.CityAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.*


class SearchFragment : Fragment(), CoroutineScope by MainScope() {

    lateinit var adapter: CityAdapter
    lateinit var service: WeatherService


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = CityAdapter(
            Data.data
        ) {
            showDetails(it)
        }
        rv_cities.adapter = adapter

        service = ApiFactory.weatherService

        //TODO get geo
        //findCities()

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
        launch {
            val response = withContext(Dispatchers.IO) {
                service.weatherByName(query)
            }
            showDetails(response.id)
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

    private fun showDetails(id: Int) {
        fragmentManager?.beginTransaction()?.apply {
            replace(
                R.id.container,
                DetailsFragment.newInstance(
                    id
                )
            )
            addToBackStack(null)
            commit()
        }
    }


    object Data {
        var data: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    }

    companion object {
        fun newInstance() =
            SearchFragment()
    }

}

//TODO make a snackbar to handle an error

//TODO Recycler geoposition
//TODO Recycler onclick

//TODO design DetailsFragment's disign
//TODO Recycler design
