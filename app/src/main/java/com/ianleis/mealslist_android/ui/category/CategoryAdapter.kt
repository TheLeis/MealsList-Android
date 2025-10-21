package com.ianleis.mealslist_android.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ianleis.mealslist_android.data.network.Category
import com.ianleis.mealslist_android.databinding.ItemCategoryBinding

class CategoryAdapter(
    var items: List<Category>,
    val onClickListener: (String) -> Unit
) : RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(layoutInflater, parent, false)
        return CategoryViewHolder(binding)
    }
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = items[position]
        holder.render(item)
        holder.itemView.setOnClickListener {
            onClickListener(item.strCategory)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(items: List<Category>) {
        this.items = items
        notifyDataSetChanged()
    }
}

