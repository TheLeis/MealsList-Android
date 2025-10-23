package com.ianleis.mealslist_android.ui.home

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import coil3.load
import com.ianleis.mealslist_android.R
import com.ianleis.mealslist_android.data.db.MealFavorite
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
        if (mealID == -1) showError(getString(R.string.error_generic))
        else {
            val meal = mealFavoriteDAO.find(mealID)
            if (meal == null) showError(getString(R.string.error_generic))
            else {
                // Text info
                binding.titleTextView.text = meal.strMeal
                binding.categoryChip.text = getString(R.string.category_and_area, meal.strCategory, meal.strArea)
                binding.textInstructions.text = meal.strInstructions
                // Meal image
                binding.mealImage.load(meal.strMealThumb)
                // Ingredients
                binding.textIngredients.text = getIngredients(meal)
            }
        }
    }

    private fun getIngredients(meal: MealFavorite): String {
        val ingredients = mutableListOf<String>()
        if (!meal.strIngredient1.isNullOrBlank()) ingredients.add("${meal.strMeasure1?.replaceFirstChar(Char::uppercase)} ${meal.strIngredient1}")
        if (!meal.strIngredient2.isNullOrBlank()) ingredients.add("${meal.strMeasure2?.replaceFirstChar(Char::uppercase)} ${meal.strIngredient2}")
        if (!meal.strIngredient3.isNullOrBlank()) ingredients.add("${meal.strMeasure3?.replaceFirstChar(Char::uppercase)} ${meal.strIngredient3}")
        if (!meal.strIngredient4.isNullOrBlank()) ingredients.add("${meal.strMeasure4?.replaceFirstChar(Char::uppercase)} ${meal.strIngredient4}")
        if (!meal.strIngredient5.isNullOrBlank()) ingredients.add("${meal.strMeasure5?.replaceFirstChar(Char::uppercase)} ${meal.strIngredient5}")
        if (!meal.strIngredient6.isNullOrBlank()) ingredients.add("${meal.strMeasure6?.replaceFirstChar(Char::uppercase)} ${meal.strIngredient6}")
        if (!meal.strIngredient7.isNullOrBlank()) ingredients.add("${meal.strMeasure7?.replaceFirstChar(Char::uppercase)} ${meal.strIngredient7}")
        if (!meal.strIngredient8.isNullOrBlank()) ingredients.add("${meal.strMeasure8?.replaceFirstChar(Char::uppercase)} ${meal.strIngredient8}")
        if (!meal.strIngredient9.isNullOrBlank()) ingredients.add("${meal.strMeasure9?.replaceFirstChar(Char::uppercase)} ${meal.strIngredient9}")
        if (!meal.strIngredient10.isNullOrBlank()) ingredients.add("${meal.strMeasure10?.replaceFirstChar(Char::uppercase)} ${meal.strIngredient10}")
        if (!meal.strIngredient11.isNullOrBlank()) ingredients.add("${meal.strMeasure11?.replaceFirstChar(Char::uppercase)} ${meal.strIngredient11}")
        if (!meal.strIngredient12.isNullOrBlank()) ingredients.add("${meal.strMeasure12?.replaceFirstChar(Char::uppercase)} ${meal.strIngredient12}")
        if (!meal.strIngredient13.isNullOrBlank()) ingredients.add("${meal.strMeasure13?.replaceFirstChar(Char::uppercase)} ${meal.strIngredient13}")
        if (!meal.strIngredient14.isNullOrBlank()) ingredients.add("${meal.strMeasure14?.replaceFirstChar(Char::uppercase)} ${meal.strIngredient14}")
        if (!meal.strIngredient15.isNullOrBlank()) ingredients.add("${meal.strMeasure15?.replaceFirstChar(Char::uppercase)} ${meal.strIngredient15}")
        if (!meal.strIngredient16.isNullOrBlank()) ingredients.add("${meal.strMeasure16?.replaceFirstChar(Char::uppercase)} ${meal.strIngredient16}")
        if (!meal.strIngredient17.isNullOrBlank()) ingredients.add("${meal.strMeasure17?.replaceFirstChar(Char::uppercase)} ${meal.strIngredient17}")
        if (!meal.strIngredient18.isNullOrBlank()) ingredients.add("${meal.strMeasure18?.replaceFirstChar(Char::uppercase)} ${meal.strIngredient18}")
        if (!meal.strIngredient19.isNullOrBlank()) ingredients.add("${meal.strMeasure19?.replaceFirstChar(Char::uppercase)} ${meal.strIngredient19}")
        if (!meal.strIngredient20.isNullOrBlank()) ingredients.add("${meal.strMeasure20?.replaceFirstChar(Char::uppercase)} ${meal.strIngredient20}")
        return ingredients.joinToString("\n")
    }

    private fun showError(message: String) {
        binding.scrollView.isVisible = false
        binding.textError.visibility = View.VISIBLE
        binding.textError.text = message
    }
}
