package com.itis.group11801.fedotova.weathertest.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itis.group11801.fedotova.weathertest.R
import com.itis.group11801.fedotova.weathertest.net.ApiFactory
import com.itis.group11801.fedotova.weathertest.net.WeatherService
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.coroutines.*

class DetailsFragment : Fragment(), CoroutineScope by MainScope() {

    lateinit var service: WeatherService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val id = arguments?.getInt("ID")

        service = ApiFactory.weatherService

        launch {
            val response = withContext(Dispatchers.IO) {
                id?.let {
                    service.weatherByID(id)
                }
            }
            if (response != null) {
                tv_city.text = response.name
                tv_show_temp.text = response.main.temp.toString()
                tv_show_humidity.text= response.main.humidity.toString()
                tv_show_wind.text = response.wind.speed.toString()
                tv_show_feels_like.text = response.main.feelsLike.toString()
            }
        }
    }

    companion object {
        private const val ID = "ID"

        fun newInstance(id: Int) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putInt(ID, id)
            }
        }
    }
}
