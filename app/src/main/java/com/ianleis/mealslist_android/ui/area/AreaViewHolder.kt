package com.ianleis.mealslist_android.ui.area

import androidx.recyclerview.widget.RecyclerView
import com.ianleis.mealslist_android.data.network.Area
import com.ianleis.mealslist_android.databinding.ItemAreaBinding

class AreaViewHolder(val binding: ItemAreaBinding) : RecyclerView.ViewHolder(binding.root) {
    fun render(area: Area) { binding.titleTextView.text = area.strArea }
}