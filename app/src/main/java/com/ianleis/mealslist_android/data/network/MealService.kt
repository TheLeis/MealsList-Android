package com.ianleis.mealslist_android.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MealService {

    @GET("categories.php")
    suspend fun getAllCategories(): Categories

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): Meals

    @GET("lookup.php")
    suspend fun getMealById(@Query("i") id: Int): MealDescription

    companion object {
        fun getInstance(): MealService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(MealService::class.java)
        }
    }
}