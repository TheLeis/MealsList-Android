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
import com.ianleis.mealslist_android.data.network.Category
import com.ianleis.mealslist_android.data.network.MealService
import com.ianleis.mealslist_android.databinding.ActivityMainBinding
import com.ianleis.mealslist_android.ui.category.CategoryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter : CategoryAdapter
    private var filteredCategoryList: List<Category> = emptyList()
    private var originalCategoryList: List<Category> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.main)
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
        adapter = CategoryAdapter(originalCategoryList)
            { category -> onItemSelected(category) }
        binding.categoryList.adapter = adapter
        binding.categoryList.layoutManager = GridLayoutManager(this, 1)
        binding.swipeRefreshLayout.setOnRefreshListener { getCategoryList() }
        getCategoryList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.appbar_menu, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean { return false }

            override fun onQueryTextChange(newText: String): Boolean {
                binding.textError.visibility = View.GONE
                filteredCategoryList = originalCategoryList.filter { it.strCategory.contains(newText, true) }
                adapter.updateItems(filteredCategoryList)
                if (filteredCategoryList.isEmpty()) showError(getString(R.string.error_no_results))
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

    fun onItemSelected(categoryName: String) {
        val intent = Intent(this, MealsListActivity::class.java)
        intent.putExtra(MealsListActivity.EXTRA_CATEGORY_NAME, categoryName)
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
                    adapter.updateItems(filteredCategoryList)
                    binding.categoryList.visibility = View.VISIBLE
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

    private fun showError(message: String) {
        binding.progressBar.isVisible = false
        binding.categoryList.visibility = View.GONE
        binding.swipeRefreshLayout.isRefreshing = false
        binding.textError.visibility = View.VISIBLE
        binding.textError.text = message
    }
}