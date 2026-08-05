package com.minger.czechdictionary.domain

import com.minger.czechdictionary.presentation.word.WordState

interface GetWordUseCase {
    suspend fun getWord(word: String): WordState.Success.Word
}