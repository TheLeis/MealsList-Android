package com.ianleis.mealslist_android.ui.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.ianleis.mealslist_android.data.network.Category
import com.ianleis.mealslist_android.data.network.MealService
import com.ianleis.mealslist_android.databinding.ActivityMainBinding
import com.ianleis.mealslist_android.ui.category.CategoryAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var adapter : CategoryAdapter
    var categoryList: List<Category> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.main)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        adapter = CategoryAdapter(categoryList)
        binding.categoryList.adapter = adapter
        binding.categoryList.layoutManager = GridLayoutManager(this, 1)
        getCategoryList()
    }

    fun getCategoryList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = MealService.getInstance()
                categoryList = service.getAllCategories().categories
                CoroutineScope(Dispatchers.Main).launch {
                    adapter.updateItems(categoryList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}