package com.ianleis.mealslist_android.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ianleis.mealslist_android.data.network.MealItemFavorite
import com.ianleis.mealslist_android.databinding.ItemMealBinding

class MealFavoriteAdapter (
    var items: List<MealItemFavorite>,
    private val onClickListener: (Int) -> Unit,
    private val onFavoriteClickListener: (Int) -> Unit
) : RecyclerView.Adapter<MealFavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealFavoriteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMealBinding.inflate(layoutInflater, parent, false)
        return MealFavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealFavoriteViewHolder, position: Int) {
        val item = items[position]
        holder.render(item)
        holder.itemView.setOnClickListener {
            onClickListener(item.idMeal)
        }
        holder.binding.favoriteButton.setOnClickListener {
            onFavoriteClickListener(item.idMeal)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(items: List<MealItemFavorite>) {
        this.items = items
        notifyDataSetChanged()
    }
}