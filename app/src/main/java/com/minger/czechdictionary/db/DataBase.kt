package com.minger.czechdictionary.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WordEntity::class],
    version = 1,
)
abstract class DataBase : RoomDatabase() {

    abstract fun wordsDao(): WordsDao
}