package com.itis.group11801.fedotova.weathertest.utils

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.itis.group11801.fedotova.weathertest.R

const val ID = "id"
const val PERMISSIONS_REQUEST_CODE = 10
const val LON = 49.12
const val LAT = 55.79
const val TEMP_COLD = -20
const val TEMP_COOL = -5
const val TEMP_WARM = 5
const val TEMP_HOT = 20
const val DEG_HALF = 45.0
const val HALF = 0.5
const val SIZE = 8

@RequiresApi(Build.VERSION_CODES.M)
fun setTempColor(context: Context, temp: Double): Int {
    var color = 0
    if (temp <= TEMP_COLD) {
        color = context.getColor(R.color.colorTemp1)
    } else if (temp > TEMP_COLD && temp <= -TEMP_COOL) {
        color = context.getColor(R.color.colorTemp2)
    } else if (temp > TEMP_COOL && temp <= TEMP_WARM) {
        color = context.getColor(R.color.colorTemp3)
    } else if (temp > TEMP_WARM && temp <= TEMP_HOT) {
        color = context.getColor(R.color.colorTemp4)
    } else if (temp > TEMP_HOT) {
        color = context.getColor(R.color.colorTemp5)
    }
    return color
}

fun degreeToDirection(deg: Int): String {
    val directions = arrayOf("N", "NE", "E", "SE", "S", "SW", "W", "NW")
    val i = (deg / DEG_HALF) + HALF
    return directions[(i % SIZE).toInt()]
}
