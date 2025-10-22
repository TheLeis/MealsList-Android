package com.ianleis.mealslist_android.ui.gallery

import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.placeholder
import com.ianleis.mealslist_android.R
import com.ianleis.mealslist_android.data.network.Ingredient
import com.ianleis.mealslist_android.databinding.ItemGalleryBinding

class GalleryViewHolder(val binding: ItemGalleryBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(ingredientItem: Ingredient) {
        val ingredientUrl = "https://www.themealdb.com/images/ingredients/${ingredientItem.name.replace(' ', '_')}-medium.png"
        binding.ingredientImageView.load(ingredientUrl) {
            placeholder(R.drawable.bg_image_placeholder)
            crossfade(true)

        }
        binding.ingredientTextView.text = binding.root.context.getString(R.string.measure_and_name, ingredientItem.measure, ingredientItem.name)
    }
}