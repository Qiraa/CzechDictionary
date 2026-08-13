package com.minger.czechdictionary.domain

import com.minger.czechdictionary.data.WordsRepository
import com.minger.czechdictionary.presentation.word.WordState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetWordUseCaseImpl(val repository: WordsRepository) : GetWordUseCase {
    override suspend fun getWord(word: String): WordState.Success.Word {
        val found = repository.getWord(word)
        return WordState.Success.Word(
            word = found.word,
            partOfSpeech = found.partOfSpeech,
            translate = found.translate,
            isFavourite = found.isFavourite,
            definition = found.definition,
            createdAt = found.createdAt,
        )
    }

    override fun observeWord(word: String): Flow<WordState.Success.Word> {
        return repository.observeWord(word).map { it ->
            WordState.Success.Word(
                word = it.word,
                partOfSpeech = it.partOfSpeech,
                translate = it.translate,
                isFavourite = it.isFavourite,
                definition = it.definition,
                createdAt = it.createdAt,
            )
        }
    }
}
