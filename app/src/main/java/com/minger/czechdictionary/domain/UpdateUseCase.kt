package com.minger.czechdictionary.domain

import com.minger.czechdictionary.data.Word

interface UpdateUseCase {
    suspend fun updateWord(word: Word)
}