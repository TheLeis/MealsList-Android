package com.ianleis.mealslist_android.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.ianleis.mealslist_android.R
import com.ianleis.mealslist_android.data.network.Category
import com.ianleis.mealslist_android.data.network.MealService
import com.ianleis.mealslist_android.databinding.ActivityMainBinding
import com.ianleis.mealslist_android.ui.category.CategoryAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

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
            { category -> onItemSelected(category) }
        binding.categoryList.adapter = adapter
        binding.categoryList.layoutManager = GridLayoutManager(this, 1)
        getCategoryList()
    }

    fun onItemSelected(categoryName: String) {
        val intent = Intent(this, MealsCategoryActivity::class.java)
        intent.putExtra(MealsCategoryActivity.EXTRA_CATEGORY_NAME, categoryName)
        startActivity(intent)
    }

    fun getCategoryList() {
        binding.progressBar.isVisible = true
        lifecycleScope.launch {
            try {
                val service = withContext(Dispatchers.IO) {
                    MealService.getInstance()
                }
                categoryList = service.getAllCategories().categories
                CoroutineScope(Dispatchers.Main).launch {
                    adapter.updateItems(categoryList)
                }
                binding.progressBar.isVisible = false
            } catch (e: IOException) {
                // Handles IO exceptions, like network errors
                Log.e("MainActivity", "Error fetching categories: ${e.message}")
                showError(getString(R.string.error_no_internet))
            } catch (e: Exception) {
                // Handles other exceptions
                Log.e("MainActivity", "Error fetching categories: ${e.message}")
                showError(getString(R.string.error_generic))
            }
        }
    }

    private fun showError(message: String) {
        binding.progressBar.isVisible = false
        binding.textError.visibility = View.VISIBLE
        binding.textError.text = message
    }
}