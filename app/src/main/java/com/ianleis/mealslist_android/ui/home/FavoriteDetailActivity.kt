package com.ianleis.mealslist_android.ui.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil3.load
import com.ianleis.mealslist_android.R
import com.ianleis.mealslist_android.data.db.MealFavoriteDAO
import com.ianleis.mealslist_android.databinding.ActivityFavoriteDetailBinding

class FavoriteDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MEAL_ID = "MEAL_ID"
    }

    private lateinit var binding: ActivityFavoriteDetailBinding
    private lateinit var mealFavoriteDAO: MealFavoriteDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavoriteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mealFavoriteDAO = MealFavoriteDAO(this)

        val mealID = intent.getIntExtra(EXTRA_MEAL_ID, -1)
        if (mealID == -1) {
            finish()
            return
        } else {
            val meal = mealFavoriteDAO.find(mealID)
            if (meal != null) {
                binding.titleTextView.text = meal.strMeal
                binding.categoryChip.text = getString(R.string.category_and_area, meal.strCategory, meal.strArea)
                binding.textInstructions.text = meal.strInstructions
                binding.mealImage.load(meal.strMealThumb)
            }
        }
    }
}