package com.ianleis.mealslist_android.ui.favorites

import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import com.ianleis.mealslist_android.R
import com.ianleis.mealslist_android.data.network.MealItemFavorite
import com.ianleis.mealslist_android.databinding.ItemMealBinding

class MealFavoriteViewHolder (val binding: ItemMealBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(meal: MealItemFavorite) {
        binding.titleTextView.text = meal.strMeal
        binding.thumbnailImageView.load(meal.strMealThumb) {
            crossfade(true)
        }
        binding.favoriteButton.setIconResource(R.drawable.ic_favorite)
    }
}