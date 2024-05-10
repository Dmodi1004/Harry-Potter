package com.example.harrypotter.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.harrypotter.models.BooksModel

@Dao
interface BooksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: BooksModel)

    @Query("SELECT * FROM books")
    suspend fun getAllBooks(): List<BooksModel>

}