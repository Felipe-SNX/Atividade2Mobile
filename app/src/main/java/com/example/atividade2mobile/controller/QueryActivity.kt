package com.example.atividade2mobile.controller

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.atividade2mobile.R
import com.example.atividade2mobile.data.dao.FilmeDao
import com.example.atividade2mobile.model.FilmeModel

class QueryActivity : AppCompatActivity(){
    private lateinit var listView: ListView;
    private lateinit var searchView: EditText;
    private lateinit var filmeDAO: FilmeDao;
    private lateinit var emptyTextView: TextView;
    private var allFilmes: List<FilmeModel> = emptyList();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_query)

        filmeDAO = FilmeDao(this)

        listView = findViewById(R.id.resultadoListView);
        searchView = findViewById(R.id.pesquisaEditText)
        emptyTextView = findViewById(R.id.tvEmpty);

        listAllFilms();

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedChar = parent.getItemAtPosition(position) as FilmeModel
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("filmeID", selectedChar.id)
            }
            startActivity(intent)
        }

        searchView.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                pesquisarFilme(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {}

        })
    }

    private fun listAllFilms(){
        allFilmes = filmeDAO.getAllFilmes()
        if(allFilmes.isEmpty()){
            listView.visibility = ListView.GONE
            emptyTextView.visibility = TextView.VISIBLE
        }
        else {
            listView.visibility = ListView.VISIBLE
            emptyTextView.visibility = TextView.GONE
            val adapter: ArrayAdapter<FilmeModel> = ArrayAdapter(
                this, android.R.layout.simple_list_item_1, allFilmes
            )
            listView.adapter = adapter
        }
    }

    private fun pesquisarFilme(query: String) {
        val filmesFiltrados = allFilmes.filter { filme ->
            filme.toString().toLowerCase().contains(query.toLowerCase())
        }
        val filteredAdapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, filmesFiltrados
        )

        listView.adapter = filteredAdapter

        if (filmesFiltrados.isEmpty()) {
            listView.visibility = ListView.GONE
            emptyTextView.visibility = TextView.VISIBLE
            emptyTextView.text = "Nenhum filme encontrado"
        } else {
            listView.visibility = ListView.VISIBLE
            emptyTextView.visibility = TextView.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        listAllFilms()
    }
}