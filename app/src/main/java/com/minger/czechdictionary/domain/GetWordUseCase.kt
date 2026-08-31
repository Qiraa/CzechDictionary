package com.minger.czechdictionary.domain

import com.minger.czechdictionary.presentation.word.WordState
import kotlinx.coroutines.flow.Flow

interface GetWordUseCase {
    suspend fun getWord(word: String): WordState.Success.Word

    fun observeWord(word: String): Flow<WordState.Success.Word>
}