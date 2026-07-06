package com.minger.czechdictionary.presentation.history

sealed class HistoryState(){

    object Loading: HistoryState()

    object Error: HistoryState()

    data class Success(val words: List<WordItem>): HistoryState()
}

data class WordItem(
    val word: String,
    val isFavorite: Boolean = false
)