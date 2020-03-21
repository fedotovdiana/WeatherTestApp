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
import com.itis.group11801.fedotova.weathertest.R
import com.itis.group11801.fedotova.weathertest.net.ApiFactory
import com.itis.group11801.fedotova.weathertest.net.WeatherResponse
import com.itis.group11801.fedotova.weathertest.net.WeatherService
import com.itis.group11801.fedotova.weathertest.net.cities.CitiesResponse
import com.itis.group11801.fedotova.weathertest.recycler.CityAdapter
import com.itis.group11801.fedotova.weathertest.utils.ID
import com.itis.group11801.fedotova.weathertest.utils.LAT
import com.itis.group11801.fedotova.weathertest.utils.LON
import com.itis.group11801.fedotova.weathertest.utils.PERMISSIONS_REQUEST_CODE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @Suppress("LateinitUsage")
    lateinit var adapter: CityAdapter

    @Suppress("LateinitUsage")
    lateinit var service: WeatherService

    @Suppress("LateinitUsage")
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var lon = LON
    private var lat = LAT

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

    private fun find(query: String) {
        val job = service
            .weatherByName(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResult, this::handleError)
    }

    private fun handleResult(response: WeatherResponse) {
        showDetails(response.id)
    }

    private fun handleError(t: Throwable) {
        Log.e("Observer", "" + t.toString())
        Toast.makeText(this, "ERROR IN GETTING COUPONS", Toast.LENGTH_LONG).show()
    }

    private fun findCities(lat: Double, lon: Double) {
        val job = service
            .findCitiesInCycle(lat, lon)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleRecResult, this::handleError)
    }

    private fun handleRecResult(response: CitiesResponse) {
        adapter = CityAdapter(response.list) {
            showDetails(it)
        }
        rv_cities.adapter = adapter
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
        if (requestCode == PERMISSIONS_REQUEST_CODE && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            getLastLocation()
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
