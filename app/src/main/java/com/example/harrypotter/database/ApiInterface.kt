package com.example.harrypotter.database

import com.example.harrypotter.models.BooksModel
import com.example.harrypotter.models.CharactersModel
import com.example.harrypotter.models.InfoModel
import com.example.harrypotter.models.SpellsModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("books")
    suspend fun getBooks(): Response<List<BooksModel>>

    @GET("characters")
    suspend fun getCharacters(): Response<List<CharactersModel>>

    @GET("spells")
    suspend fun getSpells(): Response<List<SpellsModel>>

    @GET("info")
    suspend fun getInfo(): Response<List<InfoModel>>

}