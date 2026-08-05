package com.minger.czechdictionary.domain

import com.minger.czechdictionary.data.Word
import com.minger.czechdictionary.data.WordsRepository

class UpdateWordWordUseCaseImpl(private val repository: WordsRepository) : UpdateWordUseCase {
    override suspend fun updateWord(word: Word) {
        repository.updateWord(word = word)
    }
}