package com.example.harrypotter.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.harrypotter.models.CharactersModel

@Dao
interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: CharactersModel)

    @Query("SELECT * FROM characters")
    suspend fun getAllCharacters(): List<CharactersModel>

}