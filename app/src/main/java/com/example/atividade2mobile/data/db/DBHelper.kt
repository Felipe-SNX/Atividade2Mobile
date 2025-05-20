package com.example.atividade2mobile.data.db

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context

class DBHelper(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null , DATABASE_VERSION){

        companion object{
            const val DATABASE_NAME = "filmes.db"
            const val DATABASE_VERSION = 1
            const val TABLE_NAME = "filmes"
        }

        override fun onCreate(db: SQLiteDatabase?) {
            val createTable = """
            CREATE TABLE $TABLE_NAME (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                director TEXT NOT NULL,
                year INTEGER NOT NULL
            )
        """.trimIndent()
            db?.execSQL(createTable)

            val values1 = android.content.ContentValues().apply {
                put("name", "Duro de Matar")
                put("director", "John McTiernan")
                put("year", 1988)
            }
            db?.insert(TABLE_NAME, null, values1)

            val values2 = android.content.ContentValues().apply {
                put("name", "Exterminador do Futuro")
                put("director", "James Cameron")
                put("year", 1985)
            }
            db?.insert(TABLE_NAME, null, values2)

        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }

}