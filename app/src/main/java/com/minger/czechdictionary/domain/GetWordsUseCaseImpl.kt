package com.minger.czechdictionary.domain

import com.minger.czechdictionary.data.Word
import com.minger.czechdictionary.data.WordsRepository

class GetWordsUseCaseImpl(private val repository: WordsRepository) : GetWordsUseCase {
    override suspend fun getWords(query: String): List<Word> {
        val words = repository.getWords()
        return if (query.isBlank()) {
            words.toList()
        } else {
            words.filter { it.word.contains(query, ignoreCase = true) }
        }
    }
}