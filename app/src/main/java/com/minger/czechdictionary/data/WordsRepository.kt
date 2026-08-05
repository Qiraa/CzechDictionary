package com.minger.czechdictionary.data

interface WordsRepository {

    suspend fun getWords() : List<Word>

    suspend fun getWord(word: String) : Word

    suspend fun addWord(word: Word)

    suspend fun deleteWord(word: Word)

    suspend fun updateWord(word: Word)

    suspend fun clear()
}