package com.minger.czechdictionary.domain

import com.minger.czechdictionary.data.Word
import kotlinx.coroutines.flow.Flow

interface GetWordsUseCase {
    suspend fun getWords(query: String = ""): List<Word>

    fun observeWords(): Flow<List<Word>>
}