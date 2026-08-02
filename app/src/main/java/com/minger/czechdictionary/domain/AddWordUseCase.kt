package com.minger.czechdictionary.domain

import com.minger.czechdictionary.data.Word

interface AddWordUseCase {
   suspend fun addWord(word: Word)
}