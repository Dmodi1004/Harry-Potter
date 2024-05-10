package com.example.harrypotter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.harrypotter.Converters
import com.example.harrypotter.dao.BooksDao
import com.example.harrypotter.dao.CharactersDao
import com.example.harrypotter.dao.InfoDao
import com.example.harrypotter.dao.SpellsDao
import com.example.harrypotter.models.BooksModel
import com.example.harrypotter.models.CharactersModel
import com.example.harrypotter.models.InfoModel
import com.example.harrypotter.models.SpellsModel

@Database(
    entities = [BooksModel::class, CharactersModel::class, SpellsModel::class, InfoModel::class],
    version = 6,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RoomInstance : RoomDatabase() {

    abstract fun booksDao(): BooksDao
    abstract fun charactersDao(): CharactersDao
    abstract fun spellsDao(): SpellsDao
    abstract fun infoDao(): InfoDao

    companion object {
        private var INSTANCE: RoomInstance? = null

        fun getInstance(context: Context): RoomInstance {
            if (INSTANCE == null) {
                synchronized(RoomInstance::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            RoomInstance::class.java, "HarryPotterDB"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

}