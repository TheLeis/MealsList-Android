package com.ianleis.mealslist_android.ui.area

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ianleis.mealslist_android.data.network.Area
import com.ianleis.mealslist_android.databinding.ItemAreaBinding

class AreaAdapter(
    var items: List<Area>,
    val onClickListener: (String) -> Unit
) : RecyclerView.Adapter<AreaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemAreaBinding.inflate(layoutInflater, parent, false)
        return AreaViewHolder(binding)
    }
    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
        val item = items[position]
        holder.render(item)
        holder.itemView.setOnClickListener {
            onClickListener(item.strArea)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(items: List<Area>) {
        this.items = items
        notifyDataSetChanged()
    }
}

