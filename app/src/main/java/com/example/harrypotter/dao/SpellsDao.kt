package com.example.harrypotter.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.harrypotter.models.SpellsModel

@Dao
interface SpellsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpells(spells: SpellsModel)

    @Query("SELECT * FROM spells")
    suspend fun getAllSpells(): List<SpellsModel>

}