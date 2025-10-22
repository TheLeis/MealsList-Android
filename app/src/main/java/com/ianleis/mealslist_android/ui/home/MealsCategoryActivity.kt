package com.ianleis.mealslist_android.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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
    var filteredMealList: List<MealItem> = emptyList()
    var originalMealList: List<MealItem> = emptyList()
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
        adapter = MealAdapter(filteredMealList)
            { id -> onItemSelected(id) }
        binding.mealList.adapter = adapter
        binding.mealList.layoutManager = GridLayoutManager(this, 1)
        getMealList()
        binding.textCategoryMeal.text = getString(R.string.meal_list_by_category, categoryName)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.appbar_menu, menu)

        val searchView = menu.findItem(R.id.action_search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                binding.textError.visibility = View.GONE
                filteredMealList = originalMealList.filter { it.strMeal.contains(newText, true) }
                adapter.updateItems(filteredMealList)
                if (filteredMealList.isEmpty()) {
                    showError(getString(R.string.error_no_results))
                }
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
                originalMealList = service.getMealsByCategory(categoryName).meals
                filteredMealList = originalMealList
                CoroutineScope(Dispatchers.Main).launch {
                    adapter.updateItems(filteredMealList)
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