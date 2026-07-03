package com.minger.czechdictionary.presentation.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class FavouriteViewModel: ViewModel() {

    private val mutableState: MutableStateFlow<FavouriteState> = MutableStateFlow(
        FavouriteState.Success(
            listOf("auto", "kniha", "strom").map { FavouriteWordItem(it) }
        )
    )
    val state: StateFlow<FavouriteState> = mutableState.asStateFlow()

    fun onWordClick(word: String) {
        val current = mutableState.value
        if (current is FavouriteState.Success) {
            val updatedWords = current.words.map {
                if (it.word == word) it.copy(isFavourite = false, isRemoving = true) else it
            }
            mutableState.value = FavouriteState.Success(updatedWords)

            viewModelScope.launch {
                delay(400.milliseconds)
                mutableState.value = FavouriteState.Success(
                    updatedWords.filter { it.word != word }
                )
            }
        }
    }
}
