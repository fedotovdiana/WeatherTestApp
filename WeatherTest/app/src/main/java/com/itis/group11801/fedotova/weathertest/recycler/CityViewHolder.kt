package com.itis.group11801.fedotova.weathertest.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_city.*

class CityViewHolder(
    override val containerView: View
): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(id: Int,  onClick: (Int) -> Unit) {
        tv_title.text = id.toString()
        itemView.setOnClickListener{
            onClick(id)
        }
    }
}
