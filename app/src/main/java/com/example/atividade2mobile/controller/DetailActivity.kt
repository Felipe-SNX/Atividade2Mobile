package com.example.atividade2mobile.controller

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.atividade2mobile.R
import com.example.atividade2mobile.data.dao.FilmeDao
import com.example.atividade2mobile.model.FilmeModel

class DetailActivity : AppCompatActivity() {

    private lateinit var filmeDao: FilmeDao
    private var filmeId: Int = -1

    private lateinit var edtName: EditText
    private lateinit var edtDirector: EditText
    private lateinit var edtYear: EditText
    private lateinit var btnSave: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        filmeDao = FilmeDao(this)

        filmeId = intent.getIntExtra("FILME_ID", -1)
        if (filmeId == -1) {
            Toast.makeText(this, "Filme inválido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        edtName = findViewById(R.id.edtName)
        edtDirector = findViewById(R.id.edtDirector)
        edtYear = findViewById(R.id.edtYear)
        btnSave = findViewById(R.id.btnSave)
        btnDelete = findViewById(R.id.btnDelete)

        loadFilmeDetails()

        btnSave.setOnClickListener {
            saveFilme()
        }

        btnDelete.setOnClickListener {
            deleteFilme()
        }
    }

    private fun loadFilmeDetails() {
        val filme = filmeDao.getFilmeById(filmeId)
        if (filme != null) {
            edtName.setText(filme.name)
            edtDirector.setText(filme.director)
            edtYear.setText(filme.year.toString())
        } else {
            Toast.makeText(this, "Filme não encontrado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun saveFilme() {
        val name = edtName.text.toString()
        val director = edtDirector.text.toString()
        val yearStr = edtYear.text.toString()

        if (name.isBlank() || director.isBlank() || yearStr.isBlank()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }

        val year = yearStr.toIntOrNull()
        if (year == null) {
            Toast.makeText(this, "Ano inválido", Toast.LENGTH_SHORT).show()
            return
        }

        val filme = FilmeModel(filmeId, name, director, year)
        val rows = filmeDao.updateFilme(filme)
        if (rows > 0) {
            Toast.makeText(this, "Filme atualizado", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Erro ao atualizar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteFilme() {
        val rows = filmeDao.deleteFilme(filmeId)
        if (rows > 0) {
            Toast.makeText(this, "Filme excluído", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Erro ao excluir", Toast.LENGTH_SHORT).show()
        }
    }
}
