package com.ianleis.mealslist_android.data.db.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ianleis.mealslist_android.data.db.MealFavorite

class DatabaseManager(context: Context) : SQLiteOpenHelper(context, "favoriteMeals.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(MealFavorite.SQL_CREATE_TABLE)
    }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        db.execSQL("PRAGMA foreign_keys=ON")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onDestroy(db)
        onCreate(db)
    }

    fun onDestroy(db: SQLiteDatabase) {
        db.execSQL(MealFavorite.SQL_DROP_TABLE)
    }
}