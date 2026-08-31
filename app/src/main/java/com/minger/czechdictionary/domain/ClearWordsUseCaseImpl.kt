package com.minger.czechdictionary.domain

import com.minger.czechdictionary.data.WordsRepository

class ClearWordsUseCaseImpl(private val repository: WordsRepository) : ClearWordsUseCase {
    override suspend fun clearWords() {
        repository.clear()
    }
}