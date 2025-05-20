package com.example.atividade2mobile.data.db

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import com.example.atividade2mobile.data.dao.FilmeDao
import com.example.atividade2mobile.model.FilmeModel

class DBHelper(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null , DATABASE_VERSION){

        val FilmeDAO = FilmeDao(this)

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
            populateDatabase(database.


        }
        suspend fun populateDatabase(FilmeDao: FilmeDao) {
            // Crie os filmes que você quer adicionar
            val movie1 = FilmeModel(name = "Filme Incrível", director = "Diretor A", year = 2023)
            val movie2 =
                FilmeModel(name = "Outro Filme Fantástico", director = "Diretor B", year = 2022)

            FilmeDao.addFilme(movie1)
            FilmeDao.addFilme(movie2)
        }



        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }

}