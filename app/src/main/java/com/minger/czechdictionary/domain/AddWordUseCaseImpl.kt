package com.minger.czechdictionary.domain

import com.minger.czechdictionary.data.Word
import com.minger.czechdictionary.data.WordsRepository

class AddWordUseCaseImpl(private val repository: WordsRepository) : AddWordUseCase {

    override suspend fun addWord(word: Word) {
        repository.addWord(word)
    }
}