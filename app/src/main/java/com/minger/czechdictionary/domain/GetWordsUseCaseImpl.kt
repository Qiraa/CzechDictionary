package com.minger.czechdictionary.domain

import com.minger.czechdictionary.data.Word
import com.minger.czechdictionary.data.WordsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetWordsUseCaseImpl(private val repository: WordsRepository) : GetWordsUseCase {
    override suspend fun getWords(query: String): List<Word> {
        val words = repository.getWords()
        return if (query.isBlank()) {
            words.toList()
        } else {
            words.filter { it.word.contains(query, ignoreCase = true) }
        }
    }

    override fun observeWords(): Flow<List<Word>> {
        return repository.observeWords()
    }

}