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
import com.ianleis.mealslist_android.data.network.MealItem
import com.ianleis.mealslist_android.data.network.MealService
import com.ianleis.mealslist_android.databinding.ActivityMealsCategoryBinding
import com.ianleis.mealslist_android.ui.meal.MealAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class MealsCategoryActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CATEGORY_NAME = "CATEGORY_NAME"
    }

    private lateinit var binding: ActivityMealsCategoryBinding
    lateinit var adapter : MealAdapter
    var mealList: List<MealItem> = emptyList()
    lateinit var categoryName : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMealsCategoryBinding.inflate(layoutInflater)
        setContentView(binding.main)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        categoryName = intent.getStringExtra(EXTRA_CATEGORY_NAME)!!
        adapter = MealAdapter(mealList)
            { id -> onItemSelected(id) }
        binding.mealList.adapter = adapter
        binding.mealList.layoutManager = GridLayoutManager(this, 1)
        getMealList()
        binding.textCategoryMeal.text = getString(R.string.meal_list_by_category, categoryName)
    }

    fun onItemSelected(mealID: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MEAL_ID, mealID)
        startActivity(intent)
    }

    private fun getMealList() {
        binding.progressBar.isVisible = true
        lifecycleScope.launch {
            try {
                val service = withContext(Dispatchers.IO) {
                    MealService.getInstance()
                }
                mealList = service.getMealsByCategory(categoryName).meals
                CoroutineScope(Dispatchers.Main).launch {
                    adapter.updateItems(mealList)
                }
                binding.progressBar.isVisible = false
            } catch (e: IOException) {
                // Handles IO exceptions, like network errors
                Log.e("MealsCategoryActivity", "Error fetching meals: ${e.message}")
                showError(getString(R.string.error_no_internet))
            } catch (e: Exception) {
                // Handles other exceptions
                Log.e("MealsCategoryActivity", "Error fetching meals: ${e.message}")
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