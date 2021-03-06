package com.itis.group11801.fedotova.weathertest.recycler

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.itis.group11801.fedotova.weathertest.R
import com.itis.group11801.fedotova.weathertest.net.cities.City

class CityAdapter(
    val data: List<City>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<CityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CityViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_city,
                parent,
                false
            )
        )

    override fun getItemCount() = data.size

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: CityViewHolder, position: Int) =
        holder.bind(data[position], onClick)
}
