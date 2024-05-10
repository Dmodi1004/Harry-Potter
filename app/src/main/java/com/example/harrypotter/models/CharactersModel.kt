package com.example.harrypotter.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharactersModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val character: String,
    val nickname: String,
    val hogwartsStudent: Boolean,
    val hogwartsHouse: String,
    val interpretedBy: String,
    val image: String,
    val child: List<String>?,
    var isExpandable: Boolean = false
)