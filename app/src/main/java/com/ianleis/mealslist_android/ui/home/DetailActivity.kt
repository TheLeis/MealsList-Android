package com.ianleis.mealslist_android.ui.home

import com.ianleis.mealslist_android.ui.gallery.GalleryAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil3.load
import com.google.android.material.snackbar.Snackbar
import com.ianleis.mealslist_android.R
import com.ianleis.mealslist_android.data.db.MealFavorite
import com.ianleis.mealslist_android.data.db.MealFavoriteDAO
import com.ianleis.mealslist_android.data.network.MealData
import com.ianleis.mealslist_android.data.network.MealService
import com.ianleis.mealslist_android.data.network.getIngredients
import com.ianleis.mealslist_android.databinding.ActivityDetailBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MEAL_ID = "MEAL_ID"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var meal: MealData
    private lateinit var mealFavoriteDAO: MealFavoriteDAO
    private var bottomInset: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.main)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            bottomInset = systemBars.bottom
            insets
        }
        mealFavoriteDAO = MealFavoriteDAO(this)
        val mealID = intent.getIntExtra(EXTRA_MEAL_ID, -1)
        lifecycle.addObserver(binding.youtubePlayerView)
        getMeal(mealID)
    }

    fun getMeal(id: Int) {
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
                showError(getString(R.string.error_no_internet))
            } catch (e: Exception) {
                // Handles other exceptions
                Log.e("DetailActivity", "Error fetching meal: ${e.message}")
                showError(getString(R.string.error_generic))
            }
        }
    }

    fun loadData() {
        // Text info
        binding.titleTextView.text = meal.strMeal
        binding.categoryChip.text = getString(R.string.category_and_area, meal.strCategory, meal.strArea)
        binding.textInstructions.text = meal.strInstructions
        // Meal image
        binding.mealImage.load(meal.strMealThumb)
        // Recipe video
        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                meal.strYoutube?.let {
                    if (it.isNotEmpty()) {
                        val videoId = it.substringAfter("v=").substringBefore("&")
                        youTubePlayer.cueVideo(videoId, 0f)
                    } else {
                        binding.youtubeFrame.visibility = View.GONE
                    }
                }
            }
        })
        // Ingredients
        val adapter = GalleryAdapter(meal.getIngredients())
        binding.ingredientsRecyclerView.adapter = adapter
        binding.ingredientsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        // Share button
        binding.shareButton.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.setAction(Intent.ACTION_SEND)
            sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text, meal.strMeal, meal.idMeal.toString()))
            sendIntent.setType("text/plain")
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        // Favorite button
        val isFavorite = mealFavoriteDAO.find(meal.idMeal) != null
        binding.favoriteButton.setIconResource(if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_outline)
        binding.favoriteButton.setOnClickListener {
            onFavoriteSelected(meal.idMeal)
        }
    }

    fun onFavoriteSelected(mealID: Int) {
        val isFavorite = mealFavoriteDAO.find(mealID) != null
        if (!isFavorite) {
            lifecycleScope.launch {
                try {
                    val mealData = withContext(Dispatchers.IO) {
                        MealService.getInstance().getMealById(mealID).meals[0]
                    }
                    addMealToFavorites(mealData)
                } catch (e: IOException) {
                    Log.e("MealsCategoryActivity", "Error fetching meal details: ${e.message}")
                    showSnackbar(getString(R.string.error_no_internet_favorite))
                } catch (e: Exception) {
                    Log.e("MealsCategoryActivity", "Error fetching meal details: ${e.message}")
                    showSnackbar(getString(R.string.error_generic))
                }
            }
        } else {
            mealFavoriteDAO.delete(mealID)
            binding.favoriteButton.setIconResource(R.drawable.ic_favorite_outline)
        }
    }

    fun addMealToFavorites(mealData: MealData) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val url = URL(mealData.strMealThumb)
                val thumbnail = url.readBytes()
                val mealFavorite = MealFavorite(mealData, thumbnail)
                val result = mealFavoriteDAO.insert(mealFavorite)
                withContext(Dispatchers.Main) {
                    if (result != -1L) binding.favoriteButton.setIconResource(R.drawable.ic_favorite)
                    else showSnackbar(getString(R.string.error_generic))
                }
            } catch (e: IOException) {
                Log.e("MealsCategoryActivity", "Error fetching meal details: ${e.message}")
                showSnackbar(getString(R.string.error_no_internet_favorite))
            } catch (e: Exception) {
                Log.e("MealsCategoryActivity", "Error saving favorite: ${e.message}")
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
        binding.scrollView.isVisible = false
        binding.shareButton.isVisible = false
        binding.textError.visibility = View.VISIBLE
        binding.textError.text = message
    }
}