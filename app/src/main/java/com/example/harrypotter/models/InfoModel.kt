package com.example.harrypotter.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "info")
data class InfoModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val type: String,
    val content: String
)