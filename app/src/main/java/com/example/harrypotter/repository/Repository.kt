package com.example.harrypotter.repository

import com.example.harrypotter.database.RetrofitInstance
import com.example.harrypotter.models.BooksModel
import com.example.harrypotter.models.CharactersModel
import com.example.harrypotter.models.InfoModel
import com.example.harrypotter.models.SpellsModel
import retrofit2.Response

class Repository {

    suspend fun getBooks(): Response<List<BooksModel>> {
        return RetrofitInstance.api.getBooks()
    }

    suspend fun getCharacters(): Response<List<CharactersModel>> {
        return RetrofitInstance.api.getCharacters()
    }

    suspend fun getSpells(): Response<List<SpellsModel>> {
        return RetrofitInstance.api.getSpells()
    }

    suspend fun getInfo(): Response<List<InfoModel>> {
        return RetrofitInstance.api.getInfo()
    }

}