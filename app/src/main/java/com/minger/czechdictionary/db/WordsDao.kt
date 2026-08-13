package com.minger.czechdictionary.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WordsDao {

    @Query("SELECT * FROM words ORDER BY createdAt DESC")
    fun getWords() : List<WordEntity>

    @Query("SELECT * FROM words WHERE word = :word LIMIT 1")
    suspend fun getWord(word: String): WordEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWords(vararg word: WordEntity)

    @Delete
    fun deleteWords(vararg word: WordEntity)

    @Query("DELETE FROM words")
    fun deleteAll()

    @Query("SELECT * FROM words WHERE isFavourite = 1")
    fun getFavouriteWords() : List<WordEntity>

    @Query("SELECT * FROM words ORDER BY createdAt DESC")
    fun observeWords(): Flow<List<WordEntity>>

    @Query("SELECT * FROM words WHERE word = :word LIMIT 1")
    fun observeWord(word: String): Flow<WordEntity?>
}