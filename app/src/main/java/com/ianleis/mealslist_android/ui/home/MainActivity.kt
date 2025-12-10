package com.ianleis.mealslist_android.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.ianleis.mealslist_android.R
import com.ianleis.mealslist_android.data.SettingsKeys
import com.ianleis.mealslist_android.data.dataStore
import com.ianleis.mealslist_android.data.network.Area
import com.ianleis.mealslist_android.data.network.Category
import com.ianleis.mealslist_android.data.network.MealService
import com.ianleis.mealslist_android.databinding.ActivityMainBinding
import com.ianleis.mealslist_android.ui.area.AreaAdapter
import com.ianleis.mealslist_android.ui.category.CategoryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var categoryAdapter : CategoryAdapter
    private lateinit var areaAdapter : AreaAdapter
    private var filteredCategoryList: List<Category> = emptyList()
    private var originalCategoryList: List<Category> = emptyList()
    private var filteredAreaList: List<Area> = emptyList()
    private var originalAreaList: List<Area> = emptyList()

    private enum class FilterType {
        CATEGORY, AREA
    }

    private var currentFilter: FilterType = FilterType.CATEGORY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.main)
        setSupportActionBar(binding.toolbar)
        // Set app to dark mode if the user has selected it in the settings
        lifecycleScope.launch {
            val isDarkMode = dataStore.data.map { preferences ->
                preferences[SettingsKeys.DARK_MODE_KEY]
            }.first()
            if (isDarkMode != null) {
                if (isDarkMode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) }
            delegate.applyDayNight()
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        categoryAdapter = CategoryAdapter(originalCategoryList)
            { category -> onCategorySelected(category) }
        areaAdapter = AreaAdapter(originalAreaList)
        { area -> onAreaSelected(area) }
        binding.recyclerView.adapter = categoryAdapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 1)
        binding.swipeRefreshLayout.setOnRefreshListener {
            when (currentFilter) {
                FilterType.CATEGORY -> getCategoryList()
                FilterType.AREA -> getAreaList()
            }
        }
        getCategoryList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.appbar_menu_main, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean { return false }

            override fun onQueryTextChange(newText: String): Boolean {
                binding.textError.visibility = View.GONE
                when (currentFilter) {
                    FilterType.CATEGORY -> {
                        filteredCategoryList =
                            originalCategoryList.filter { it.strCategory.contains(newText, true) }
                        categoryAdapter.updateItems(filteredCategoryList)
                        if (filteredCategoryList.isEmpty()) showError(getString(R.string.error_no_results))
                        else binding.recyclerView.isVisible = true
                    }

                    FilterType.AREA -> {
                        filteredAreaList =
                            originalAreaList.filter { it.strArea.contains(newText, true) }
                        areaAdapter.updateItems(filteredAreaList)
                        if (filteredAreaList.isEmpty()) showError(getString(R.string.error_no_results))
                        else binding.recyclerView.isVisible = true
                    }
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
            R.id.action_favorites -> {
                val intent = Intent(this, FavoritesActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_filter_by_category -> {
                currentFilter = FilterType.CATEGORY
                binding.textList.text = getString(R.string.category_list)
                binding.recyclerView.adapter = categoryAdapter
                getCategoryList()
                true
            }
            R.id.action_filter_by_area -> {
                currentFilter = FilterType.AREA
                binding.textList.text = getString(R.string.area_list)
                binding.recyclerView.adapter = areaAdapter
                getAreaList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun onCategorySelected(categoryName: String) {
        val intent = Intent(this, MealsListActivity::class.java)
        intent.putExtra(MealsListActivity.EXTRA_CATEGORY_NAME, categoryName)
        startActivity(intent)
    }

    fun onAreaSelected(areaName: String) {
        val intent = Intent(this, MealsListActivity::class.java)
        intent.putExtra(MealsListActivity.EXTRA_AREA_NAME, areaName)
        startActivity(intent)
    }

    private fun getCategoryList() {
        binding.textError.visibility = View.GONE
        if (!binding.swipeRefreshLayout.isRefreshing) binding.progressBar.isVisible = true
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val service = MealService.getInstance()
                val categories = service.getAllCategories().categories
                originalCategoryList = categories
                filteredCategoryList = categories
                withContext(Dispatchers.Main) {
                    categoryAdapter.updateItems(filteredCategoryList)
                    binding.recyclerView.visibility = View.VISIBLE
                }
            } catch (e: IOException) {
                // Handles IO exceptions, like network errors
                Log.e("MainActivity", "Error fetching categories: ${e.message}")
                withContext(Dispatchers.Main) { showError(getString(R.string.error_no_internet)) }
            } catch (e: Exception) {
                // Handles other exceptions
                Log.e("MainActivity", "Error fetching categories: ${e.message}")
                withContext(Dispatchers.Main) { showError(getString(R.string.error_generic)) }
            } finally {
                withContext(Dispatchers.Main) {
                    binding.progressBar.isVisible = false
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        }
    }

    private fun getAreaList() {
        binding.textError.visibility = View.GONE
        if (!binding.swipeRefreshLayout.isRefreshing) binding.progressBar.isVisible = true
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val service = MealService.getInstance()
                val areas = service.getAllAreas().meals
                originalAreaList = areas
                filteredAreaList = areas
                withContext(Dispatchers.Main) {
                    areaAdapter.updateItems(filteredAreaList)
                    binding.recyclerView.visibility = View.VISIBLE
                }
            } catch (e: IOException) {
                Log.e("MainActivity", "Error fetching areas: ${e.message}")
                withContext(Dispatchers.Main) { showError(getString(R.string.error_no_internet)) }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error fetching areas: ${e.message}")
                withContext(Dispatchers.Main) { showError(getString(R.string.error_generic)) }
            } finally {
                withContext(Dispatchers.Main) {
                    binding.progressBar.isVisible = false
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        }
    }

    private fun showError(message: String) {
        binding.progressBar.isVisible = false
        binding.recyclerView.isVisible = false
        binding.swipeRefreshLayout.isRefreshing = false
        binding.textError.visibility = View.VISIBLE
        binding.textError.text = message
    }
}