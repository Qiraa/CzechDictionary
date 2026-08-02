package com.minger.czechdictionary.domain

import com.minger.czechdictionary.data.Word

interface GetWordsUseCase {
    suspend fun getWords(query: String = ""): List<Word>
}