package com.minger.czechdictionary.domain

import com.minger.czechdictionary.data.Word

interface UpdateWordUseCase {
    suspend fun updateWord(word: Word)
}