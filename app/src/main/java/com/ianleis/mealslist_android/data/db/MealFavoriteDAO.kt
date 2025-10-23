package com.ianleis.mealslist_android.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.ianleis.mealslist_android.data.db.utils.DatabaseManager
import com.ianleis.mealslist_android.data.network.MealItemFavorite

class MealFavoriteDAO(val context: Context) {

    private lateinit var db: SQLiteDatabase

    private fun open() { db = DatabaseManager(context).writableDatabase }

    private fun close() { db.close() }

    fun insert(mealFavorite: MealFavorite): Long {
        var newRowId: Long = -1
        val values = ContentValues().apply {
            put(MealFavorite.COLUMN_ID, mealFavorite.id)
            put(MealFavorite.COLUMN_STRMEAL, mealFavorite.strMeal)
            put(MealFavorite.COLUMN_STRCATEGORY, mealFavorite.strCategory)
            put(MealFavorite.COLUMN_STRAREA, mealFavorite.strArea)
            put(MealFavorite.COLUMN_STRINSTRUCTIONS, mealFavorite.strInstructions)
            put(MealFavorite.COLUMN_STRMEALTHUMB, mealFavorite.strMealThumb)
            put(MealFavorite.COLUMN_STRINGREDIENT1, mealFavorite.strIngredient1)
            put(MealFavorite.COLUMN_STRINGREDIENT2, mealFavorite.strIngredient2)
            put(MealFavorite.COLUMN_STRINGREDIENT3, mealFavorite.strIngredient3)
            put(MealFavorite.COLUMN_STRINGREDIENT4, mealFavorite.strIngredient4)
            put(MealFavorite.COLUMN_STRINGREDIENT5, mealFavorite.strIngredient5)
            put(MealFavorite.COLUMN_STRINGREDIENT6, mealFavorite.strIngredient6)
            put(MealFavorite.COLUMN_STRINGREDIENT7, mealFavorite.strIngredient7)
            put(MealFavorite.COLUMN_STRINGREDIENT8, mealFavorite.strIngredient8)
            put(MealFavorite.COLUMN_STRINGREDIENT9, mealFavorite.strIngredient9)
            put(MealFavorite.COLUMN_STRINGREDIENT10, mealFavorite.strIngredient10)
            put(MealFavorite.COLUMN_STRINGREDIENT11, mealFavorite.strIngredient11)
            put(MealFavorite.COLUMN_STRINGREDIENT12, mealFavorite.strIngredient12)
            put(MealFavorite.COLUMN_STRINGREDIENT13, mealFavorite.strIngredient13)
            put(MealFavorite.COLUMN_STRINGREDIENT14, mealFavorite.strIngredient14)
            put(MealFavorite.COLUMN_STRINGREDIENT15, mealFavorite.strIngredient15)
            put(MealFavorite.COLUMN_STRINGREDIENT16, mealFavorite.strIngredient16)
            put(MealFavorite.COLUMN_STRINGREDIENT17, mealFavorite.strIngredient17)
            put(MealFavorite.COLUMN_STRINGREDIENT18, mealFavorite.strIngredient18)
            put(MealFavorite.COLUMN_STRINGREDIENT19, mealFavorite.strIngredient19)
            put(MealFavorite.COLUMN_STRINGREDIENT20, mealFavorite.strIngredient20)
            put(MealFavorite.COLUMN_STRMEASURE1, mealFavorite.strMeasure1)
            put(MealFavorite.COLUMN_STRMEASURE2, mealFavorite.strMeasure2)
            put(MealFavorite.COLUMN_STRMEASURE3, mealFavorite.strMeasure3)
            put(MealFavorite.COLUMN_STRMEASURE4, mealFavorite.strMeasure4)
            put(MealFavorite.COLUMN_STRMEASURE5, mealFavorite.strMeasure5)
            put(MealFavorite.COLUMN_STRMEASURE6, mealFavorite.strMeasure6)
            put(MealFavorite.COLUMN_STRMEASURE7, mealFavorite.strMeasure7)
            put(MealFavorite.COLUMN_STRMEASURE8, mealFavorite.strMeasure8)
            put(MealFavorite.COLUMN_STRMEASURE9, mealFavorite.strMeasure9)
            put(MealFavorite.COLUMN_STRMEASURE10, mealFavorite.strMeasure10)
            put(MealFavorite.COLUMN_STRMEASURE11, mealFavorite.strMeasure11)
            put(MealFavorite.COLUMN_STRMEASURE12, mealFavorite.strMeasure12)
            put(MealFavorite.COLUMN_STRMEASURE13, mealFavorite.strMeasure13)
            put(MealFavorite.COLUMN_STRMEASURE14, mealFavorite.strMeasure14)
            put(MealFavorite.COLUMN_STRMEASURE15, mealFavorite.strMeasure15)
            put(MealFavorite.COLUMN_STRMEASURE16, mealFavorite.strMeasure16)
            put(MealFavorite.COLUMN_STRMEASURE17, mealFavorite.strMeasure17)
            put(MealFavorite.COLUMN_STRMEASURE18, mealFavorite.strMeasure18)
            put(MealFavorite.COLUMN_STRMEASURE19, mealFavorite.strMeasure19)
            put(MealFavorite.COLUMN_STRMEASURE20, mealFavorite.strMeasure20)
        }

        try {
            open()
            newRowId = db.insert(MealFavorite.TABLE_NAME, null, values)
            Log.i("DATABASE", "New row inserted in table ${MealFavorite.TABLE_NAME} with id: $newRowId")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
        }

        return newRowId
    }

    fun delete(id: Int) {
        try {
            open()

            // Insert the new row, returning the primary key value of the new row
            val deletedRows = db.delete(MealFavorite.TABLE_NAME, "${MealFavorite.COLUMN_ID} = $id", null)
            Log.i("DATABASE", "$deletedRows rows deleted in table ${MealFavorite.TABLE_NAME}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
        }
    }

    fun find(id: Int) : MealFavorite? {
        var mealFavorite: MealFavorite? = null
        var cursor: Cursor? = null

        // Filter results WHERE "title" = 'My Title'
        val selection = "${MealFavorite.COLUMN_ID} = $id"
        val selectionArgs = null

        // How you want the results sorted in the resulting Cursor
        val sortOrder = null

        try {
            open()

            cursor = db.query(
                MealFavorite.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
            )

            // Read the cursor data
            if (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_ID))
                val strMeal = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEAL))
                val strCategory = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRCATEGORY))
                val strArea = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRAREA))
                val strInstructions = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRINSTRUCTIONS))
                val strMealThumb = cursor.getBlob(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEALTHUMB))
                val strIngredient1 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRINGREDIENT1))
                val strIngredient2 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRINGREDIENT2))
                val strIngredient3 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRINGREDIENT3))
                val strIngredient4 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRINGREDIENT4))
                val strIngredient5 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRINGREDIENT5))
                val strIngredient6 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRINGREDIENT6))
                val strIngredient7 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRINGREDIENT7))
                val strIngredient8 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRINGREDIENT8))
                val strIngredient9 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRINGREDIENT9))
                val strIngredient10 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRINGREDIENT10))
                val strIngredient11 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRINGREDIENT11))
                val strIngredient12 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRINGREDIENT12))
                val strIngredient13 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRINGREDIENT13))
                val strIngredient14 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRINGREDIENT14))
                val strIngredient15 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRINGREDIENT15))
                val strIngredient16 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRINGREDIENT16))
                val strIngredient17 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRINGREDIENT17))
                val strIngredient18 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRINGREDIENT18))
                val strIngredient19 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRINGREDIENT19))
                val strIngredient20 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRINGREDIENT20))
                val strMeasure1 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEASURE1))
                val strMeasure2 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEASURE2))
                val strMeasure3 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEASURE3))
                val strMeasure4 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEASURE4))
                val strMeasure5 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEASURE5))
                val strMeasure6 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEASURE6))
                val strMeasure7 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEASURE7))
                val strMeasure8 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEASURE8))
                val strMeasure9 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEASURE9))
                val strMeasure10 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEASURE10))
                val strMeasure11 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEASURE11))
                val strMeasure12 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEASURE12))
                val strMeasure13 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEASURE13))
                val strMeasure14 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEASURE14))
                val strMeasure15 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEASURE15))
                val strMeasure16 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEASURE16))
                val strMeasure17 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEASURE17))
                val strMeasure18 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEASURE18))
                val strMeasure19 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEASURE19))
                val strMeasure20 = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEASURE20))
                mealFavorite = MealFavorite(id, strMeal, strCategory, strArea, strInstructions, strMealThumb,
                    strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5, strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10,
                    strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15, strIngredient16, strIngredient17, strIngredient18, strIngredient19, strIngredient20,
                    strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5, strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10,
                    strMeasure11, strMeasure12, strMeasure13, strMeasure14, strMeasure15, strMeasure16, strMeasure17, strMeasure18, strMeasure19, strMeasure20)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
            close()
        }

        return mealFavorite
    }

    fun findNameByID(id: Int) : String? {
        var name: String? = null
        var cursor: Cursor? = null

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(MealFavorite.COLUMN_STRMEAL)

        // Filter results WHERE "title" = 'My Title'
        val selection = "${MealFavorite.COLUMN_ID} = $id"
        val selectionArgs = null

        // How you want the results sorted in the resulting Cursor
        val sortOrder = null

        try {
            open()

            cursor = db.query(
                MealFavorite.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
            )

            // Read the cursor data
            if (cursor.moveToNext()) {
                val strMeal = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEAL))
                name = strMeal
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
            close()
        }

        return name
    }

    fun findAll() : List<MealItemFavorite> {
        val items: MutableList<MealItemFavorite> = mutableListOf()
        var cursor: Cursor? = null

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(
            MealFavorite.COLUMN_ID,
            MealFavorite.COLUMN_STRMEAL,
            MealFavorite.COLUMN_STRMEALTHUMB)

        // Filter results WHERE "title" = 'My Title'
        val selection = null
        val selectionArgs = null

        // How you want the results sorted in the resulting Cursor
        val sortOrder = null

        try {
            open()

            cursor = db.query(
                MealFavorite.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
            )

            // Read the cursor data
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_ID))
                val strMeal = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEAL))
                val strMealThumb = cursor.getBlob(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEALTHUMB))
                val mealFavorite = MealItemFavorite(id, strMeal, strMealThumb)
                items.add(mealFavorite)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
            close()
        }

        return items
    }
}