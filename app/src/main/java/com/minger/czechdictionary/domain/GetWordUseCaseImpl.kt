package com.minger.czechdictionary.domain

import com.minger.czechdictionary.data.WordsRepository
import com.minger.czechdictionary.presentation.word.WordState

class GetWordUseCaseImpl(val repository: WordsRepository) : GetWordUseCase {
    override suspend fun getWord(word: String): WordState.Success.Word {
        val found = repository.getWord(word)
        return WordState.Success.Word(
            word = found.word,
            partOfSpeech = found.partOfSpeech,
            translate = found.translate,
            isFavourite = found.isFavourite,
            definition = found.definition,
        )
    }
}
