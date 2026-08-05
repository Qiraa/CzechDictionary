package com.minger.czechdictionary.presentation.word

sealed interface WordState {

    object Loading: WordState

    object Error: WordState

    class Success(val word: Word): WordState {

        data class Word(
            val word: String,
            val partOfSpeech: String,
            val translate: String,
            val isFavourite: Boolean,
            val definition: String,
        )
    }
}

