package com.ianleis.mealslist_android.ui.category

import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.ianleis.mealslist_android.data.network.Category
import com.ianleis.mealslist_android.databinding.ItemCategoryBinding
import coil3.load
import coil3.request.crossfade
import com.ianleis.mealslist_android.R

class CategoryViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(category: Category) {
        binding.titleTextView.text = category.strCategory
        binding.thumbnailImageView.load(category.strCategoryThumb) {
            crossfade(true)
        }
        binding.infoButton.setOnClickListener { showInfo(category.strCategory, category.strCategoryDescription) }
        binding.infoButton.contentDescription = binding.root.context.getString(R.string.info_about_category, category.strCategory)
    }

    fun showInfo(title: String, description: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(binding.root.context)
        builder
            .setMessage(description)
            .setTitle(title)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}