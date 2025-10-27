package com.ianleis.mealslist_android.ui.home

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ianleis.mealslist_android.R
import com.ianleis.mealslist_android.data.db.MealFavoriteDAO
import com.ianleis.mealslist_android.data.network.MealItemFavorite
import com.ianleis.mealslist_android.databinding.ActivityMealsListBinding
import com.ianleis.mealslist_android.ui.favorites.MealFavoriteAdapter

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealsListBinding
    private lateinit var adapter : MealFavoriteAdapter
    private var filteredMealList: List<MealItemFavorite> = emptyList()
    private var originalMealList: List<MealItemFavorite> = emptyList()
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
        adapter = MealFavoriteAdapter(
            filteredMealList,
            { id -> onItemSelected(id) },
            { id -> onFavoriteSelected(id) }
        )
        binding.mealList.adapter = adapter
        binding.mealList.layoutManager = GridLayoutManager(this, 1)
        binding.textCategoryMeal.text = getString(R.string.meal_list_favorites)
        getMealList()
        if (filteredMealList.isEmpty()) {
            showError(getString(R.string.error_no_results))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.appbar_menu_favorites, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

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
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getMealList() {
        originalMealList = mealFavoriteDAO.findAll()
        filteredMealList = originalMealList
        adapter.updateItems(filteredMealList)
    }

    fun onItemSelected(mealID: Int) {
        if (isInternetAvailable()) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MEAL_ID, mealID)
            startActivity(intent)
        } else {
            val intent = Intent(this, FavoriteDetailActivity::class.java)
            intent.putExtra(FavoriteDetailActivity.EXTRA_MEAL_ID, mealID)
            startActivity(intent)
        }
    }

    fun onFavoriteSelected(mealID: Int) {
        val mealName = mealFavoriteDAO.findNameByID(mealID)
        showSnackbar(getString(R.string.meal_removed_from_favorites, mealName))
        mealFavoriteDAO.delete(mealID)
        getMealList()
    }

    private fun showSnackbar(message: String) {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
        val params = snackbar.view.layoutParams as ViewGroup.MarginLayoutParams
        params.bottomMargin = bottomInset
        snackbar.view.layoutParams = params
        snackbar.show()
    }

    private fun showError(message: String) {
        binding.textError.visibility = View.VISIBLE
        binding.textError.text = message
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}