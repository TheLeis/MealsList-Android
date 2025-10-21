package com.ianleis.mealslist_android.ui.meal

import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import com.ianleis.mealslist_android.data.network.MealItem
import com.ianleis.mealslist_android.databinding.ItemMealBinding

class MealViewHolder (val binding: ItemMealBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(meal: MealItem) {
        binding.titleTextView.text = meal.strMeal
        binding.thumbnailImageView.load(meal.strMealThumb) {
            crossfade(true)
        }
    }
}