package com.example.atividade2mobile.model

data class FilmeModel (
    val id: Int = 0,
    val name: String,
    val director: String,
    val year: Int
){
    override fun toString(): String{
        return name
    }
}