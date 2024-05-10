package com.example.harrypotter.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.harrypotter.models.InfoModel

@Dao
interface InfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInfo(info: InfoModel)

    @Query("SELECT * FROM info")
    suspend fun getAllInfo(): List<InfoModel>

}