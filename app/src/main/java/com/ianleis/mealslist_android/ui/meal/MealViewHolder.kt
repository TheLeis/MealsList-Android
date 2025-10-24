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
        if (!isFavorite) {
            binding.favoriteButton.setIconResource(R.drawable.ic_favorite_outline)
            binding.favoriteButton.contentDescription = binding.root.context.getString(R.string.mark_meal_as_favorite, meal.strMeal)
        }
        else {
            binding.favoriteButton.setIconResource(R.drawable.ic_favorite)
            binding.favoriteButton.contentDescription = binding.root.context.getString(R.string.remove_meal_from_favorite, meal.strMeal)

        }
    }
}