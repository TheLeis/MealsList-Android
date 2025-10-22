package com.ianleis.mealslist_android.ui.home

import GalleryAdapter
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
import androidx.recyclerview.widget.LinearLayoutManager
import coil3.load
import com.ianleis.mealslist_android.R
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
        lifecycle.addObserver(binding.youtubePlayerView)
        getMeal(mealID)
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

    private fun showError(message: String) {
        binding.scrollView.isVisible = false
        binding.shareButton.isVisible = false
        binding.textError.visibility = View.VISIBLE
        binding.textError.text = message
    }
}