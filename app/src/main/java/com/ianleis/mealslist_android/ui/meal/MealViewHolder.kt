package com.ianleis.mealslist_android.ui.meal

import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import com.ianleis.mealslist_android.R
import com.ianleis.mealslist_android.data.db.MealFavoriteDAO
import com.ianleis.mealslist_android.data.network.MealItem
import com.ianleis.mealslist_android.databinding.ItemMealBinding

class MealViewHolder (
    val binding: ItemMealBinding,
    val mealFavoriteDAO: MealFavoriteDAO
) : RecyclerView.ViewHolder(binding.root) {

    fun render(meal: MealItem) {
        binding.titleTextView.text = meal.strMeal
        binding.thumbnailImageView.load(meal.strMealThumb) {
            crossfade(true)
        }
        val isFavorite = mealFavoriteDAO.find(meal.idMeal) != null
        val favoriteIcon = if (isFavorite) { R.drawable.ic_favorite }
        else { R.drawable.ic_favorite_outline }
        binding.favoriteButton.setIconResource(favoriteIcon)
    }
}