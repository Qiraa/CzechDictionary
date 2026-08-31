package com.minger.czechdictionary.data

import kotlinx.coroutines.flow.Flow

interface WordsRepository {

    suspend fun getWords() : List<Word>

    fun observeWords(): Flow<List<Word>>

    suspend fun getWord(word: String) : Word

    suspend fun addWord(word: Word)

    suspend fun deleteWord(word: Word)

    suspend fun updateWord(word: Word)

    suspend fun clear()

    fun observeWord(word: String): Flow<Word>
}