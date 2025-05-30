package com.example.atividade2mobile.controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.atividade2mobile.R
import com.example.atividade2mobile.data.dao.FilmeDao
import com.example.atividade2mobile.model.FilmeModel


class MainActivity : AppCompatActivity() {

    private lateinit var filmeDAO: FilmeDao
    private lateinit var listView: ListView
    private lateinit var emptyTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.lvFilmes)
        emptyTextView = findViewById(R.id.tvEmpty)
        filmeDAO = FilmeDao(this)

        listAllFilmes()

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedFilme = parent.getItemAtPosition(position) as FilmeModel
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("filmeID", selectedFilme.id)
            }
            startActivity(intent)
        }
    }

    private fun listAllFilmes(){
        val filmeModel = filmeDAO.getAllFilmes()
        if(filmeModel.isEmpty()){
            listView.visibility = ListView.GONE
            emptyTextView.visibility = TextView.VISIBLE
        }
        else {
            listView.visibility = ListView.VISIBLE
            emptyTextView.visibility = TextView.GONE
            val adapter: ArrayAdapter<FilmeModel> = ArrayAdapter(
                this, android.R.layout.simple_list_item_1, filmeModel
            )
            listView.adapter = adapter
        }
    }

    fun newFilme(view: View){
        val intent = Intent(this, DetailActivity::class.java)
        startActivity(intent)
    }

    fun pesquisarFilme(view: View){
        val intent = Intent(this, QueryActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        listAllFilmes()
    }
}