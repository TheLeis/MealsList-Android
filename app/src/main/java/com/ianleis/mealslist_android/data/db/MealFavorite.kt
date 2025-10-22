package com.ianleis.mealslist_android.data.db

data class MealFavorite (
    val id: Int,
    val strMeal: String,
    val strCategory: String,
    val strMealThumb: ByteArray,
    val strArea: String,
    val strInstructions: String,
) {
    companion object {
        const val TABLE_NAME = "MealFavorite"
        const val COLUMN_ID = "id"
        const val COLUMN_STRMEAL = "strMeal"
        const val COLUMN_STRCATEGORY = "strCategory"
        const val COLUMN_STRMEALTHUMB = "strMealThumb"
        const val COLUMN_STRAREA = "strArea"
        const val COLUMN_STRINSTRUCTIONS = "strInstructions"

        const val SQL_CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME (" +
                    "$COLUMN_ID INTEGER," +
                    "${COLUMN_STRMEAL }TEXT," +
                    "${COLUMN_STRCATEGORY}TEXT," +
                    "${COLUMN_STRMEALTHUMB}BLOB," +
                    "${COLUMN_STRAREA}TEXT," +
                    "${COLUMN_STRINSTRUCTIONS}TEXT)"

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
