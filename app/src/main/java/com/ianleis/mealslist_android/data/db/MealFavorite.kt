package com.ianleis.mealslist_android.data.db

import com.ianleis.mealslist_android.data.network.MealData

data class MealFavorite (
    val id: Int,
    val strMeal: String,
    val strCategory: String,
    val strArea: String,
    val strInstructions: String,
    val strMealThumb: ByteArray,
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: String?,
    val strIngredient9: String?,
    val strIngredient10: String?,
    val strIngredient11: String?,
    val strIngredient12: String?,
    val strIngredient13: String?,
    val strIngredient14: String?,
    val strIngredient15: String?,
    val strIngredient16: String?,
    val strIngredient17: String?,
    val strIngredient18: String?,
    val strIngredient19: String?,
    val strIngredient20: String?,
    val strMeasure1: String?,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?,
    val strMeasure6: String?,
    val strMeasure7: String?,
    val strMeasure8: String?,
    val strMeasure9: String?,
    val strMeasure10: String?,
    val strMeasure11: String?,
    val strMeasure12: String?,
    val strMeasure13: String?,
    val strMeasure14: String?,
    val strMeasure15: String?,
    val strMeasure16: String?,
    val strMeasure17: String?,
    val strMeasure18: String?,
    val strMeasure19: String?,
    val strMeasure20: String?
) {
    constructor(mealData: MealData, mealThumb: ByteArray) : this(
        id = mealData.idMeal,
        strMeal = mealData.strMeal,
        strCategory = mealData.strCategory,
        strArea = mealData.strArea,
        strInstructions = mealData.strInstructions,
        strMealThumb = mealThumb,
        strIngredient1 = mealData.strIngredient1,
        strIngredient2 = mealData.strIngredient2,
        strIngredient3 = mealData.strIngredient3,
        strIngredient4 = mealData.strIngredient4,
        strIngredient5 = mealData.strIngredient5,
        strIngredient6 = mealData.strIngredient6,
        strIngredient7 = mealData.strIngredient7,
        strIngredient8 = mealData.strIngredient8,
        strIngredient9 = mealData.strIngredient9,
        strIngredient10 = mealData.strIngredient10,
        strIngredient11 = mealData.strIngredient11,
        strIngredient12 = mealData.strIngredient12,
        strIngredient13 = mealData.strIngredient13,
        strIngredient14 = mealData.strIngredient14,
        strIngredient15 = mealData.strIngredient15,
        strIngredient16 = mealData.strIngredient16,
        strIngredient17 = mealData.strIngredient17,
        strIngredient18 = mealData.strIngredient18,
        strIngredient19 = mealData.strIngredient19,
        strIngredient20 = mealData.strIngredient20,
        strMeasure1 = mealData.strMeasure1,
        strMeasure2 = mealData.strMeasure2,
        strMeasure3 = mealData.strMeasure3,
        strMeasure4 = mealData.strMeasure4,
        strMeasure5 = mealData.strMeasure5,
        strMeasure6 = mealData.strMeasure6,
        strMeasure7 = mealData.strMeasure7,
        strMeasure8 = mealData.strMeasure8,
        strMeasure9 = mealData.strMeasure9,
        strMeasure10 = mealData.strMeasure10,
        strMeasure11 = mealData.strMeasure11,
        strMeasure12 = mealData.strMeasure12,
        strMeasure13 = mealData.strMeasure13,
        strMeasure14 = mealData.strMeasure14,
        strMeasure15 = mealData.strMeasure15,
        strMeasure16 = mealData.strMeasure16,
        strMeasure17 = mealData.strMeasure17,
        strMeasure18 = mealData.strMeasure18,
        strMeasure19 = mealData.strMeasure19,
        strMeasure20 = mealData.strMeasure20
    )

    companion object {
        const val TABLE_NAME = "MealFavorite"
        const val COLUMN_ID = "id"
        const val COLUMN_STRMEAL = "strMeal"
        const val COLUMN_STRCATEGORY = "strCategory"
        const val COLUMN_STRAREA = "strArea"
        const val COLUMN_STRINSTRUCTIONS = "strInstructions"
        const val COLUMN_STRMEALTHUMB = "strMealThumb"
        const val COLUMN_STRINGREDIENT1 = "strIngredient1"
        const val COLUMN_STRINGREDIENT2 = "strIngredient2"
        const val COLUMN_STRINGREDIENT3 = "strIngredient3"
        const val COLUMN_STRINGREDIENT4 = "strIngredient4"
        const val COLUMN_STRINGREDIENT5 = "strIngredient5"
        const val COLUMN_STRINGREDIENT6 = "strIngredient6"
        const val COLUMN_STRINGREDIENT7 = "strIngredient7"
        const val COLUMN_STRINGREDIENT8 = "strIngredient8"
        const val COLUMN_STRINGREDIENT9 = "strIngredient9"
        const val COLUMN_STRINGREDIENT10 = "strIngredient10"
        const val COLUMN_STRINGREDIENT11 = "strIngredient11"
        const val COLUMN_STRINGREDIENT12 = "strIngredient12"
        const val COLUMN_STRINGREDIENT13 = "strIngredient13"
        const val COLUMN_STRINGREDIENT14 = "strIngredient14"
        const val COLUMN_STRINGREDIENT15 = "strIngredient15"
        const val COLUMN_STRINGREDIENT16 = "strIngredient16"
        const val COLUMN_STRINGREDIENT17 = "strIngredient17"
        const val COLUMN_STRINGREDIENT18 = "strIngredient18"
        const val COLUMN_STRINGREDIENT19 = "strIngredient19"
        const val COLUMN_STRINGREDIENT20 = "strIngredient20"
        const val COLUMN_STRMEASURE1 = "strMeasure1"
        const val COLUMN_STRMEASURE2 = "strMeasure2"
        const val COLUMN_STRMEASURE3 = "strMeasure3"
        const val COLUMN_STRMEASURE4 = "strMeasure4"
        const val COLUMN_STRMEASURE5 = "strMeasure5"
        const val COLUMN_STRMEASURE6 = "strMeasure6"
        const val COLUMN_STRMEASURE7 = "strMeasure7"
        const val COLUMN_STRMEASURE8 = "strMeasure8"
        const val COLUMN_STRMEASURE9 = "strMeasure9"
        const val COLUMN_STRMEASURE10 = "strMeasure10"
        const val COLUMN_STRMEASURE11 = "strMeasure11"
        const val COLUMN_STRMEASURE12 = "strMeasure12"
        const val COLUMN_STRMEASURE13 = "strMeasure13"
        const val COLUMN_STRMEASURE14 = "strMeasure14"
        const val COLUMN_STRMEASURE15 = "strMeasure15"
        const val COLUMN_STRMEASURE16 = "strMeasure16"
        const val COLUMN_STRMEASURE17 = "strMeasure17"
        const val COLUMN_STRMEASURE18 = "strMeasure18"
        const val COLUMN_STRMEASURE19 = "strMeasure19"
        const val COLUMN_STRMEASURE20 = "strMeasure20"

        const val SQL_CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME (" +
                    "$COLUMN_ID INTEGER UNIQUE," +
                    "$COLUMN_STRMEAL TEXT," +
                    "$COLUMN_STRCATEGORY TEXT," +
                    "$COLUMN_STRAREA TEXT," +
                    "$COLUMN_STRINSTRUCTIONS TEXT," +
                    "$COLUMN_STRMEALTHUMB BLOB," +
                    "$COLUMN_STRINGREDIENT1 TEXT," +
                    "$COLUMN_STRINGREDIENT2 TEXT," +
                    "$COLUMN_STRINGREDIENT3 TEXT," +
                    "$COLUMN_STRINGREDIENT4 TEXT," +
                    "$COLUMN_STRINGREDIENT5 TEXT," +
                    "$COLUMN_STRINGREDIENT6 TEXT," +
                    "$COLUMN_STRINGREDIENT7 TEXT," +
                    "$COLUMN_STRINGREDIENT8 TEXT," +
                    "$COLUMN_STRINGREDIENT9 TEXT," +
                    "$COLUMN_STRINGREDIENT10 TEXT," +
                    "$COLUMN_STRINGREDIENT11 TEXT," +
                    "$COLUMN_STRINGREDIENT12 TEXT," +
                    "$COLUMN_STRINGREDIENT13 TEXT," +
                    "$COLUMN_STRINGREDIENT14 TEXT," +
                    "$COLUMN_STRINGREDIENT15 TEXT," +
                    "$COLUMN_STRINGREDIENT16 TEXT," +
                    "$COLUMN_STRINGREDIENT17 TEXT," +
                    "$COLUMN_STRINGREDIENT18 TEXT," +
                    "$COLUMN_STRINGREDIENT19 TEXT," +
                    "$COLUMN_STRINGREDIENT20 TEXT," +
                    "$COLUMN_STRMEASURE1 TEXT," +
                    "$COLUMN_STRMEASURE2 TEXT," +
                    "$COLUMN_STRMEASURE3 TEXT," +
                    "$COLUMN_STRMEASURE4 TEXT," +
                    "$COLUMN_STRMEASURE5 TEXT," +
                    "$COLUMN_STRMEASURE6 TEXT," +
                    "$COLUMN_STRMEASURE7 TEXT," +
                    "$COLUMN_STRMEASURE8 TEXT," +
                    "$COLUMN_STRMEASURE9 TEXT," +
                    "$COLUMN_STRMEASURE10 TEXT," +
                    "$COLUMN_STRMEASURE11 TEXT," +
                    "$COLUMN_STRMEASURE12 TEXT," +
                    "$COLUMN_STRMEASURE13 TEXT," +
                    "$COLUMN_STRMEASURE14 TEXT," +
                    "$COLUMN_STRMEASURE15 TEXT," +
                    "$COLUMN_STRMEASURE16 TEXT," +
                    "$COLUMN_STRMEASURE17 TEXT," +
                    "$COLUMN_STRMEASURE18 TEXT," +
                    "$COLUMN_STRMEASURE19 TEXT," +
                    "$COLUMN_STRMEASURE20 TEXT)"

        const val SQL_DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MealFavorite

        if (id != other.id) return false
        if (strMeal != other.strMeal) return false
        if (strCategory != other.strCategory) return false
        if (strArea != other.strArea) return false
        if (strInstructions != other.strInstructions) return false
        if (!strMealThumb.contentEquals(other.strMealThumb)) return false
        if (strIngredient1 != other.strIngredient1) return false
        if (strIngredient2 != other.strIngredient2) return false
        if (strIngredient3 != other.strIngredient3) return false
        if (strIngredient4 != other.strIngredient4) return false
        if (strIngredient5 != other.strIngredient5) return false
        if (strIngredient6 != other.strIngredient6) return false
        if (strIngredient7 != other.strIngredient7) return false
        if (strIngredient8 != other.strIngredient8) return false
        if (strIngredient9 != other.strIngredient9) return false
        if (strIngredient10 != other.strIngredient10) return false
        if (strIngredient11 != other.strIngredient11) return false
        if (strIngredient12 != other.strIngredient12) return false
        if (strIngredient13 != other.strIngredient13) return false
        if (strIngredient14 != other.strIngredient14) return false
        if (strIngredient15 != other.strIngredient15) return false
        if (strIngredient16 != other.strIngredient16) return false
        if (strIngredient17 != other.strIngredient17) return false
        if (strIngredient18 != other.strIngredient18) return false
        if (strIngredient19 != other.strIngredient19) return false
        if (strIngredient20 != other.strIngredient20) return false
        if (strMeasure1 != other.strMeasure1) return false
        if (strMeasure2 != other.strMeasure2) return false
        if (strMeasure3 != other.strMeasure3) return false
        if (strMeasure4 != other.strMeasure4) return false
        if (strMeasure5 != other.strMeasure5) return false
        if (strMeasure6 != other.strMeasure6) return false
        if (strMeasure7 != other.strMeasure7) return false
        if (strMeasure8 != other.strMeasure8) return false
        if (strMeasure9 != other.strMeasure9) return false
        if (strMeasure10 != other.strMeasure10) return false
        if (strMeasure11 != other.strMeasure11) return false
        if (strMeasure12 != other.strMeasure12) return false
        if (strMeasure13 != other.strMeasure13) return false
        if (strMeasure14 != other.strMeasure14) return false
        if (strMeasure15 != other.strMeasure15) return false
        if (strMeasure16 != other.strMeasure16) return false
        if (strMeasure17 != other.strMeasure17) return false
        if (strMeasure18 != other.strMeasure18) return false
        if (strMeasure19 != other.strMeasure19) return false
        if (strMeasure20 != other.strMeasure20) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + strMeal.hashCode()
        result = 31 * result + strCategory.hashCode()
        result = 31 * result + strArea.hashCode()
        result = 31 * result + strInstructions.hashCode()
        result = 31 * result + strMealThumb.contentHashCode()
        result = 31 * result + (strIngredient1?.hashCode() ?: 0)
        result = 31 * result + (strIngredient2?.hashCode() ?: 0)
        result = 31 * result + (strIngredient3?.hashCode() ?: 0)
        result = 31 * result + (strIngredient4?.hashCode() ?: 0)
        result = 31 * result + (strIngredient5?.hashCode() ?: 0)
        result = 31 * result + (strIngredient6?.hashCode() ?: 0)
        result = 31 * result + (strIngredient7?.hashCode() ?: 0)
        result = 31 * result + (strIngredient8?.hashCode() ?: 0)
        result = 31 * result + (strIngredient9?.hashCode() ?: 0)
        result = 31 * result + (strIngredient10?.hashCode() ?: 0)
        result = 31 * result + (strIngredient11?.hashCode() ?: 0)
        result = 31 * result + (strIngredient12?.hashCode() ?: 0)
        result = 31 * result + (strIngredient13?.hashCode() ?: 0)
        result = 31 * result + (strIngredient14?.hashCode() ?: 0)
        result = 31 * result + (strIngredient15?.hashCode() ?: 0)
        result = 31 * result + (strIngredient16?.hashCode() ?: 0)
        result = 31 * result + (strIngredient17?.hashCode() ?: 0)
        result = 31 * result + (strIngredient18?.hashCode() ?: 0)
        result = 31 * result + (strIngredient19?.hashCode() ?: 0)
        result = 31 * result + (strIngredient20?.hashCode() ?: 0)
        result = 31 * result + (strMeasure1?.hashCode() ?: 0)
        result = 31 * result + (strMeasure2?.hashCode() ?: 0)
        result = 31 * result + (strMeasure3?.hashCode() ?: 0)
        result = 31 * result + (strMeasure4?.hashCode() ?: 0)
        result = 31 * result + (strMeasure5?.hashCode() ?: 0)
        result = 31 * result + (strMeasure6?.hashCode() ?: 0)
        result = 31 * result + (strMeasure7?.hashCode() ?: 0)
        result = 31 * result + (strMeasure8?.hashCode() ?: 0)
        result = 31 * result + (strMeasure9?.hashCode() ?: 0)
        result = 31 * result + (strMeasure10?.hashCode() ?: 0)
        result = 31 * result + (strMeasure11?.hashCode() ?: 0)
        result = 31 * result + (strMeasure12?.hashCode() ?: 0)
        result = 31 * result + (strMeasure13?.hashCode() ?: 0)
        result = 31 * result + (strMeasure14?.hashCode() ?: 0)
        result = 31 * result + (strMeasure15?.hashCode() ?: 0)
        result = 31 * result + (strMeasure16?.hashCode() ?: 0)
        result = 31 * result + (strMeasure17?.hashCode() ?: 0)
        result = 31 * result + (strMeasure18?.hashCode() ?: 0)
        result = 31 * result + (strMeasure19?.hashCode() ?: 0)
        result = 31 * result + (strMeasure20?.hashCode() ?: 0)
        return result
    }
}