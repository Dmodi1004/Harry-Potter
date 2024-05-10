package com.example.harrypotter.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "spells")
data class SpellsModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val spell: String,
    val use: String
)