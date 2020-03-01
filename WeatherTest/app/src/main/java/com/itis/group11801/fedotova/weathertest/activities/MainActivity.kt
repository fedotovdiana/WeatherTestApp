package com.itis.group11801.fedotova.weathertest.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.itis.group11801.fedotova.weathertest.R
import com.itis.group11801.fedotova.weathertest.net.ApiFactory
import com.itis.group11801.fedotova.weathertest.net.WeatherService
import com.itis.group11801.fedotova.weathertest.recycler.CityAdapter
import com.itis.group11801.fedotova.weathertest.utils.ID
import com.itis.group11801.fedotova.weathertest.utils.PERMISSIONS_REQUEST_CODE
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.HttpException

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    lateinit var adapter: CityAdapter
    lateinit var service: WeatherService
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var lon = 49.12
    private var lat = 55.79

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()

        service = ApiFactory.weatherService
        initSearchView()

        findCities(lat, lon)
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
            adapter = CityAdapter(response.list) {
                showDetails(it)
            }
            rv_cities.adapter = adapter
        }
        return false
    }

    //Permissions
    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSIONS_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    location?.let {
                        lon = it.longitude
                        lat = it.latitude
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
            }
        } else {
            requestPermissions()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
}
