package com.minger.czechdictionary.domain

import com.minger.czechdictionary.data.Word
import com.minger.czechdictionary.data.WordsRepository

class UpdateWordUseCaseImpl(private val repository: WordsRepository) : UpdateUseCase {
    override suspend fun updateWord(word: Word) {
        repository.updateWord(word = word)
    }
}