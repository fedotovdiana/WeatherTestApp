package com.itis.group11801.fedotova.weathertest.recycler

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.itis.group11801.fedotova.weathertest.net.cities.City
import com.itis.group11801.fedotova.weathertest.utils.setTempColor
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_city.*

class CityViewHolder(
    override val containerView: View
): RecyclerView.ViewHolder(containerView), LayoutContainer {

    @RequiresApi(Build.VERSION_CODES.M)
    fun bind(city: City, onClick: (Int) -> Unit) {
        tv_rec_name.text = city.name
        tv_rec_temp.text = city.main.temp.toString()
        tv_rec_temp.setTextColor(setTempColor(containerView.context, city.main.temp))
        itemView.setOnClickListener{
            onClick(city.id)
        }
    }
}
