package com.ianleis.mealslist_android.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ianleis.mealslist_android.R
import com.ianleis.mealslist_android.data.db.MealFavoriteDAO
import com.ianleis.mealslist_android.data.db.MealFavorite
import com.ianleis.mealslist_android.data.network.MealData
import com.ianleis.mealslist_android.data.network.MealItem
import com.ianleis.mealslist_android.data.network.MealService
import com.ianleis.mealslist_android.databinding.ActivityMealsListBinding
import com.ianleis.mealslist_android.ui.meal.MealAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL

class MealsListActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CATEGORY_NAME = "CATEGORY_NAME"
        const val EXTRA_AREA_NAME = "AREA_NAME"
    }

    private lateinit var binding: ActivityMealsListBinding
    private lateinit var adapter : MealAdapter
    private var filteredMealList: List<MealItem> = emptyList()
    private var originalMealList: List<MealItem> = emptyList()
    private lateinit var categoryName : String
    private lateinit var areaName : String
    private lateinit var mealFavoriteDAO: MealFavoriteDAO
    private var bottomInset: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMealsListBinding.inflate(layoutInflater)
        setContentView(binding.main)
        setSupportActionBar(binding.toolbar)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            bottomInset = systemBars.bottom
            insets
        }
        mealFavoriteDAO = MealFavoriteDAO(this)
        categoryName = intent.getStringExtra(EXTRA_CATEGORY_NAME).orEmpty()
        areaName = intent.getStringExtra(EXTRA_AREA_NAME).orEmpty()
        Log.i("MealsListActivity", "Category: $categoryName, Area: $areaName")
        adapter = MealAdapter(
            filteredMealList, mealFavoriteDAO,
            { id -> onItemSelected(id) },
            { id, position -> onFavoriteSelected(id, position) }
        )
        binding.mealList.adapter = adapter
        binding.mealList.layoutManager = GridLayoutManager(this, 1)
        binding.swipeRefreshLayout.setOnRefreshListener { getMealList() }
        getMealList()
        if (categoryName.isNotEmpty()) binding.textCategoryMeal.text = getString(R.string.meal_list_by, categoryName)
        else binding.textCategoryMeal.text = getString(R.string.meal_list_by, areaName)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.appbar_menu_meals, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean { return false }

            override fun onQueryTextChange(newText: String): Boolean {
                binding.textError.visibility = View.GONE
                filteredMealList = originalMealList.filter { it.strMeal.contains(newText, true) }
                adapter.updateItems(filteredMealList)
                if (filteredMealList.isEmpty()) showError(getString(R.string.error_no_results))
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
            R.id.action_favorites -> {
                val intent = Intent(this, FavoritesActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getMealList() {
        binding.textError.visibility = View.GONE
        if (!binding.swipeRefreshLayout.isRefreshing) {
            binding.progressBar.isVisible = true
        }

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val service = MealService.getInstance()
                val meals = if (categoryName.isNotEmpty()) service.getMealsByCategory(categoryName).meals
                else if (areaName.isNotEmpty()) service.getMealsByArea(areaName).meals
                else emptyList()
                originalMealList = meals
                filteredMealList = meals
                withContext(Dispatchers.Main) {
                    adapter.updateItems(filteredMealList)
                    binding.mealList.visibility = View.VISIBLE
                }
            } catch (e: IOException) {
                // Handles IO exceptions, like network errors
                Log.e("MealsListActivity", "Error fetching meals: ${e.message}")
                withContext(Dispatchers.Main) { showError(getString(R.string.error_no_internet)) }
            } catch (e: Exception) {
                // Handles other exceptions
                Log.e("MealsListActivity", "Error fetching meals: ${e.message}")
                withContext(Dispatchers.Main) { showError(getString(R.string.error_generic)) }
            } finally {
                withContext(Dispatchers.Main) {
                    binding.progressBar.isVisible = false
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        }
    }

    fun onItemSelected(mealID: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MEAL_ID, mealID)
        startActivity(intent)
    }

    fun onFavoriteSelected(mealID: Int, position: Int) {
        val isFavorite = mealFavoriteDAO.find(mealID) != null
        if (!isFavorite) {
            lifecycleScope.launch {
                try {
                    val mealData = withContext(Dispatchers.IO) { MealService.getInstance().getMealById(mealID).meals[0] }
                    addMealToFavorites(mealData, position)
                } catch (e: IOException) {
                    Log.e("MealsListActivity", "Error fetching meal details: ${e.message}")
                    showSnackbar(getString(R.string.error_no_internet_favorite))
                } catch (e: Exception) {
                    Log.e("MealsListActivity", "Error fetching meal details: ${e.message}")
                    showSnackbar(getString(R.string.error_generic))
                }
            }
        } else {
            mealFavoriteDAO.delete(mealID)
            adapter.notifyItemChanged(position)
        }
    }

    fun addMealToFavorites(mealData: MealData, position: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val url = URL(mealData.strMealThumb)
                val thumbnail = url.readBytes()
                val mealFavorite = MealFavorite(mealData, thumbnail)
                val result = mealFavoriteDAO.insert(mealFavorite)
                withContext(Dispatchers.Main) {
                    if (result != -1L) adapter.notifyItemChanged(position)
                    else showSnackbar(getString(R.string.error_generic))
                }
            } catch (e: IOException) {
                Log.e("MealsListActivity", "Error fetching meal details: ${e.message}")
                showSnackbar(getString(R.string.error_no_internet_favorite))
            } catch (e: Exception) {
                Log.e("MealsListActivity", "Error saving favorite: ${e.message}")
                withContext(Dispatchers.Main) { showSnackbar(getString(R.string.error_generic)) }
            }
        }
    }

    private fun showSnackbar(message: String) {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
        val params = snackbar.view.layoutParams as ViewGroup.MarginLayoutParams
        params.bottomMargin = bottomInset
        snackbar.view.layoutParams = params
        snackbar.show()
    }

    private fun showError(message: String) {
        binding.progressBar.isVisible = false
        binding.mealList.isVisible = false
        binding.swipeRefreshLayout.isRefreshing = false
        binding.textError.visibility = View.VISIBLE
        binding.textError.text = message
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
}