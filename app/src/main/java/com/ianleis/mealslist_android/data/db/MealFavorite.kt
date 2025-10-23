package com.ianleis.mealslist_android.data.db

import com.ianleis.mealslist_android.data.network.MealData

data class MealFavorite (
    val id: Int,
    val strMeal: String,
    val strCategory: String,
    val strArea: String,
    val strInstructions: String,
    val strMealThumb: ByteArray
) {
    constructor(mealData: MealData, mealThumb: ByteArray) : this(
        id = mealData.idMeal,
        strMeal = mealData.strMeal,
        strCategory = mealData.strCategory,
        strArea = mealData.strArea,
        strInstructions = mealData.strInstructions,
        strMealThumb = mealThumb
    )

    companion object {
        const val TABLE_NAME = "MealFavorite"
        const val COLUMN_ID = "id"
        const val COLUMN_STRMEAL = "strMeal"
        const val COLUMN_STRCATEGORY = "strCategory"
        const val COLUMN_STRAREA = "strArea"
        const val COLUMN_STRINSTRUCTIONS = "strInstructions"
        const val COLUMN_STRMEALTHUMB = "strMealThumb"

        const val SQL_CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME (" +
                    "$COLUMN_ID INTEGER UNIQUE," +
                    "$COLUMN_STRMEAL TEXT," +
                    "$COLUMN_STRCATEGORY TEXT," +
                    "$COLUMN_STRAREA TEXT," +
                    "$COLUMN_STRINSTRUCTIONS TEXT," +
                    "$COLUMN_STRMEALTHUMB BLOB)"

        const val SQL_DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MealFavorite

        if (id != other.id) return false
        if (strMeal != other.strMeal) return false
        if (strCategory != other.strCategory) return false
        if (!strMealThumb.contentEquals(other.strMealThumb)) return false
        if (strArea != other.strArea) return false
        if (strInstructions != other.strInstructions) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + strMeal.hashCode()
        result = 31 * result + strCategory.hashCode()
        result = 31 * result + strMealThumb.contentHashCode()
        result = 31 * result + strArea.hashCode()
        result = 31 * result + strInstructions.hashCode()
        return result
    }
}