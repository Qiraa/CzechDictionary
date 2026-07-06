package com.minger.czechdictionary.presentation.history

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HistoryViewModel : ViewModel() {

    private val allWords = listOf(
        WordItem("matka"),
        WordItem("otec"),
        WordItem("syr"),
        WordItem("auto"),
        WordItem("kniha"),
        WordItem("strom"),
    )

    private val mutableState = MutableStateFlow(HistoryState.Success(words = allWords))
    val state: StateFlow<HistoryState> = mutableState.asStateFlow()

    private val currentSearchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = currentSearchQuery.asStateFlow()

    fun onSearchQueryChanged(query: String) {
        currentSearchQuery.value = query
        mutableState.value = HistoryState.Success(
            words = if (query.isBlank()) {
                allWords
            } else {
                allWords.filter { it.word.contains(query, ignoreCase = true) }
            }
        )
    }

    fun onFavouriteClick(word: String) {
        val current = mutableState.value
        if (current is HistoryState.Success) {
            val updated = current.words.map {
                if(it.word == word) it.copy(isFavorite = !it.isFavorite) else it
            }
            mutableState.value = HistoryState.Success(words = updated)
        }
    }
}