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

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(
            MealFavorite.COLUMN_ID,
            MealFavorite.COLUMN_STRMEAL,
            MealFavorite.COLUMN_STRCATEGORY,
            MealFavorite.COLUMN_STRAREA,
            MealFavorite.COLUMN_STRINSTRUCTIONS,
            MealFavorite.COLUMN_STRMEALTHUMB)

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
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_ID))
                val strMeal = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEAL))
                val strCategory = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRCATEGORY))
                val strArea = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRAREA))
                val strInstructions = cursor.getString(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRINSTRUCTIONS))
                val strMealThumb = cursor.getBlob(cursor.getColumnIndexOrThrow(MealFavorite.COLUMN_STRMEALTHUMB))
                mealFavorite = MealFavorite(id, strMeal, strCategory, strArea, strInstructions, strMealThumb)
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