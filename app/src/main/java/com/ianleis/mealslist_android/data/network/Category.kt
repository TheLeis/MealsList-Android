package com.ianleis.mealslist_android.data.network

import com.google.gson.annotations.SerializedName

data class Categories (
    @SerializedName("categories") val categories: List<Category>
)

data class Category (
    @SerializedName("strCategory") val strCategory: String,
    @SerializedName("strCategoryThumb") val strCategoryThumb: String,
    @SerializedName("strCategoryDescription") val strCategoryDescription: String
)