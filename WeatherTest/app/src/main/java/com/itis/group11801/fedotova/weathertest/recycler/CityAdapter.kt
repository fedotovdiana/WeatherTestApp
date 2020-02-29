package com.itis.group11801.fedotova.weathertest.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.group11801.fedotova.weathertest.R

class CityAdapter(
    val data: List<Int>,
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

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) =
        holder.bind(data[position], onClick)
}