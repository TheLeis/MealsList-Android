package com.ianleis.mealslist_android.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.ianleis.mealslist_android.data.db.utils.DatabaseManager

class CategoryDAO(val context: Context) {

    private lateinit var db: SQLiteDatabase

    private fun open() { db = DatabaseManager(context).writableDatabase }

    private fun close() { db.close() }
}