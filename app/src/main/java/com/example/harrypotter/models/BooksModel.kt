package com.example.harrypotter.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "books")
data class BooksModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val author: String,
    val releaseDay: String?,
    val releseDay: String?,
    val description: String
): Serializable