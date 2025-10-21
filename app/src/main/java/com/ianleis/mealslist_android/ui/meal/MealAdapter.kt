package com.ianleis.mealslist_android.ui.meal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ianleis.mealslist_android.data.network.MealItem
import com.ianleis.mealslist_android.databinding.ItemMealBinding

class MealAdapter (
    var items: List<MealItem>,
    private val onClickListener: (Int) -> Unit
) : RecyclerView.Adapter<MealViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMealBinding.inflate(layoutInflater, parent, false)
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val item = items[position]
        holder.render(item)
        holder.itemView.setOnClickListener {
            onClickListener(item.idMeal)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(items: List<MealItem>) {
        this.items = items
        notifyDataSetChanged()
    }
}