package com.itis.group11801.fedotova.weathertest.utils

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.itis.group11801.fedotova.weathertest.R


const val ID = "id"
const val PERMISSIONS_REQUEST_CODE = 10

@RequiresApi(Build.VERSION_CODES.M)
fun setTempColor(context: Context, temp: Double): Int {
    var color = 0
    if (temp <= -20) {
        color = context.getColor(R.color.colorTemp1)
    } else if (temp > -20 && temp <= -5) {
        color = context.getColor(R.color.colorTemp2)
    } else if (temp > -5 && temp <= 5) {
        color = context.getColor(R.color.colorTemp3)
    } else if (temp > 5 && temp <= 20) {
        color = context.getColor(R.color.colorTemp4)
    } else if (temp > 20) {
        color = context.getColor(R.color.colorTemp5)
    }
    return color
}

fun degreeToDirection(deg: Int): String {
    val directions = arrayOf("N", "NE", "E", "SE", "S", "SW", "W", "NW")
    val i = (deg / 45.0) + 0.5
    return directions[(i % 8).toInt()]
}
