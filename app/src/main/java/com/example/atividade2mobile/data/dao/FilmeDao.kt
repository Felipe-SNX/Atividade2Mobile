package com.example.atividade2mobile.data.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.atividade2mobile.data.db.DBHelper
import com.example.atividade2mobile.model.FilmeModel

class FilmeDao (private val context: Context){
    private val dbHelper = DBHelper(context)

    fun addFilme(filme: FilmeModel): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("name", filme.name)
            put("director", filme.director)
            put("year", filme.year)
        }
        val id = db.insert(DBHelper.TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun getAllFilmes(): List<FilmeModel>{
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null)
        val filmList = mutableListOf<FilmeModel>()
        while(cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val director = cursor.getString(cursor.getColumnIndexOrThrow("director"))
            val year = cursor.getInt(cursor.getColumnIndexOrThrow("year"))
            filmList.add(FilmeModel(id,name,director,year))
        }
        cursor.close()
        db.close()
        return filmList
    }

    fun getFilmeById(id: Int): FilmeModel? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(DBHelper.TABLE_NAME, null, "id=?", arrayOf(id.toString()), null, null, null)

        var film: FilmeModel? = null
        if(cursor.moveToFirst()){
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val director = cursor.getString(cursor.getColumnIndexOrThrow("director"))
            val year = cursor.getInt(cursor.getColumnIndexOrThrow("year"))
            film = FilmeModel(id, name, director, year)
        }
        cursor.close()
        db.close()
        return film
    }

    fun updateFilme(filme: FilmeModel): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("name", filme.name)
            put("director", filme.director)
            put("year", filme.year)
        }
        val rowsAffected = db.update(DBHelper.TABLE_NAME, values, "id=?", arrayOf(filme.id.toString()))
        db.close()
        return rowsAffected
    }

    fun deleteFilme(id: Int): Int{
        val db = dbHelper.writableDatabase
        val rowsDeleted = db.delete(DBHelper.TABLE_NAME, "id=?", arrayOf(id.toString()))
        db.close()
        return rowsDeleted
    }
}