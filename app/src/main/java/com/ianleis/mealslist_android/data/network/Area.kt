package com.ianleis.mealslist_android.data.network

import com.google.gson.annotations.SerializedName

data class Areas (
    @SerializedName("meals") val meals: List<Area>
)

data class Area (
    @SerializedName("strArea") val strArea: String,
)