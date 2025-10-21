package com.ianleis.mealslist_android.ui.home

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.ianleis.mealslist_android.R
import com.ianleis.mealslist_android.data.network.MealData
import com.ianleis.mealslist_android.data.network.MealService
import com.ianleis.mealslist_android.databinding.ActivityDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MEAL_ID = "MEAL_ID"
    }

    private lateinit var binding: ActivityDetailBinding
    lateinit var meal: MealData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.main)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val mealID = intent.getIntExtra(EXTRA_MEAL_ID, -1)
        getGame(mealID)
    }

    fun loadData() {
        binding.titleTextView.text = meal.strMeal
        binding.categoryChip.text = getString(R.string.category_and_area, meal.strCategory, meal.strArea)
        binding.textInstructions.text = meal.strInstructions
    }

    fun getGame(id: Int) {
        lifecycleScope.launch {
            try {
                val service = withContext(Dispatchers.IO) {
                    MealService.getInstance()
                }
                meal = service.getMealById(id).meals[0]
                CoroutineScope(Dispatchers.Main).launch {
                    loadData()
                }
            } catch (e: IOException) {
                // Handles IO exceptions, like network errors
                Log.e("DetailActivity", "Error fetching meal: ${e.message}")
            } catch (e: Exception) {
                // Handles other exceptions
                Log.e("DetailActivity", "Error fetching meal: ${e.message}")
            }
        }
    }
}